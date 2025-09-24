package org.zuma.examplePlugin.domain.heimscript.action;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.XpSource;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.dialog.Dialog;
import org.zuma.examplePlugin.domain.droptable.DropTable;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehavior;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.heimscript.ScriptParser;
import org.zuma.examplePlugin.domain.player.states.TravelStateHandler;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;
import org.zuma.examplePlugin.events.dialog.QuestStepToBeCompleted;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ScriptedActionHandler {

    public record ActionCaller(ActionCallerType type, String identifier) {
    }

    public enum ActionCallerType {
        PLAYER_COMMAND, CONSOLE_COMMAND, DIALOG
    }

    public static void executeAction(String input, RunePlayer player, @Nullable Runnable callback, ActionCaller executor, @Nullable Dialog parentDialog) {
        try {
            ScriptedAction action = ScriptParser.matchInputToStatement(input, ScriptedAction.class);
            HashMap<String, String> args = ScriptParser.parseArgs(input, action);
            AbstractMob holder = getHolder(input, parentDialog, action);

            switch (action) {
                case DIALOG_HOLDER_SOUND -> {
                    holder.playSound(MobBehavior.MobSoundType.valueOf(args.get("sound").toUpperCase()));
                }
                case DIALOG_HOLDER_FORWARD_INTERACTION -> {
                    holder.handleInteraction(player);
                }
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
                case TAKE_POUCH_COINS -> {
                    player.setPouchCoins(player.getPouchCoins() - Long.parseLong(args.get("amount")), false);
                }
                case TAKE_INVENTORY_ITEM -> {
                    PlayerUtils.removeItems(player, List.of(new ItemRecord(Integer.parseInt(args.get("item_id")), Integer.parseInt(args.get("amount")))));
                }
                case QUEST_STEP_FINISH -> {
                    String stableId = args.get("stable_id");

                    if (player.getActiveQuestSteps().values().stream().noneMatch(step -> Objects.equals(step.getStableId(), stableId))) {
                        throw new IllegalStateException("Cannot finish " + player.asBukkitPlayer().getName() + " quest step " + stableId + "! No active steps with that ID found.");
                    }

                    Bukkit.getServer().getPluginManager().callEvent(new QuestStepToBeCompleted(player.getUUID(), stableId));

                }
                case CLEAR_INVENTORY -> {
                    player.asBukkitPlayer().getInventory().clear();
                }
                case TRAVEL_SEQUENCE -> {
                    World world = Bukkit.getWorld(args.get("world"));

                    Location destination = new Location(world, Double.parseDouble(args.get("dest_x")), Double.parseDouble(args.get("dest_y")), Double.parseDouble(args.get("dest_z")));
                    destination.setYaw(Float.parseFloat(args.get("dest_yaw")));

                    Location buffer = new Location(world, Double.parseDouble(args.get("buffer_x")), Double.parseDouble(args.get("buffer_y")), Double.parseDouble(args.get("buffer_z")));
                    buffer.setYaw(Float.parseFloat(args.get("buffer_yaw")));

                    int durationTicks = Integer.parseInt(args.get("duration_seconds")) * 20;
                    TravelStateHandler.startTravelingSequence(player, destination, buffer, durationTicks);

                }
                case START_TOUR -> {
                    String tourName = args.get("tour_name");
                    RuneQuestPlugin.getTourHandler().startTour(player, tourName, callback);
                }
                default ->
                        RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "[SCRIPT] Unknown action " + action + " in " + input + "!");
            }

            RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO,
                    "\u001B[36m[SCRIPT] Action " +
                            "\u001B[93m" + input +
                            "\u001B[36m executed by " +
                            "\u001B[93m" + executor.type +
                            "\u001B[36m target " +
                            "\u001B[93m" + player.asBukkitPlayer().getName() +
                            "\u001B[36m identifier: " +
                            "\u001B[93m" + executor.identifier +
                            "\u001B[0m.");

        } catch (Exception e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "[SCRIPT] Could not execute the actions " + input, e);
            throw e;
        }
    }

    private static @org.jetbrains.annotations.Nullable AbstractMob getHolder(String input, @org.jetbrains.annotations.Nullable Dialog parentDialog, ScriptedAction action) {
        List<ScriptedAction> typesRequireDialogHolder = List.of(ScriptedAction.DIALOG_HOLDER_SOUND, ScriptedAction.DIALOG_HOLDER_FORWARD_INTERACTION);
        AbstractMob holder = null;

        if (typesRequireDialogHolder.contains(action)) {
            if (parentDialog == null) {
                throw new IllegalStateException("[SCRIPT] Cannot run " + input + " action is executed outside of dialog context!");
            }

            holder = parentDialog.getDialogHolder();

            if (holder == null) {
                throw new IllegalStateException("[SCRIPT] Cannot run " + input + " dialog holder is null!");
            }
        }
        return holder;
    }


}
