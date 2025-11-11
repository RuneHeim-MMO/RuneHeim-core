package org.zuma.examplePlugin.domain.command.backend;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.config.ConfigManager;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.permissions.roles.UpgradedRole;
import org.zuma.examplePlugin.domain.payments.handlers.DonationHandler;
import org.zuma.examplePlugin.domain.payments.Payment;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class ConsolePaymentCommand implements BasicCommand {


    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        CommandSender sender = commandSourceStack.getSender();

        if (sender instanceof Player) {
            sender.sendMessage(Component.text("This command can be only used by console.").color(Color.ERROR.asColor()));
            return;
        }

        if (!ConfigManager.isPreviewServer) {
            sender.sendMessage(Component.text("This command can be only used on a preview server.").color(Color.ERROR.asColor()));
            return;
        }

        if (args[0].equalsIgnoreCase("add_payment")) {
            if (args.length < 5) {
                sender.sendMessage(Component.text("Usage: /handle_payment add_payment {uuid} {username} {txid} {amount} {email}").color(Color.ERROR.asColor()));
                return;
            }

            try {
                // uuid, username, amount, email
                BigDecimal amount = new BigDecimal(args[4]);
                Payment payment = new Payment(UUID.fromString(args[1]), args[2], args[3], amount, args[5]);
                RuneQuestPlugin.getPaymentHandler().addPayment(payment, true);
            } catch (Exception e) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL,
                        "Failed to add payment for UUID " + args[1] + "!", e);
            }
            return;
        }

        if (args[0].equalsIgnoreCase("update_rank")) {
            if (args.length < 2) {
                sender.sendMessage(Component.text("Usage: /handle_payment update_rank {uuid} {upgraded_rank}").color(Color.ERROR.asColor()));
                return;
            }

            try {
                UUID uuid = UUID.fromString(args[1]);
                UpgradedRole newRole = UpgradedRole.valueOf(args[2].toUpperCase());
                DonationHandler.updatePlayerRole(uuid, newRole);
            } catch (Exception e) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL,
                        "Failed to update uuid " + args[1] + " role to " + args[2] + "!", e);
            }
            return;
        }


    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        return List.of();
    }
}


