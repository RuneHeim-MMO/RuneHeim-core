package org.zuma.examplePlugin.domain.territory;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.List;

public class TerritoryRepository {

    public List<Territory> getAll() {
        return List.of(
                new Territory("drakkar", new Location(Bukkit.getWorld("world"), 200, 0, -68), 150, "§3Drakkar", "The starting point for all adventurers."),
                new Territory("drakkar-outskirts", new Location(Bukkit.getWorld("world"), 200, 0, -68), 200, "§bDrakkar outskirts", "The training grounds just outside the city walls."),

                new Territory("islenor", new Location(Bukkit.getWorld("world"), 496, 0, -714), 100, "§9Islenor", "A quiet port town where the waves lap gently."),
                new Territory("saharijin", new Location(Bukkit.getWorld("world"), -1078, 0, -720), 300, "§eSaharijin", "A vast desert where the sun shows no mercy."),
                new Territory("eldarwyn", new Location(Bukkit.getWorld("world"), -900, 0, -350), 150, "§bEldarwyn", "A grand castle overlooking the western seas."),
                new Territory("branleaft", new Location(Bukkit.getWorld("world"), -620, 0, -300), 100, "§aBranleaft", "A secluded village nestled deep within the whispering woods."),
                new Territory("murkwatch", new Location(Bukkit.getWorld("world"), -1050, 0, 425), 120, "§2Murkwatch", "A city built on stilts, rising above the treacherous swamp."),
                new Territory("fenshade", new Location(Bukkit.getWorld("world"), -775, 0, 570), 100, "§2Fenshade", "An abandoned mine where the air hangs thick with a murky mist."),
                new Territory("swamp-island", new Location(Bukkit.getWorld("world"), -820, 0, 434), 300, "§2Swamp Island", "An island in the swamp, full of hidden dangers."),
                new Territory("swamp-island", new Location(Bukkit.getWorld("world"), -920, 0, 295), 300, "§2Swamp Island", "An island in the swamp, full of hidden dangers."),
                new Territory("aetheris", new Location(Bukkit.getWorld("world"), -135, 0, 780), 150, "§dAetheris", "An ethereal forest where celestial magic courses through the trees."),
                new Territory("infernak", new Location(Bukkit.getWorld("world"), 1000, 0, -630), 180, "§cInfernak", "A volcanic land forged in fire and brimstone."),
                new Territory("glaciem", new Location(Bukkit.getWorld("world"), 950, 0, 500), 100, "§bGlaciem", "A frozen tundra where blizzards rage endlessly."),
                new Territory("sakurmeer", new Location(Bukkit.getWorld("world"), 850, 0, 180), 250, "§dSakurmeer", "A mystical land of eternal cherry blossoms."),
                new Territory("sporefell", new Location(Bukkit.getWorld("world"), 1250, 0, -150), 150, "§5Sporefell", "A forest of colossal, glowing mushrooms."),
                new Territory("thornbark", new Location(Bukkit.getWorld("world"), 1090, 0, -350), 100, "§3Thornbark", "A dark and twisted forest of gnarled trees."),
                new Territory("ethereal-nexus", new Location(Bukkit.getWorld("world"), 925, 0, -845), 110, "§5The Ethereal Nexus", "A swirling vortex of pure magical energy."),
                new Territory("valdrin", new Location(Bukkit.getWorld("world"), -15, 0, -530), 80, "§fValdrin", "A peaceful village nestled on a cliff."),
                new Territory("brineport", new Location(Bukkit.getWorld("world"), -445, 0, 230), 100, "§3Brineport", "A coastal town overlooking the sea."),
                new Territory("zarali", new Location(Bukkit.getWorld("world"), 480, 0, 470), 100, "§2Zarali", "A hidden village nestled deep within the jungle canopy."),
                new Territory("heartroots-chamber", new Location(Bukkit.getWorld("world"), 250, 0, 500), 100, "§2The Heartroots Chamber", "The hidden source of the ancient forest's life."),
                new Territory("oakshade", new Location(Bukkit.getWorld("world"), 535, 0, 0), 100, "§aOakshade", "A quiet village located near Drakkar."),
                new Territory("amber-cottage", new Location(Bukkit.getWorld("world"), 250, 0, -270), 60, "§6Amber farm", "A tranquil farm where crops grow."),
                new Territory("ashwale", new Location(Bukkit.getWorld("world"), -80, 0, -80), 100, "§8Ashwale", "Small village neighboring Drakkar.")
                );
    }
}
