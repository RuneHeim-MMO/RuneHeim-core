package org.zuma.examplePlugin.domain.command;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.core.utils.XpUtils;
import org.zuma.examplePlugin.domain.hud.LoadingBarBuilder;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.Skill;

import java.text.NumberFormat;
import java.util.Collection;

public class SkillsCommand implements BasicCommand {

    private Component getSkillStats(RunePlayer player) {
        Component result = Component.text("\n");

        for (Skill skill : player.getSkills().values()) {
            int currentLevel = skill.getBaseLevel();
            int totalXp = (int) Math.round(skill.getXp());
            boolean isMaxed = XpUtils.isMaxLevel(currentLevel);
            int percentToNextLevel = isMaxed ? 100 : XpUtils.getProgressPercent(totalXp, currentLevel);
            String formattedXp = NumberFormat.getNumberInstance().format(totalXp);

            String formattedNextLevelXp;
            if (isMaxed) {
                formattedNextLevelXp = "MAX";
            } else {
                formattedNextLevelXp = NumberFormat.getNumberInstance().format(XpUtils.getXpForLevel(currentLevel + 1));
            }

            Component progressBar = new LoadingBarBuilder()
                    .totalSteps(100)
                    .currentStep(isMaxed ? 100 : percentToNextLevel)
                    .barLength(25)
                    .segment(Icon.PROGRESS_SHORT_SEGMENT.get())
                    .start(Icon.PROGRESS_SHORT_START.get())
                    .stop(Icon.PROGRESS_SHORT_END.get())
                    .emptyColor(Color.SKILL_BAR_EMPTY.asColor())
                    .fillColor(Color.SKILL_BAR_FILL.asColor())
                    .build();

            Component tooltip = Component.text()
                    .append(Component.text("Total: ", Color.ACCENT.asColor()))
                    .append(Component.text(formattedXp + " XP", NamedTextColor.WHITE))
                    .append(Component.text("\n"))
                    .append(Component.text("Next Level: ", Color.ACCENT.asColor()))
                    .append(Component.text(formattedNextLevelXp + " XP", NamedTextColor.WHITE))
                    .append(Component.text("\n\n"))
                    .append(Component.text("Remaining: ", Color.ACCENT.asColor()))
                    .append(Component.text(
                            NumberFormat.getNumberInstance().format(XpUtils.getRemainingXP(skill.getXp())) + " XP",
                            NamedTextColor.WHITE
                    ))
                    .build();

            result = result
                    .append(progressBar)
                    .append(Component.text(" - ", NamedTextColor.GRAY))
                    .append(Component.text(skill.getType().getSymbol() + " ", NamedTextColor.WHITE))
                    .append(Component.text(skill.getType().getLabel() + ": ", NamedTextColor.GRAY))
                    .append(Component.text(skill.getBaseLevel(), NamedTextColor.WHITE).decoration(TextDecoration.BOLD, true))
                    .append(skill.getBonus() > 0 ? Component.text(" (+" + skill.getBonus() + ")", NamedTextColor.GREEN) : Component.text(""))
                    .append(Component.text("\n")).hoverEvent(HoverEvent.showText(tooltip));

        }

        return result;

    }


    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        CommandSender sender = commandSourceStack.getSender();

        if (args.length == 0 && !(sender instanceof Player)) {
            sender.sendMessage(Component.text("This command can be only used by players. Pass in an argument to look up player.").color(Color.ERROR.asColor()));
            return;
        }

        // No player
        if (args.length == 0) {
            RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer((Player) sender);

            if (runePlayer == null) {
                sender.sendMessage(Component.text("Could not find RunePlayer instance!").color(Color.ERROR.asColor()));
                return;
            }

            int totalLevel = runePlayer.getSkills().values().stream().mapToInt(Skill::getBaseLevel).sum();
            int cmbLevel = runePlayer.getCombatLvl();

            runePlayer.asBukkitPlayer().sendMessage(getSkillStats(runePlayer)
                    .append(Component.text("\n"))
                    .append(Component.text("Total Level: ", NamedTextColor.GRAY))
                    .append(Component.text(totalLevel, Color.ACCENT.asColor())).append(Component.text(" || Combat level: ", NamedTextColor.GRAY))
                    .append(TextUtils.getLevelGradientComponent(cmbLevel)));

        }

        // With Player
        if (args.length == 1) {
            Player matchingPlayer = Bukkit.getPlayer(args[0]);

            if (matchingPlayer == null) {
                sender.sendMessage(Component.text("Player " + args[0] + " could not be found.", Color.ERROR.asColor()));
                return;
            }

            RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(matchingPlayer);

            if (runePlayer == null) {
                sender.sendMessage(Component.text("Could not find RunePlayer instance!").color(Color.ERROR.asColor()));
                return;
            }

            int totalLevel = runePlayer.getSkills().values().stream().mapToInt(Skill::getBaseLevel).sum();
            int cmbLevel = runePlayer.getCombatLvl();

            sender.sendMessage(getSkillStats(runePlayer)
                    .append(Component.text("\n"))
                    .append(Component.text(runePlayer.asBukkitPlayer().getName() + " Total Level: ", NamedTextColor.GRAY))
                    .append(Component.text(totalLevel, Color.ACCENT.asColor())).append(Component.text(" || Combat level: ", NamedTextColor.GRAY))
                    .append(TextUtils.getLevelGradientComponent(cmbLevel)));


        }


    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
    }


}
