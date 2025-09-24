package org.zuma.examplePlugin.domain.tour;

public enum Tour {
    WELCOME_TOUR("welcome");

    private final String routeName;

    Tour(String routeName) {
        this.routeName = routeName;
    }

    public String getRouteName() {
        return routeName;
    }
}
