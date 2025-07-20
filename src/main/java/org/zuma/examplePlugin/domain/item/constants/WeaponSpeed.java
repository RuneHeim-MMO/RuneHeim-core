package org.zuma.examplePlugin.domain.item.constants;

public enum WeaponSpeed {
    VERY_SLOW("§4Very Slow", 0.75),
    SLOW("§cSlow", 1.1875),
    NORMAL("§eNormal", 1.625),
    FAST("§3Fast", 2.0625),
    VERY_FAST("§bVery Fast", 2.5);

    private final static float BOW_SLOWNESS_OFFSET_MULTIPLIER = 2.5f;
    private final String label;
    private final double hitsPerSec;

    WeaponSpeed(String label, double hitsPerSec) {
        this.label = label;
        this.hitsPerSec = hitsPerSec;
    }


    public String getLabel() {
        return label;
    }


    public double getHitsPerSec() {
        return hitsPerSec;
    }


    public Long getMeleeDelay() {
        return (long) (1000 / hitsPerSec);
    }

    public float getRangedMinPower() {
        double secondsPerHit = 1.0 / hitsPerSec;
        double ticksPerHit = secondsPerHit * 20;
        float drawPower = (float) (ticksPerHit / 20);
        return Math.min(drawPower * BOW_SLOWNESS_OFFSET_MULTIPLIER, 3.0f);
    }

}
