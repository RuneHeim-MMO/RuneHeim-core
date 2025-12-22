package org.zuma.examplePlugin.domain.command;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.housing.instance.HouseInstance;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class HouseCommand implements BasicCommand {


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

        if (runePlayer.isInCutscene()) return;
        if (runePlayer.isDead()) return;

        HouseInstance visitingHouse = RuneQuestPlugin.getHousingManager().getVisitingHouse(runePlayer);

        if (visitingHouse == null || !visitingHouse.getOwnerUUID().equals(runePlayer.getUUID())) {
            HudService.sendFailMessage(runePlayer, Component.text("You must be inside your own house to use this."));
            return;
        }

        if (args.length == 0) {
            RuneQuestPlugin.getGUIHandler().openInventory(InventoryId.HOUSE_SETTINGS.asInt(), runePlayer, null);
            SoundType.CONTROL_PANEL_OPEN.play(runePlayer, false);
            return;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("invite")) {
            String invitedName = args[1];

            try {
                visitingHouse.invitePlayer(invitedName);
                HudService.sendInfoMessage(runePlayer, Component.text("You have invited " + invitedName + " to your house."));
            } catch (Exception e) {
                HudService.sendFailMessage(runePlayer, Component.text(e.getMessage()));
            }
            return;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("revoke")) {
            String invitedName = args[1];

            try {
                visitingHouse.uninvitePlayer(invitedName);
                HudService.sendInfoMessage(runePlayer, Component.text("You have removed " + invitedName + "'s invite to your house."));
            } catch (Exception e) {
                HudService.sendFailMessage(runePlayer, Component.text(e.getMessage()));
            }
            return;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("kick")) {
            Player target = Bukkit.getPlayer(args[1]);

            if (target == null) {
                HudService.sendFailMessage(runePlayer, Component.text("Player " + args[1] + " is not found"));
                return;
            }

            try {
                visitingHouse.kickVisitor(target.getUniqueId(), true);
                HudService.sendInfoMessage(runePlayer, Component.text("You have kicked " + target.getName() + " from your house."));
            } catch (Exception e) {
                HudService.sendFailMessage(runePlayer, Component.text(e.getMessage()));
            }
            return;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("kickall")) {
            try {
                visitingHouse.kickAll();
                HudService.sendInfoMessage(runePlayer, Component.text("You have kicked all visitors from your house."));
            } catch (Exception e) {
                HudService.sendFailMessage(runePlayer, Component.text(e.getMessage()));
            }
            return;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("public")) {
            try {
                visitingHouse.setOpenToPublic(true);
                HudService.sendInfoMessage(runePlayer, Component.text("Your house is now open to the public."));
            } catch (Exception e) {
                HudService.sendFailMessage(runePlayer, Component.text(e.getMessage()));
            }
            return;
        }


        if (args.length == 1 && args[0].equalsIgnoreCase("private")) {
            try {
                visitingHouse.setOpenToPublic(false);
                HudService.sendInfoMessage(runePlayer, Component.text("Your house is now private."));
            } catch (Exception e) {
                HudService.sendFailMessage(runePlayer, Component.text(e.getMessage()));
            }
            return;
        }


        bukkitPlayer.sendMessage(Component.text("Usage: /house <invite | revoke | kick | kickall | public | private>", Color.ERROR.asColor()));


    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        CommandSender sender = commandSourceStack.getSender();

        if (!(sender instanceof Player player)) {
            return List.of();
        }

        RunePlayer runePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer(player);
        if (runePlayer == null) {
            return List.of();
        }

        HouseInstance house = RuneQuestPlugin.getHousingManager().getVisitingHouse(runePlayer);
        if (house == null || !house.getOwnerUUID().equals(runePlayer.getUUID())) {
            return List.of();
        }

        // /house <tab>
        if (args.length == 1) {
            return List.of("invite", "revoke", "kick", "kickall", "public", "private");
        }

        // /house invite <tab>
        if (args.length == 2 && args[0].equalsIgnoreCase("invite")) {
            return RuneQuestPlugin.getInstance()
                    .getServer()
                    .getOnlinePlayers()
                    .stream()
                    .map(Player::getName)
                    .filter(name -> !name.equalsIgnoreCase(house.getOwnerName()))
                    .filter(name -> !house.getInvitedPlayers().contains(name.toLowerCase()))
                    .toList();
        }

        // /house uninvite <tab>
        if (args.length == 2 && args[0].equalsIgnoreCase("revoke")) {
            return house.getInvitedPlayers().stream().map(Bukkit::getPlayer).filter(Objects::nonNull).map(Player::getName)
        }

        // /house kick <tab>
        if (args.length == 2 && args[0].equalsIgnoreCase("kick")) {
            return house.getCurrentVisitors().stream().map(Bukkit::getPlayer).filter(Objects::nonNull).map(Player::getName).toList();
        }

        return List.of();
    }


}
