package org.zuma.examplePlugin.domain.item.state.states;

import org.zuma.examplePlugin.domain.item.base.AbstractItem;
import org.zuma.examplePlugin.domain.item.state.StateType;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;

import java.util.ArrayList;
import java.util.List;

public class GearGemSocketsState extends AbstractState {
    private final List<Integer> gemIds;
    private final int slots;

    public GearGemSocketsState(List<Integer> gemIds, int maxSlots) {
        super(StateType.MULTI_CONSUMABLE);
        this.gemIds = gemIds;
        this.slots = maxSlots;
    }


    public void insertGem(AbstractItem gem) throws IllegalStateException {
        if (gemIds.contains(gem.id)) {
            throw new IllegalStateException(gem.displayName + " is already in this gear.");
        }

        if (gemIds.size() >= slots) {
            throw new IllegalStateException("This item has maximum of " + slots + " gem slots!");
        }

        gemIds.add(gem.id);
    }

    public void removeGem(AbstractItem gem) throws IllegalStateException {
        if (!gemIds.contains(gem.id)) {
            throw new IllegalStateException(gem.displayName + " is not inserted into this gear.");
        }

        gemIds.remove(gem.id);
    }

    public List<Integer> getInsertedGemIds() {
        return new ArrayList<>(gemIds);
    }

    public int getGemSlots() {
        return this.slots;
    }
}
