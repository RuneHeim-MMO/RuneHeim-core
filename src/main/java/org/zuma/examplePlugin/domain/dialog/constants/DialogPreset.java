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
    )),

    FAREWELL(List.of("Bye!",
            "See ya!",
            "Later!",
            "Cheers!",
            "Cya!",
            "Peace!",
            "Adios!",
            "Take care!",
            "Farewell!",
            "So long!",
            "Catch ya!",
            "Goodbye!",
            "Stay safe!",
            "Till then!",
            "Bye now!")),

    WANT_TO_SEE_STOCK(List.of(
            "Want to see what's in stock?",
            "Want to take a look at my goods?",
            "Interested in my wares?",
            "See what I have for sale?",
            "Care to browse my stock?",
            "Looking for something special?",
            "Have a look around?",
            "See anything you like?",
            "Let me know if you want something?"
    ));

    private final List<String> preset;

    DialogPreset(List<String> preset) {
        this.preset = preset;
    }

    public List<String> getList() {
        return preset;
    }
}
