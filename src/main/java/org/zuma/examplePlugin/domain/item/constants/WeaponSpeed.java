package org.zuma.examplePlugin.domain.item.constants;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.zuma.examplePlugin.core.constants.Icon;

public enum WeaponSpeed {
    VERY_SLOW("§4Very Slow", 0.75),
    SLOW("§cSlow", 1.1875),
    NORMAL("§eNormal", 1.625),
    FAST("§3Fast", 2.0625),
    VERY_FAST("§bVery Fast", 2.5);

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

    public Long getDelayMillis() {
        return (long) (1000 / hitsPerSec);
    }

}
