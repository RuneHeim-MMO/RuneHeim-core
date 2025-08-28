package org.zuma.examplePlugin.core.module;

import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.interfaces.PluginModule;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;

import java.util.*;

/**
 * Iterative ModuleLoader that starts modules when their dependencies are satisfied
 * either by context-bound services or by modules already started.
 *
 * This avoids requiring modules to declare "provides()" and works with existing
 * modules that bind services during their start() calls.
 */
public class ModuleLoader {
    private final List<PluginModule> modules;
    private final ModuleContext context;
    private final List<PluginModule> startOrder = new ArrayList<>(); // actual start order

    public ModuleLoader(List<PluginModule> modules, ModuleContext context) {
        this.modules = new ArrayList<>(modules);
        this.context = context;
    }

    /**
     * Starts modules in an order determined at runtime. Modules that have all
     * dependencies satisfied (by context or already-started modules) will be started.
     */
    public void startAll(Plugin plugin) {
        startOrder.clear();
        // remaining modules to start
        List<PluginModule> remaining = new ArrayList<>(modules);

        // keep track of started modules (same as startOrder but useful during loop)
        Set<PluginModule> started = new LinkedHashSet<>();

        while (!remaining.isEmpty()) {
            boolean progressed = false;
            Iterator<PluginModule> it = remaining.iterator();

            while (it.hasNext()) {
                PluginModule candidate = it.next();
                if (canStart(candidate, started)) {
                    try {
                        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Starting " + candidate.name() + "...");
                        candidate.start(plugin, context);
                        started.add(candidate);
                        startOrder.add(candidate);
                        it.remove();
                        progressed = true;
                    } catch (Exception e) {
                        RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL,
                                "Failed to start " + candidate.name() + "!", e);
                        // remove to avoid infinite loop — but propagate failure by throwing after logging
                        it.remove();
                        throw new RuntimeException("Module " + candidate.name() + " failed to start", e);
                    }
                }
            }

            if (!progressed) {
                // Nothing started this pass -> unresolved deps or circular / missing prebound services.
                throw new RuntimeException(buildUnresolvedMessage(remaining, started));
            }
        }
    }

    /**
     * Stops all modules in reverse start order.
     */
    public void stopAll(Plugin plugin) {
        List<PluginModule> reversed = new ArrayList<>(startOrder);
        Collections.reverse(reversed);

        for (PluginModule module : reversed) {
            try {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Stopping " + module.name() + "...");
                module.stop(plugin, context);
            } catch (Exception e) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL,
                        "Failed to stop " + module.name() + "!", e);
            }
        }
    }

    /**
     * Returns true if the module's dependencies are currently satisfied by either:
     * - context (has exact or assignable bound service)
     * - OR some already-started module whose class is assignable to the dependency
     */
    private boolean canStart(PluginModule module, Set<PluginModule> started) {
        for (Class<?> dep : module.dependencies()) {
            if (isSatisfiedByContext(dep)) continue;

            boolean satisfiedByStartedModule = false;
            for (PluginModule s : started) {
                if (dep.isAssignableFrom(s.getClass())) {
                    satisfiedByStartedModule = true;
                    break;
                }
            }
            if (!satisfiedByStartedModule) return false;
        }
        return true;
    }

    /**
     * Check if ModuleContext currently satisfies the dependency.
     * Prefer to call context.hasAssignable(Class) if present; otherwise fallback to has()
     * plus checking bound keys for assignable matches using reflection over context internals
     * is avoided — instead we rely on context.has() and optional hasAssignable.
     */
    private boolean isSatisfiedByContext(Class<?> dep) {
        // Try context.hasAssignable(Class) if implemented
        try {
            var method = context.getClass().getMethod("hasAssignable", Class.class);
            Object res = method.invoke(context, dep);
            if (res instanceof Boolean && (Boolean) res) return true;
        } catch (NoSuchMethodException ignored) {
            // skip to fallback
        } catch (Exception ignored) {
            // if reflection fails, fallback to has()
        }

        // fallback: exact match
        if (context.has(dep)) return true;

        // As a last resort, try to check by getting a service assignable to dep.
        // If get(dep) returns non-null for an assignable match, consider satisfied.
        try {
            var method = context.getClass().getMethod("get", Class.class);
            Object svc = method.invoke(context, dep);
            if (svc != null) return true;
        } catch (NoSuchMethodException ignored) {
            // no get method implemented - treat as not present
        } catch (Exception ignored) {
            // ignore reflection errors
        }

        return false;
    }

    /**
     * Build a helpful error message describing why modules remain unresolved.
     */
    private String buildUnresolvedMessage(List<PluginModule> remaining, Set<PluginModule> started) {
        StringBuilder sb = new StringBuilder();
        sb.append("Unresolved module dependencies detected. Could not make progress starting modules.\n");
        sb.append("Remaining modules (").append(remaining.size()).append("):\n");

        for (PluginModule m : remaining) {
            sb.append(" - ").append(m.name()).append(" requires: ");
            List<String> missing = new ArrayList<>();
            for (Class<?> dep : m.dependencies()) {
                boolean ok = isSatisfiedByContext(dep);
                if (!ok) {
                    for (PluginModule s : started) {
                        if (dep.isAssignableFrom(s.getClass())) {
                            ok = true; // satisfied by already started module (shouldn't happen if isSatisfiedByContext false, but safe)
                            break;
                        }
                    }
                }
                if (!ok) {
                    // collect candidates among ALL modules (including remaining) to help debug
                    List<String> candidates = new ArrayList<>();
                    for (PluginModule candidate : modules) {
                        if (dep.isAssignableFrom(candidate.getClass())) {
                            candidates.add(candidate.name());
                        }
                    }
                    if (candidates.isEmpty()) {
                        missing.add(dep.getName() + " (no candidate module; check context.bind or provider)");
                    } else {
                        missing.add(dep.getName() + " (candidate modules: " + String.join(", ", candidates) + ")");
                    }
                }
            }
            if (missing.isEmpty()) {
                sb.append("none (should be satisfied)"); // defensive
            } else {
                sb.append(String.join(", ", missing));
            }
            sb.append('\n');
        }

        sb.append("\nStarted modules (").append(started.size()).append("): ");
        boolean first = true;
        for (PluginModule s : started) {
            if (!first) sb.append(", ");
            sb.append(s.name());
            first = false;
        }
        sb.append('\n');

        sb.append("Tips:\n");
        sb.append(" * Ensure the module that binds the service calls context.bind(...) during start().\n");
        sb.append(" * If a service is pre-bound before startAll, make sure ModuleContext.has() / get() works.\n");
        sb.append(" * If a dependency is an interface, ensure the implementing module's class is assignable to it or binds the interface into context.\n");

        return sb.toString();
    }
}
