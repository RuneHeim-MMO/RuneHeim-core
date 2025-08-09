package org.zuma.examplePlugin.domain.player.actions;

import org.zuma.examplePlugin.core.constants.SoundType;

import javax.annotation.Nullable;

public class ActionParams {
    private final int durationMs;
    private final String action;
    private final String successAction;
    @Nullable
    private final SoundType stepSound;
    @Nullable
    private final SoundType successSound;
    private final Runnable onSuccess;
    @Nullable
    private final Runnable onFailure;

    private ActionParams(Builder builder) {
        this.durationMs = builder.durationMs;
        this.action = builder.actionLabel;
        this.successAction = builder.successActionLabel;
        this.stepSound = builder.stepSound;
        this.successSound = builder.successSound;
        this.onSuccess = builder.onSuccess;
        this.onFailure = builder.onFailure;
    }

    // Getters
    public int getDurationMs() {
        return durationMs;
    }

    public String getActionLabel() {
        return action;
    }

    public String getSuccessActionLabel() {
        return successAction;
    }

    @Nullable
    public SoundType getStepSound() {
        return stepSound;
    }

    @Nullable
    public SoundType getSuccessSound() {
        return successSound;
    }

    public Runnable getOnSuccess() {
        return onSuccess;
    }

    @Nullable
    public Runnable getOnFailure() {
        return onFailure;
    }

    // Builder class
    public static class Builder {
        private int durationMs;
        private String actionLabel;
        private String successActionLabel;
        @Nullable
        private SoundType stepSound;
        @Nullable
        private SoundType successSound;
        private Runnable onSuccess;
        @Nullable
        private Runnable onFailure;

        public Builder durationMs(int durationMs) {
            this.durationMs = durationMs;
            return this;
        }

        public Builder actionLabel(String actionLabel) {
            this.actionLabel = actionLabel;
            return this;
        }

        public Builder successActionLabel(String successActionLabel) {
            this.successActionLabel = successActionLabel;
            return this;
        }

        public Builder stepSound(@Nullable SoundType stepSound) {
            this.stepSound = stepSound;
            return this;
        }

        public Builder successSound(@Nullable SoundType successSound) {
            this.successSound = successSound;
            return this;
        }

        public Builder onSuccess(Runnable onSuccess) {
            this.onSuccess = onSuccess;
            return this;
        }

        public Builder onFailure(@Nullable Runnable onFailure) {
            this.onFailure = onFailure;
            return this;
        }

        public ActionParams build() {
            if (actionLabel == null || successActionLabel == null || onSuccess == null) {
                throw new IllegalStateException("Required fields missing");
            }
            return new ActionParams(this);
        }
    }
}
