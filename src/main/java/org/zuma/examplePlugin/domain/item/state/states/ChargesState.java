package org.zuma.examplePlugin.domain.item.state.states;

import com.google.gson.Gson;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.item.state.StateType;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.meta.AbstractStateMeta;
import org.zuma.examplePlugin.domain.item.state.meta.v1.ChargesStateMeta;

public class ChargesState extends AbstractState {
    private double charges;

    public ChargesState(ChargesStateMeta meta) {
        super(StateType.CHARGES_STATE);
        this.charges = meta.charges();
    }

    @Override
    public AbstractStateMeta serialize(Gson gson) {
        return new AbstractStateMeta(this.getStateType(), new VersionedJson(gson.toJson(new ChargesStateMeta(this.charges)), 1));
    }

    @Override
    public AbstractState clone() {
        return new ChargesState(new ChargesStateMeta(this.charges));
    }

    public double getCharges() {
        return charges;
    }

    public void setCharges(double charges) {
        this.charges = charges;
    }
}
