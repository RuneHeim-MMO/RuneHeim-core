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

    WANT_TO_SEE_FOOD_STOCK(List.of(
            "Care to see the menu?",
            "Want a hot meal?",
            "See what's cooking?",
            "Looking for food?",
            "Hungry?"
    )),

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
    )),

    SHIP_GHOSTS(List.of("We never finished our watch.",
            "The sea took our names.",
            "I still hear the cannon fire.",
            "The captain betrayed us.",
            "Our hammocks sway with no wind.",
            "We cannot leave the deck.",
            "Rations rotted, but we stayed.",
            "The crow feeds on our fear.",
            "Our bones still echo in the hull.",
            "We died calling for help.",
            "The galley smells of ash.",
            "We hear the tide in our ears.",
            "The first mate still drags his chains.",
            "Our flags are tattered memories.",
            "We were never buried.",
            "Even the rats abandoned us.",
            "The bell tolls for us alone.",
            "We see the living, but they do not see us.",
            "The wind carries our curses.",
            "Our hammocks sway in silence.",
            "The brig smells of rust and regret.",
            "The powder keg waits for the foolish.",
            "Our eyes follow you from below deck.",
            "We were chained to this voyage.",
            "We died keeping watch.",
            "No one remembers our names.",
            "The sea keeps what it wants.",
            "We can smell your fear.",
            "Our shadows linger on the deck.",
            "We whisper so the living listen.",
            "We cannot feel, yet we ache.",
            "The ropes still hold our sins.",
            "We are hungry for what we lost.",
            "The tide will claim you too.",
            "We were too slow to die.",
            "Even the lanterns avoid us.",
            "We remember every mutiny.",
            "Our screams are waterlogged.",
            "We died with secrets untold.",
            "The sails still bear our whispers.",
            "We hear footsteps that aren’t ours.",
            "The captain waits for no one.",
            "We cannot escape the hold.",
            "Our blood stains the planks forever.",
            "We watch the horizon that will never come.",
            "The lanterns flicker at our presence.",
            "We are tethered to the sea.",
            "Even death could not free us.",
            "We know where the bodies float.",
            "The waves remember our pain."));

    private final List<String> preset;

    DialogPreset(List<String> preset) {
        this.preset = preset;
    }

    public List<String> getList() {
        return preset;
    }
}
