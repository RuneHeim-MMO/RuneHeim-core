package org.zuma.examplePlugin.domain.player.states;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.utils.LoadingMessage;

import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RunePlayerLoadingStateHandler {
    private static final int EFFECT_DURATION_TICKS = 10 * 20;
    private final Map<UUID, Collection<PotionEffect>> loadingPlayers = new HashMap<>();
    private final LoadingMessage loadingMessageGenerator = new LoadingMessage();
    private final RuneQuestPlugin plugin;

    public RunePlayerLoadingStateHandler(RuneQuestPlugin plugin) {
        this.plugin = plugin;
        startEffectLoop();
    }

    public boolean isPlayerLoading(Player player) {
        return loadingPlayers.get(player.getUniqueId()) != null;
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
        String funFact = loadingMessageGenerator.getRandomMessage();


        player.showTitle(Title.title(
                Component.text("Loading...").color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD),
                Component.text(funFact).color(Color.SUBTITLE.asColor()).decorate(TextDecoration.ITALIC).append(Component.text(" @ RuneHeim").color(Color.SERVER_BRAND.asColor())),
                Title.Times.times(Duration.ofSeconds(EFFECT_DURATION_TICKS / 20 / 3), Duration.ofSeconds(EFFECT_DURATION_TICKS / 20 / 3), Duration.ofSeconds(EFFECT_DURATION_TICKS / 20 / 3))
        ));
    }


}
