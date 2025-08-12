package org.zuma.examplePlugin.domain.dialog.constants;

import java.util.List;

public enum DialogPreset {
    WELCOME(List.of(
            "Hello, {PLAYER_NAME}!",
            "Hello!",
            "Hello there!",
            "Greetings, {PLAYER_NAME}!",
            "Welcome back, {PLAYER_NAME}!",
            "Hey, {PLAYER_NAME}!",
            "Good to see you, {PLAYER_NAME}!",
            "Hi, {PLAYER_NAME}!",
            "Well met, {PLAYER_NAME}!",
            "Salutations!",
            "Howdy, {PLAYER_NAME}!",
            "Ah, {PLAYER_NAME}, just in time!",
            "Good day to you, {PLAYER_NAME}!",
            "Nice to meet you, {PLAYER_NAME}!",
            "Hey there!",
            "What's up, {PLAYER_NAME}?",
            "Welcome!",
            "How goes it, {PLAYER_NAME}?"
    ));


    private final List<String> preset;

    DialogPreset(List<String> preset) {
        this.preset = preset;
    }

    public List<String> getList() {
        return preset;
    }
}
