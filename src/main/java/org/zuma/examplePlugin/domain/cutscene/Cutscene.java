package org.zuma.examplePlugin.domain.cutscene;

import org.bukkit.Bukkit;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.cutscene.base.CutsceneSegment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Cutscene {
    private final String stableId;
    private final UUID instanceUUID;
    private final HashMap<String, CutsceneEntityInstance> participants;
    private final List<CutsceneSegment> segments = new ArrayList<>();
    private CutsceneCamera camera;

    public Cutscene(String stableId) {
        this.stableId = stableId;
        this.instanceUUID = UUID.randomUUID();
        this.participants = new HashMap<>();
    }

    public Cutscene addParticipant(String id, CutsceneEntity entity) {
        CutsceneEntityInstance instance = new CutsceneEntityInstance(entity, this);
        participants.put(id, instance);
        return this;
    }

    public CutsceneEntityInstance getParticipant(String id) {
        return participants.get(id);
    }

    public String getStableId() {
        return stableId;
    }

    public UUID getInstanceUUID() {
        return instanceUUID;
    }

    public int durationTicks() {
        return segments.stream().mapToInt(CutsceneSegment::getDurationTicks).sum();
    }

    public Cutscene addSegment(CutsceneSegment segment) {
        segments.add(segment);
        return this;
    }

    public void play(CutsceneCamera camera) {
        Cutscene cutscene = this;
        this.camera = camera;
        camera.startCutscene();

        final int segmentCount = segments.size();
        final int[] finishedSegments = {0};

        for (CutsceneSegment segment : segments) {
            segment.playSegment(camera, this);

            final int[] taskId = new int[1];
            taskId[0] = Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), new Runnable() {
                private int ticks = 0;

                @Override
                public void run() {
                    double progress = (double) ticks / segment.getDurationTicks();
                    segment.tick(progress, cutscene);

                    ticks += 1;
                    if (ticks > segment.getDurationTicks()) {
                        segment.cleanup();
                        Bukkit.getScheduler().cancelTask(taskId[0]);
                        finishedSegments[0]++;
                        if (finishedSegments[0] == segmentCount) {
                            camera.endCutscene();
                            participants.values().forEach(CutsceneEntityInstance::cleanup);
                        }
                    }
                }
            }, 0L, 1L).getTaskId();

        }
    }

    public CutsceneCamera getCamera() {
        return camera;
    }
}
