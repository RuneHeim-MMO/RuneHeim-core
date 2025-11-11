package org.zuma.examplePlugin.core.permissions;

import net.kyori.adventure.text.Component;

import java.util.List;

public interface Role {
    int getWeight();

    List<Permission> getInheritablePermissions();

    List<Permission> getNonInheritablePermissions();

    boolean containsPermission(Permission permission);

    Component getPrefix();

    boolean isOp();
}
