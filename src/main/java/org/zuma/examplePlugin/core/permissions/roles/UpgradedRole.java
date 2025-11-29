package org.zuma.examplePlugin.core.permissions.roles;

import net.kyori.adventure.text.Component;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.permissions.Permission;
import org.zuma.examplePlugin.core.permissions.Role;

import javax.annotation.Nullable;
import java.util.*;

public enum UpgradedRole implements Role {
    MYTHIC(997, 50, 25, Icon.RANK_BADGE_MYTHIC, List.of(Permission.BONUS_BOMB_TRACKER), List.of()),
    LEGEND(998, 25, 15, Icon.RANK_BADGE_LEGEND, List.of(), List.of()),
    HERO(999, 20, 10, Icon.RANK_BADGE_HERO, List.of(), List.of()),
    VIP(1000, 15, 7, Icon.RANK_BADGE_VIP, List.of(Permission.TOGGLE_UPGRADED_PREFIX), List.of());

    private final int weight, maxQueueSize, maxPartySize;
    private final List<Permission> inheritablePermissions;
    private final List<Permission> nonInheritedPermissions; // Permissions that are not inherited by other roles
    private final Icon prefix;

    UpgradedRole(int weight, int maxQueueSize, int maxPartySize, @Nullable Icon prefix, List<Permission> inheritablePermissions, List<Permission> nonInheritedPermissions) {
        this.weight = weight;
        this.maxQueueSize = maxQueueSize;
        this.maxPartySize = maxPartySize;
        this.inheritablePermissions = inheritablePermissions;
        this.nonInheritedPermissions = nonInheritedPermissions;
        this.prefix = prefix;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public boolean isOp() {
        return false;
    }

    @Override
    public List<Permission> getInheritablePermissions() {
        return inheritablePermissions;
    }

    @Override
    public List<Permission> getNonInheritablePermissions() {
        return nonInheritedPermissions;
    }

    public boolean containsPermission(Permission permission) {
        return inheritablePermissions.contains(permission) || nonInheritedPermissions.contains(permission);
    }

    public Component getPrefix() {
        return prefix != null ? Component.text(prefix.get()) : Component.empty();
    }

    @Override
    public int maxQueueSize() {
        return maxQueueSize;
    }

    @Override
    public int maxPartySize() {
        return maxPartySize;
    }
}
