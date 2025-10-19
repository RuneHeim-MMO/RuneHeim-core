package org.zuma.examplePlugin.core.permissions;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.permissions.PermissionAttachment;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.*;

public enum PlayerRole {
    OWNER(0, true, Icon.RANK_BADGE_OWNER, List.of()),
    ADMIN(1, false, Icon.RANK_BADGE_ADMIN, List.of(Permission.WORLD_MANIPULATION, Permission.ITEM_WATCHDOG_BYPASS, Permission.INTERACTABLE_SPAWN, Permission.DEBUG_COMMAND, Permission.STAFF_ITEM_SPAWN, Permission.BLOCKED_CMD_BYPASS, Permission.CHAT_PROFANITY_BYPASS)),
    MODERATOR(2, false, Icon.RANK_BADGE_MOD, List.of(Permission.STAFF_MODERATION, Permission.STAFF_MESSAGING)),
    PLAYER(1000, false, null, List.of(Permission.PLAYER_DEFAULTS));

    private final int priority;
    private final List<Permission> permissions;
    private final boolean isOp;
    private final Icon prefix;

    PlayerRole(int priority, boolean isOp, @Nullable Icon prefix, List<Permission> permissions) {
        this.priority = priority;
        this.isOp = isOp;
        this.permissions = permissions;
        this.prefix = prefix;
    }

    public int getPriority() {
        return priority;
    }

    public void reloadPermissions(RunePlayer player) {
        PermissionAttachment attachment = player.getPermissionAttachment();
        player.asBukkitPlayer().setOp(false);

        // Clear all existing permissions
        Arrays.stream(PlayerRole.values()).forEach(role -> {
            role.permissions.forEach(permission -> {
                permission.getNodes().forEach(attachment::unsetPermission);
            });
        });


        if (isOp) {
            player.asBukkitPlayer().setOp(true);
        }

        // Set this role's permissions
        this.permissions.forEach(permission -> {
            permission.getNodes().forEach(node -> attachment.setPermission(node, true));
        });

        // Inherit permissions from lower priority roles
        for (PlayerRole role : getLowerPriorityRoles(this)) {
            role.permissions.forEach(permission -> {
                permission.getNodes().forEach(node -> attachment.setPermission(node, true));
            });
        }

    }

    private static List<PlayerRole> getLowerPriorityRoles(PlayerRole role) {
        return Arrays.stream(PlayerRole.values())
                .filter(r -> r.priority > role.priority) // roles with greater numeric priority are "lower"
                .toList();
    }

    public boolean hasPermission(Permission permission) {
        if (permissions != null && permissions.contains(permission)) {
            return true;
        }

        for (PlayerRole role : getLowerPriorityRoles(this)) {
            if (role.permissions != null && role.permissions.contains(permission)) {
                return true;
            }
        }

        return false;
    }

    public Component getFormattedAllPermissionComponent() {
        List<Component> lines = new ArrayList<>();
        Set<Permission> seen = new HashSet<>();

        // this role's permissions first (preserve as parent)
        for (Permission permission : permissions) {
            lines.add(formatPermissionComponent(permission));
            seen.add(permission);
        }

        // then lower-priority roles' permissions that haven't been added yet
        for (PlayerRole role : getLowerPriorityRoles(this)) {
            for (Permission permission : role.permissions) {
                if (seen.add(permission)) {
                    lines.add(formatPermissionComponent(permission));
                }
            }
        }

        // join lines with newline
        Component result = Component.empty();
        for (int i = 0; i < lines.size(); i++) {
            result = result.append(lines.get(i));
            if (i < lines.size() - 1) {
                result = result.append(Component.text("\n"));
            }
        }

        return result;
    }

    private static Component formatPermissionComponent(Permission permission) {
        List<String> nodes = permission.getNodes();
        Component parent = Component.text(permission.name()).color(NamedTextColor.LIGHT_PURPLE); // pink-ish

        if (nodes == null || nodes.isEmpty()) {
            return parent;
        }

        // build nodes array in gray, quoted and comma-separated
        Component nodesComponent = Component.empty();
        for (int i = 0; i < nodes.size(); i++) {
            String node = nodes.get(i);
            nodesComponent = nodesComponent.append(Component.text("'" + node + "'").color(NamedTextColor.GRAY));
            if (i < nodes.size() - 1) {
                nodesComponent = nodesComponent.append(Component.text(", ").color(NamedTextColor.GRAY));
            }
        }

        return parent
                .append(Component.text(" [").color(NamedTextColor.GRAY))
                .append(nodesComponent)
                .append(Component.text("]").color(NamedTextColor.GRAY));
    }

    public Component getPrefix() {
        return prefix != null ? prefix.asComponent() : Component.empty();
    }
}
