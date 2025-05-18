package org.zuma.examplePlugin.core.utils;

import java.util.concurrent.ThreadLocalRandom;

public class LoadingMessage {
    private static final String[] loadingMessages = {
            "Waking up your character... Please wait.",
            "Your data is being summoned by a wizard.",
            "The servers are consulting the Magic 8-Ball.",
            "Hold on! Your internet is riding a turtle.",
            "Dragon on break. Loading your data.",
            "Your XP is coming, but it’s slow.",
            "Goblins are eating your connection.",
            "Loading... Even magic needs coffee.",
            "Wizard searching for your loot.",
            "Internet labyrinth ahead, one moment!",
            "Snail-powered loading... Hang tight!",
            "Data on its way, just a bit slow.",
            "Server gremlins are on lunch break.",
            "Hang tight! The hamster is running faster!",
            "Loading future... Just no rats, please.",
            "Character raised from the dead... Almost.",
            "Goblins delayed your server, sorry!",
            "Loading speed: ‘Oh-no-we-didn’t-prepare.’",
            "Server gremlins messing with the speed.",
            "Filling your inventory, one item at a time.",
            "Data smuggled by internet gremlins.",
            "Enchanting your loading screen now.",
            "Sacrificing pixels to the loading gods.",
            "Data stuck in a dragon’s stomach.",
            "Hold tight! Loading at ‘cat avoids bath’ speed.",
            "Loading your adventure, slow buffet style.",
            "Character’s looking for his keys.",
            "Invisible fairies slow on the job.",
            "Chess with your data... almost done.",
            "Hero’s getting ready... slowpoke style.",
            "Loading... Wizard casting spells too slow.",
            "Not loading, just stuck in traffic."
    };


    public String getRandomMessage() {
        return loadingMessages[ThreadLocalRandom.current().nextInt(loadingMessages.length)];
    }
}
