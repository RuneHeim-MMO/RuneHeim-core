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
import org.zuma.examplePlugin.domain.heimscript.ScriptParser;
import org.zuma.examplePlugin.domain.heimscript.action.ScriptedAction;
import org.zuma.examplePlugin.domain.payments.Payment;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public class ConsolePaymentCommand implements BasicCommand {


    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        CommandSender sender = commandSourceStack.getSender();

        if (sender instanceof Player) {
            sender.sendMessage(Component.text("This command can be only used by console.").color(Color.ERROR.asColor()));
            return;
        }

        if (args.length < 5) {
            sender.sendMessage(Component.text("Usage: /add_payment {uuid} {username} {txid} {amount} {email} {new role (optional)}").color(Color.ERROR.asColor()));
            return;
        }

        if (!ConfigManager.isPreviewServer) {
            sender.sendMessage(Component.text("This command can be only used on a preview server.").color(Color.ERROR.asColor()));
            return;
        }

        try {
            // uuid, username, amount, email
            BigDecimal amount = new BigDecimal(args[3]);
            Payment payment = new Payment(UUID.fromString(args[0]), args[1], args[2], amount, args[4]);
            UpgradedRole newRole = args.length >= 6 ? UpgradedRole.valueOf(args[5]) : null;

            // Example: /add_payment {uuid} {username} {txid} {amount} {email} {new role (optional)}

            RuneQuestPlugin.getPaymentHandler().addPayment(payment, newRole, true);
        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL,
                    "Failed to add payment for UUID " + args[0] + "!", e);
        }
    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        return Arrays.stream(ScriptedAction.values()).map(ScriptParser::getPreview).toList();
    }
}


