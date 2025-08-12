package org.zuma.examplePlugin.domain.action;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.XpSource;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.droptable.DropTable;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.UUID;

public class ScriptedActionHandler {

    public record ActionCaller(ActionCallerType type, String identifier) {
    }

    public enum ActionCallerType {
        PLAYER_COMMAND, CONSOLE_COMMAND,
    }

    public static void executeAction(String input, RunePlayer player, @Nullable Runnable callback, ActionCaller executor) {
        try {
            ScriptedAction action = ScriptedAction.toAction(input);
            HashMap<String, String> args = action.parseArgs(input);

            switch (action) {
                case OPEN_INVENTORY ->
                        RuneQuestPlugin.getGUIHandler().openInventory(Integer.parseInt(args.get("id")), player, callback);
                case GIVE_ITEM ->
                        player.giveItemOrDrop(new ItemRecord(Integer.parseInt(args.get("id")), Integer.parseInt(args.get("amount"))));
                case ROLL_DROP_TABLE -> {
                    DropTable table = RuneQuestPlugin.getDropTableHandler().getByID(Integer.parseInt(args.get("id")));
                    ItemRecord result = table.roll();

                    if (result != null) player.giveItemOrDrop(result);
                }
                case SPAWN_MOB -> {
                    Location loc = new Location(Bukkit.getWorld(args.get("world")), Integer.parseInt(args.get("x")), Integer.parseInt(args.get("y")), Integer.parseInt(args.get("z")));
                    RuneQuestPlugin.getMobRegistry().spawnMob(UUID.randomUUID(), loc, args.get("type"));
                }
                case GIVE_DISTRIBUTED_XP -> {
                    SkillType type = SkillType.valueOf(args.get("skill").toUpperCase());
                    player.distributeXp(type, Double.parseDouble(args.get("xp")));
                }
                case GIVE_PERSONAL_XP -> {
                    SkillType type = SkillType.valueOf(args.get("skill").toUpperCase());
                    player.addXp(type, Double.parseDouble(args.get("xp")), XpSource.PERSONAL);
                }
                case TELEPORT_PLAYER -> {
                    Location loc = new Location(Bukkit.getWorld(args.get("world")), Integer.parseInt(args.get("x")), Integer.parseInt(args.get("y")), Integer.parseInt(args.get("z")));
                    player.asBukkitPlayer().teleport(loc);
                }
            }

            RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO,
                    "\u001B[36m[ScriptedAction] Action " +
                            "\u001B[93m" + input +
                            "\u001B[36m executed by " +
                            "\u001B[93m" + executor.type +
                            "\u001B[36m identifier: " +
                            "\u001B[93m" + executor.identifier +
                            "\u001B[0m.");
            
        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Could not execute the action " + input, e);
        }
    }


}
