package org.zuma.examplePlugin.core.permissions;

import net.kyori.adventure.text.Component;
import org.bukkit.permissions.PermissionAttachment;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.permissions.Permission;
import org.zuma.examplePlugin.core.permissions.roles.BaseRole;
import org.zuma.examplePlugin.core.permissions.roles.UpgradedRole;
import org.zuma.examplePlugin.domain.payments.SupporterLevel;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.settings.PlayerSetting;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PlayerRole {
    private static final List<Permission> allPermissions = Stream.concat(
            Arrays.stream(BaseRole.values()).flatMap(role -> Stream.of(role.getInheritablePermissions(), role.getNonInheritablePermissions())),
            Arrays.stream(UpgradedRole.values()).flatMap(role -> Stream.of(role.getInheritablePermissions(), role.getNonInheritablePermissions()))
    ).flatMap(List::stream).distinct().toList();

    private final BaseRole baseRole;
    @Nullable
    private final UpgradedRole upgradedRole;
    private final SupporterLevel supporterLevel;

    // Separate inheritance chains
    private final List<BaseRole> inheritedBaseRoles; // Base roles that this role inherits from
    private final List<UpgradedRole> inheritedUpgradedRoles; // Upgraded roles that this role inherits from

    private final List<Permission> permissions;

    public PlayerRole(BaseRole baseRole, @Nullable UpgradedRole upgradedRole, SupporterLevel supporterLevel) {
        this.upgradedRole = upgradedRole;
        this.baseRole = baseRole;
        this.supporterLevel = supporterLevel;

        // Calculate base role inheritance (lower weight base roles inherit from higher weight base roles)
        this.inheritedBaseRoles = getInheritedBaseRoles(baseRole);

        // Calculate upgraded role inheritance (lower weight upgraded roles inherit from higher weight upgraded roles)
        this.inheritedUpgradedRoles = upgradedRole != null ?
                getInheritedUpgradedRoles(upgradedRole) : new ArrayList<>();

        this.permissions = getPermissions();
    }

    public String getSupporterRoleIcon(RunePlayer player) {
        boolean showIcon = player.getSettings().getSetting(PlayerSetting.SHOW_SUPPORTER_PREFIX);

        if (!showIcon) {
            return "";
        }

        return supporterLevel.equals(SupporterLevel.LVL_0) ? "" : supporterLevel.levelIcon.get().concat(" ");
    }

    public SupporterLevel getSupporterRoleLevel() {
        return supporterLevel;
    }

    public Component getPrefix(RunePlayer player) {
        Component basePrefix = baseRole.getPrefix();
        Component upgradedPrefix = upgradedRole != null ? upgradedRole.getPrefix() : Component.empty();

        boolean baseVisible = player.getSettings().getSetting(PlayerSetting.SHOW_BASE_PREFIX);
        boolean upgradedVisible = player.getSettings().getSetting(PlayerSetting.SHOW_UPGRADED_PREFIX);

        if (baseRole.equals(BaseRole.PLAYER)) {
            return upgradedVisible ? upgradedPrefix.append(Component.text(" ")) : Component.empty();
        }

        if (baseVisible && upgradedVisible) {
            return upgradedPrefix.append(basePrefix).append(Component.text(" "));
        } else if (baseVisible) {
            return basePrefix.append(Component.text(" "));
        } else if (upgradedVisible) {
            return upgradedPrefix.append(Component.text(" "));
        }

        return Component.empty();
    }

    public boolean hasPermission(Permission permission) {
        return permissions.contains(permission);
    }

    public void reloadPermissions(RunePlayer player) {
        PermissionAttachment attachment = player.getPermissionAttachment();

        // Clear all existing permissions
        allPermissions.forEach(permission -> {
            permission.getNodes().forEach(attachment::unsetPermission);
        });

        player.asBukkitPlayer().setOp(false);

        // Apply base role permissions (current + inherited)
        applyBaseRolePermissions(attachment);

        // Apply upgraded role permissions (current + inherited, if present)
        if (upgradedRole != null) {
            applyUpgradedRolePermissions(attachment);
        }

        // Set OP status
        if (baseRole.isOp() || (upgradedRole != null && upgradedRole.isOp())) {
            player.asBukkitPlayer().setOp(true);
        }
    }

    private List<BaseRole> getInheritedBaseRoles(BaseRole role) {
        return Arrays.stream(BaseRole.values())
                .filter(r -> r.getWeight() > role.getWeight()) // Inherit from higher weight base roles
                .collect(Collectors.toList());
    }

    private List<UpgradedRole> getInheritedUpgradedRoles(UpgradedRole role) {
        return Arrays.stream(UpgradedRole.values())
                .filter(r -> r.getWeight() > role.getWeight()) // Inherit from higher weight upgraded roles
                .collect(Collectors.toList());
    }


    private void applyBaseRolePermissions(PermissionAttachment attachment) {
        // Apply current base role permissions
        baseRole.getInheritablePermissions().forEach(permission -> {
            permission.getNodes().forEach(node -> attachment.setPermission(node, true));
        });

        baseRole.getNonInheritablePermissions().forEach(permission -> {
            permission.getNodes().forEach(node -> attachment.setPermission(node, true));
        });

        // Apply inherited base role permissions (only inheritable ones)
        for (BaseRole inheritedRole : inheritedBaseRoles) {
            inheritedRole.getInheritablePermissions().forEach(permission -> {
                permission.getNodes().forEach(node -> attachment.setPermission(node, true));
            });
        }
    }

    private void applyUpgradedRolePermissions(PermissionAttachment attachment) {
        if (upgradedRole != null) {
            // Apply current upgraded role permissions
            upgradedRole.getInheritablePermissions().forEach(permission -> {
                permission.getNodes().forEach(node -> attachment.setPermission(node, true));
            });

            upgradedRole.getNonInheritablePermissions().forEach(permission -> {
                permission.getNodes().forEach(node -> attachment.setPermission(node, true));
            });
        }

        // Apply inherited upgraded role permissions (only inheritable ones)
        for (UpgradedRole inheritedRole : inheritedUpgradedRoles) {
            inheritedRole.getInheritablePermissions().forEach(permission -> {
                permission.getNodes().forEach(node -> attachment.setPermission(node, true));
            });
        }
    }

    private List<Permission> getPermissions() {
        List<Permission> permissions = new ArrayList<>();

        permissions.addAll(baseRole.getInheritablePermissions());
        permissions.addAll(baseRole.getNonInheritablePermissions());

        for (BaseRole inheritedRole : inheritedBaseRoles) {
            permissions.addAll(inheritedRole.getInheritablePermissions());
        }

        for (UpgradedRole inheritedRole : inheritedUpgradedRoles) {
            permissions.addAll(inheritedRole.getInheritablePermissions());
        }

        if (upgradedRole != null) {
            permissions.addAll(upgradedRole.getInheritablePermissions());
            permissions.addAll(upgradedRole.getNonInheritablePermissions());

            for (UpgradedRole inheritedRole : inheritedUpgradedRoles) {
                permissions.addAll(inheritedRole.getInheritablePermissions());
            }
        }

        return permissions;
    }

    public BaseRole getBaseRole() {
        return baseRole;
    }

    @Nullable
    public UpgradedRole getUpgradedRole() {
        return upgradedRole;
    }

    public SupporterLevel getSupporterLevel() {
        return supporterLevel;
    }

    public int getMaxQueueSize() {
        int baseMax = baseRole.maxQueueSize();
        int upgradedMax = upgradedRole != null ? upgradedRole.maxQueueSize() : 0;

        return Math.max(baseMax, upgradedMax);
    }
}