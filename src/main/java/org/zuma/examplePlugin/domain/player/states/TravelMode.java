package org.zuma.examplePlugin.domain.player.states;

import org.zuma.examplePlugin.core.constants.SoundType;

public enum TravelMode {
    CARRIAGE("carriage", SoundType.TRAVEL_CARRIAGE_STEP, SoundType.TRAVEL_CARRIAGE_LOOP, SoundType.TRAVEL_CARRIAGE_END),
    SHIP("ship", SoundType.TRAVEL_SHIP_STEP, SoundType.TRAVEL_SHIP_LOOP, SoundType.TRAVEL_SHIP_END);

    private final String vehicleType;
    private final SoundType stepSound;
    private final SoundType loopSound;
    private final SoundType endSound;

    TravelMode(String vehicleType, SoundType stepSound, SoundType loopSound, SoundType endSound) {
        this.vehicleType = vehicleType;
        this.stepSound = stepSound;
        this.loopSound = loopSound;
        this.endSound = endSound;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public SoundType getStepSound() {
        return stepSound;
    }

    public SoundType getLoopSound() {
        return loopSound;
    }

    public SoundType getEndSound() {
        return endSound;
    }
}
