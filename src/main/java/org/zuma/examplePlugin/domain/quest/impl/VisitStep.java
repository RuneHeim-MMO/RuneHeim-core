package org.zuma.examplePlugin.domain.quest.impl;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.block.impl.CampfireBlock;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.quest.PlayerQuestState;
import org.zuma.examplePlugin.domain.quest.Quest;
import org.zuma.examplePlugin.domain.quest.base.AbstractQuestStep;
import org.zuma.examplePlugin.domain.quest.base.Tickable;
import org.zuma.examplePlugin.domain.quest.constants.StepType;
import org.zuma.examplePlugin.events.block.CampfireStateChanged;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class VisitStep extends AbstractQuestStep implements Tickable {
    private final VisitStepEntry condition;
    private @Nullable Quest.Coordinates initialCoordinates;

    public record VisitStepEntry(Quest.Coordinates coordinates, int radius) {
    }

    public VisitStep(RuneQuestPlugin plugin, Quest parentQuest, String description, String stableId, VisitStepEntry condition, @Nullable UUID owner) {
        super(plugin, parentQuest, StepType.VISIT_LOCATION, description, stableId, owner);
        this.condition = condition;
        this.initialCoordinates = null;
    }

    public float getProgressPercentage() {
        RunePlayer owner = RuneQuestPlugin.getPlayerManager().getRunePlayer(getOwner());
        if (owner == null || initialCoordinates == null) {
            return 0.0f;
        }

        Location playerLocation = owner.asBukkitPlayer().getLocation();
        Location targetLocation = new Location(
                playerLocation.getWorld(),
                condition.coordinates().x(),
                condition.coordinates().y(),
                condition.coordinates().z()
        );

        Location startLocation = new Location(
                playerLocation.getWorld(),
                initialCoordinates.x(),
                initialCoordinates.y(),
                initialCoordinates.z()
        );

        double radius = condition.radius();
        double currentDist = playerLocation.distance(targetLocation);
        double startDist = startLocation.distance(targetLocation);

        // Inside radius = fully complete
        if (currentDist <= radius) {
            return 1.0f;
        }

        // If initial distance is less than or equal to radius,
        // the player basically spawned on top of the objective.
        if (startDist <= radius) {
            return 1.0f;
        }

        // Scale progress based on movement toward target
        double totalNeeded = startDist - radius;        // how much they need to close
        double closed = startDist - currentDist;   // how much they've actually closed

        if (totalNeeded <= 0) {
            return 0.0f;
        }

        double percent = closed / totalNeeded;

        // Clamp between 0 and 0.9999
        percent = Math.max(0.0, Math.min(percent, 0.9999));

        return (float) percent;
    }


    public Quest.Coordinates getCenterPoint() {
        return condition.coordinates();
    }

    public void tick() {
        RunePlayer owner = RuneQuestPlugin.getPlayerManager().getRunePlayer(getOwner());

        if (owner == null) {
            return;
        }

        Location playerLocation = owner.asBukkitPlayer().getLocation();
        Location targetLocation = new Location(
                playerLocation.getWorld(),
                condition.coordinates().x(),
                condition.coordinates().y(),
                condition.coordinates().z()
        );

        double distance = playerLocation.distance(targetLocation);
        double radius = condition.radius();

        owner.getBossBarManager().setProgress(this.getProgressPercentage());

        if (distance <= radius) {
            this.handleStepCompletion();
        }
    }

    @Override
    public AbstractQuestStep cloneForPlayer(UUID ownerUUID) {
        VisitStep step = new VisitStep(super.getPlugin(), super.getParentQuest(), super.getDescription(), super.getStableId(), this.condition, ownerUUID);
        step.setId(this.getId());

        RunePlayer owner = RuneQuestPlugin.getPlayerManager().getRunePlayer(ownerUUID);
        if (owner != null) {
            Location loc = owner.asBukkitPlayer().getLocation();

            step.setInitialCoordinates(new Quest.Coordinates(
                    (int) loc.getX(),
                    (int) loc.getY(),
                    (int) loc.getZ()
            ));
        }

        return step;
    }

    @Override
    public AbstractQuestStep assignValuesFromState(PlayerQuestState state) {
        Object raw = state.getStepProgressData().get("initial_coords");

        if (raw instanceof Map<?,?> map) {
            Number x = (Number) map.get("x");
            Number y = (Number) map.get("y");
            Number z = (Number) map.get("z");

            this.initialCoordinates = new Quest.Coordinates(
                    x.intValue(),
                    y.intValue(),
                    z.intValue()
            );
        }

        return this;
    }

    @Override
    public PlayerQuestState assignValuesToState(PlayerQuestState state) {
        Map<String, Object> toBeMutated = state.getStepProgressData();
        toBeMutated.put("initial_coords", this.initialCoordinates);
        return state;
    }

    public void setInitialCoordinates(Quest.Coordinates coords) {
        this.initialCoordinates = coords;
    }
}
