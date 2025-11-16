package org.zuma.examplePlugin.core.permissions.roles;

import net.kyori.adventure.text.Component;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.permissions.Permission;
import org.zuma.examplePlugin.core.permissions.Role;

import javax.annotation.Nullable;
import java.util.*;

public enum BaseRole implements Role {
    OWNER(0, true, Icon.RANK_BADGE_OWNER, List.of(Permission.INTERACTABLE_SPAWN), List.of()),
    ADMIN(1, false, Icon.RANK_BADGE_ADMIN, List.of(Permission.STAFF_MOVEMENT, Permission.COOLDOWN_BYPASS, Permission.WORLD_MANIPULATION, Permission.ITEM_WATCHDOG_BYPASS, Permission.DEBUG_COMMAND, Permission.STAFF_ITEM_SPAWN, Permission.BLOCKED_CMD_BYPASS, Permission.CHAT_PROFANITY_BYPASS), List.of()),
    MODERATOR(2, false, Icon.RANK_BADGE_MOD, List.of(Permission.CHAT_COOLDOWN_BYPASS, Permission.STAFF_MODERATION, Permission.STAFF_MESSAGING, Permission.CHAT_SPY, Permission.TOGGLE_STAFF_PREFIX), List.of()),
    PLAYER(1000, false, null, List.of(Permission.PLAYER_DEFAULTS), List.of());

    private final int weight;
    private final List<Permission> inheritablePermissions;
    private final List<Permission> nonInheritedPermissions; // Permissions that are not inherited by other roles
    private final boolean isOp;
    private final Icon prefix;

    BaseRole(int weight, boolean isOp, @Nullable Icon prefix, List<Permission> inheritablePermissions, List<Permission> nonInheritedPermissions) {
        this.weight = weight;
        this.isOp = isOp;
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
        return isOp;
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
}
