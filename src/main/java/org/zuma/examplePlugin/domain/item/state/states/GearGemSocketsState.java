package org.zuma.examplePlugin.domain.item.state.states;

import com.google.gson.Gson;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.impl.Gem;
import org.zuma.examplePlugin.domain.item.state.StateType;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.meta.AbstractStateMeta;
import org.zuma.examplePlugin.domain.item.state.meta.v1.GearGemSocketsStateMeta;

import java.util.ArrayList;
import java.util.List;

public class GearGemSocketsState extends AbstractState {
    private final List<Integer> gemIds;
    private final int slots;

    public GearGemSocketsState(GearGemSocketsStateMeta meta) {
        super(StateType.GEAR_GEM_SOCKETS);
        this.gemIds = meta.gemIds();
        this.slots = meta.maxSlots();
    }


    public void insertGem(AbstractItemType gem) throws IllegalStateException {
        if (gemIds.contains(gem.id)) {
            throw new IllegalStateException(gem.displayName + " is already in this item.");
        }

        if (gemIds.size() >= slots) {
            throw new IllegalStateException("This item has maximum of " + slots + " gem slots!");
        }

        gemIds.add(gem.id);
    }

    public void removeGem(AbstractItemType gem) throws IllegalStateException {
        if (!gemIds.contains(gem.id)) {
            throw new IllegalStateException(gem.displayName + " is not inserted into this item.");
        }

        gemIds.remove(gem.id);
    }

    public List<Integer> getInsertedGemIds() {
        return new ArrayList<>(gemIds);
    }

    public int getTotalGemSlots() {
        return this.slots;
    }

    public int getAvailableGemSlots() {
        return this.slots - this.gemIds.size();
    }

    public List<Gem> getInsertedGems() {
        List<Gem> list = new ArrayList<>();

        getInsertedGemIds().forEach(id -> {
            AbstractItemType item = RuneQuestPlugin.getItemRegistry().getType(id);

            if (item == null) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Cannot retrieve gem item: Socket state contains invalid item id " + id + "!");
                return;
            }

            if (!(item instanceof Gem gemItem)) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Cannot retrieve gem item: Socket state inserted gems ids contains id " + id + ", but " + item.displayName + " is not a gem!");
                return;
            }

            list.add(gemItem);
        });

        return list;
    }

    @Override
    public AbstractStateMeta serialize(Gson gson) {
        return new AbstractStateMeta(this.getStateType(), new VersionedJson(gson.toJson(new GearGemSocketsStateMeta(this.gemIds, this.slots)), 1));
    }

    @Override
    public AbstractState clone() {
        return new GearGemSocketsState(new GearGemSocketsStateMeta(new ArrayList<>(this.gemIds), this.slots));
    }
}
