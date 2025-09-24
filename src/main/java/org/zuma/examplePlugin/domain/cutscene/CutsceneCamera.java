package org.zuma.examplePlugin.domain.cutscene;

import org.bukkit.Location;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class CutsceneCamera {
    private final RunePlayer player;
    private final Location initialLocation;

    public CutsceneCamera(RunePlayer player) {
        this.player = player;
        this.initialLocation = player.asBukkitPlayer().getLocation();
    }

    public void setLocation(Location location) {
        player.asBukkitPlayer().teleport(location);
    }

    public void startCutscene() {
        player.setInCutscene(player.asBukkitPlayer().getLocation());
    }

    public void endCutscene() {
        System.out.println("Teleporting player to initial location: " + initialLocation);

        player.asBukkitPlayer().teleport(initialLocation);
        player.setOutOfCutscene();
    }
}
