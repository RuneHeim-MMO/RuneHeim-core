package org.zuma.examplePlugin.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.constants.Colors;

import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class RunePlayerLoadingStateHandler {
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


    private static final int EFFECT_DURATION_TICKS = 10 * 20;
    private final Map<UUID, Collection<PotionEffect>> loadingPlayers = new HashMap<>();
    private final RuneQuestPlugin plugin;

    public RunePlayerLoadingStateHandler(RuneQuestPlugin plugin) {
        this.plugin = plugin;
        startEffectLoop();
    }

    public void setPlayerLoading(Player player) {
        loadingPlayers.put(player.getUniqueId(), player.getActivePotionEffects()); // Save initial potion effects
        player.clearActivePotionEffects();
        applyLoadingEffect(player);
        printLoadingTitle(player);
    }

    public void setPlayerLoaded(Player player) {
        Collection<PotionEffect> previousEffects = loadingPlayers.get(player.getUniqueId());

        if (previousEffects != null) {
            player.clearActivePotionEffects();
            player.addPotionEffects(previousEffects); // Restore initial potion effects
        }

        loadingPlayers.remove(player.getUniqueId());
        player.clearTitle();
    }

    private void startEffectLoop() {
        Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {

                loadingPlayers.entrySet().removeIf(entry -> {
                    Player player = Bukkit.getPlayer(entry.getKey());

                    if (player == null) {
                        return true;
                    }

                    applyLoadingEffect(player);
                    printLoadingTitle(player);
                    return false;
                });
            }
        }, 0L, EFFECT_DURATION_TICKS);
    }

    private void applyLoadingEffect(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, EFFECT_DURATION_TICKS + 20, 100, false, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, EFFECT_DURATION_TICKS + 20, 100, false, true));
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, EFFECT_DURATION_TICKS + 20, -128, false, true));
    }

    private void printLoadingTitle(Player player) {
        String funFact = loadingMessages[ThreadLocalRandom.current().nextInt(loadingMessages.length)];


        player.showTitle(Title.title(
                Component.text("Loading...").color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD),
                Component.text(funFact).color(Colors.SUBTITLE.asColor()).decorate(TextDecoration.ITALIC).append(Component.text(" @ RuneQuest").color(Colors.SERVER_BRAND.asColor())),
                Title.Times.times(Duration.ofSeconds(EFFECT_DURATION_TICKS / 20 / 3), Duration.ofSeconds(EFFECT_DURATION_TICKS / 20 / 3), Duration.ofSeconds(EFFECT_DURATION_TICKS / 20 / 3))
        ));
    }


}
