package org.zuma.examplePlugin.domain.command;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.permissions.Permission;
import org.zuma.examplePlugin.core.utils.CurrencyUtils;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.core.utils.TypeUtils;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.actions.ActionParams;
import org.zuma.examplePlugin.domain.player.actions.ProgressBarAction;
import org.zuma.examplePlugin.domain.territory.Territory;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class StuckCommand implements BasicCommand {
    private static final AbstractItemType coinsType = RuneQuestPlugin.getItemRegistry().getType(ItemId.COINS.asInt());
    private static final HashMap<UUID, Long> dropTimes = new HashMap<>(); // Prevent coins spamming.
    private static final int DROP_COOLDOWN_SECONDS = 5;


    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        CommandSender sender = commandSourceStack.getSender();

        if (!(sender instanceof Player bukkitPlayer)) {
            sender.sendMessage(Component.text("This command can be only used by players.").color(Color.ERROR.asColor()));
            return;
        }

        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(bukkitPlayer);

        if (runePlayer == null) {
            sender.sendMessage(Component.text("Could not find RunePlayer instance!").color(Color.ERROR.asColor()));
            return;
        }

        if (runePlayer.hasPendingActions()) return;

        ActionParams params = new ActionParams.Builder()
                .durationMs(30000)
                .actionLabel("Teleporting to closest city...")
                .successActionLabel("")
                .stepSound(SoundType.UNSTUCK_COMMAND_PROGRESS)
                .stepSoundEveryNth(3)
                .onSuccess(() -> {
                    PlayerUtils.teleportToClosestCity(bukkitPlayer);
                    runePlayer.cancelActionTask(true);
                })
                .build();

        runePlayer.queueOrStartAction(new ProgressBarAction(false, true), params, false);
    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        return List.of("drop");
    }


}
