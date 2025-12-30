package org.zuma.examplePlugin.domain.command.help;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.quest.constants.QuestId;

import java.util.Collection;
import java.util.List;

public class DiscordCommand implements BasicCommand {


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


        Component line = Component.text("───────────────────────────────────")
                .color(NamedTextColor.DARK_GRAY);

        boolean completedTutorial = runePlayer.getCompletedQuestIds().contains(QuestId.TUTORIAL.getId());

        if (!completedTutorial) {
            bukkitPlayer.sendMessage(line);
            bukkitPlayer.sendMessage(Component.empty());
            SocialsCommand.sendLink(1, sender, "Discord", "https://discord.gg/dZpS2mkb2k");
            bukkitPlayer.sendMessage(Component.empty());
            bukkitPlayer.sendMessage(Component.text("Tutorial must be completed before you can verify your Discord account.", NamedTextColor.YELLOW));
            bukkitPlayer.sendMessage(line);
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(RuneQuestPlugin.getInstance(), () -> {
            boolean isVerified = RuneQuestPlugin.getDiscordVerificationHandler().isPlayerVerified(bukkitPlayer.getUniqueId());

            if (isVerified) {
                Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                    bukkitPlayer.sendMessage(line);
                    SocialsCommand.sendLink(1, sender, "Discord", "https://discord.gg/dZpS2mkb2k");
                    bukkitPlayer.sendMessage(line);
                });
                return;
            }

            String verificationCode = RuneQuestPlugin.getDiscordVerificationHandler().createOrUpdateVerificationCode(bukkitPlayer.getUniqueId());

            Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
                if (verificationCode == null) {
                    bukkitPlayer.sendMessage(line);
                    bukkitPlayer.sendMessage(Component.empty());
                    SocialsCommand.sendLink(1, sender, "Discord", "https://discord.gg/dZpS2mkb2k");
                    bukkitPlayer.sendMessage(Component.empty());
                    bukkitPlayer.sendMessage(Component.text("An error occurred while generating your verification code. Please try again later.")
                            .color(Color.ERROR.asColor()));
                    bukkitPlayer.sendMessage(line);

                    return;
                }

                bukkitPlayer.sendMessage(line);
                bukkitPlayer.sendMessage(Component.empty());
                SocialsCommand.sendLink(1, sender, "Discord", "https://discord.gg/dZpS2mkb2k");
                bukkitPlayer.sendMessage(Component.empty());
                bukkitPlayer.sendMessage(Component.text("To verify your account, type ", NamedTextColor.YELLOW)
                        .append(Component.text("/verify ", NamedTextColor.GRAY))
                        .append(Component.text(verificationCode, NamedTextColor.AQUA))
                        .append(Component.text(" in our Discord server.").color(NamedTextColor.YELLOW)));
                bukkitPlayer.sendMessage(line);
            });

        });

    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        return List.of();
    }


}
