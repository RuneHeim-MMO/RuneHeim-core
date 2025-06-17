package org.zuma.examplePlugin.domain.hud;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class LoadingBarBuilder {
    private int totalSteps;
    private int currentStep;
    private int barLength = 10; // default
    private String segment = "|";
    private String start = "[";
    private String stop = "]";
    private TextColor empty = TextColor.color(0xAAAAAA);
    private TextColor fill = TextColor.color(0x00FF00);
    private boolean colorBorders = true;
    private boolean inverted = false;

    public LoadingBarBuilder totalSteps(int totalSteps) {
        this.totalSteps = totalSteps;
        return this;
    }

    public LoadingBarBuilder currentStep(int currentStep) {
        this.currentStep = currentStep;
        return this;
    }

    public LoadingBarBuilder barLength(int length) {
        this.barLength = length;
        return this;
    }

    public LoadingBarBuilder inverted(boolean inverted) {
        this.inverted = inverted;
        return this;
    }

    public LoadingBarBuilder segment(String segment) {
        this.segment = segment;
        return this;
    }

    public LoadingBarBuilder start(String start) {
        this.start = start;
        return this;
    }

    public LoadingBarBuilder stop(String stop) {
        this.stop = stop;
        return this;
    }

    public LoadingBarBuilder fillColor(TextColor fill) {
        this.fill = fill;
        return this;
    }

    public LoadingBarBuilder emptyColor(TextColor empty) {
        this.empty = empty;
        return this;
    }

    public LoadingBarBuilder colorBorders(boolean colorBorders) {
        this.colorBorders = colorBorders;
        return this;
    }

    public Component build() {
        // Always use fill color for “filled” and empty color for “unfilled”
        TextColor activeColor = fill;
        TextColor inactiveColor = empty;

        int totalSegments = barLength;
        // Compute how many segments should be filled
        int filledSegments = (int) Math.round(((double) currentStep / totalSteps) * totalSegments);
        filledSegments = Math.max(0, Math.min(totalSegments, filledSegments));

        boolean hasLeft = colorBorders && totalSegments >= 1;
        boolean hasRight = colorBorders && totalSegments >= 2;

        int centerSegments = totalSegments;
        if (colorBorders) {
            centerSegments = Math.max(0, totalSegments - (hasLeft ? 1 : 0) - (hasRight ? 1 : 0));
        }

        Component bar = Component.empty();

        if (!inverted) {
            // Normal fill: left-to-right
            int idx = 0;
            // Left border
            if (hasLeft) {
                bar = bar.append(Component.text(start)
                        .color(idx < filledSegments ? activeColor : inactiveColor));
                idx++;
            } else {
                bar = bar.append(Component.text(start).color(inactiveColor));
            }
            // Center
            for (int i = 0; i < centerSegments; i++, idx++) {
                bar = bar.append(Component.text(segment)
                        .color(idx < filledSegments ? activeColor : inactiveColor));
            }
            // Right border
            if (hasRight) {
                bar = bar.append(Component.text(stop)
                        .color(idx < filledSegments ? activeColor : inactiveColor));
            } else {
                bar = bar.append(Component.text(stop).color(inactiveColor));
            }

        } else {
            // Inverted fill: right-to-left
            int idx = 0;
            // Left border (still at left, but “filled” if it's within the rightmost filledSegments)
            if (hasLeft) {
                bar = bar.append(Component.text(start)
                        .color((totalSegments - idx) <= filledSegments ? activeColor : inactiveColor));
                idx++;
            } else {
                bar = bar.append(Component.text(start).color(inactiveColor));
            }
            // Center
            for (int i = 0; i < centerSegments; i++, idx++) {
                bar = bar.append(Component.text(segment)
                        .color((totalSegments - idx) <= filledSegments ? activeColor : inactiveColor));
            }
            // Right border
            if (hasRight) {
                bar = bar.append(Component.text(stop)
                        .color((totalSegments - idx) <= filledSegments ? activeColor : inactiveColor));
            } else {
                bar = bar.append(Component.text(stop).color(inactiveColor));
            }
        }

        return bar;
    }


}
