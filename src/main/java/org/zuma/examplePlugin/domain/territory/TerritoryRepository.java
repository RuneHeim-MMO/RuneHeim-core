package org.zuma.examplePlugin.domain.territory;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.List;

public class TerritoryRepository {

    public List<Territory> getAll() {
        return List.of(
                new Territory("drakkar", new Location(Bukkit.getWorld("world"), 200, 0, -68), new Location(Bukkit.getWorld("world"), 192, 73, -71), 150, "§3Drakkar", "The starting point for all adventurers.", 1, 10),
                new Territory("drakkar-outskirts", new Location(Bukkit.getWorld("world"), 200, 0, -68), null, 200, "§bDrakkar outskirts", "The training grounds just outside the city walls.", 3, 10),
                new Territory("oakshade", new Location(Bukkit.getWorld("world"), 535, 0, 0), new Location(Bukkit.getWorld("world"), 503, 68, -3), 100, "§aOakshade", "A quiet village located near Drakkar.", 3, 10),
                new Territory("amber-cottage", new Location(Bukkit.getWorld("world"), 250, 0, -270), new Location(Bukkit.getWorld("world"), 243, 68, -250), 60, "§6Amber farm", "A tranquil farm where crops grow.", 3, 10),
                new Territory("ashwale", new Location(Bukkit.getWorld("world"), -80, 0, -80), new Location(Bukkit.getWorld("world"), -86, 68, -78), 100, "§8Ashwale", "Small village neighboring Drakkar.", 3, 10),

                new Territory("brineport", new Location(Bukkit.getWorld("world"), -445, 0, 230), new Location(Bukkit.getWorld("world"), -479, 65, 206), 100, "§3Brineport", "A coastal town overlooking the sea.", 20, 30),
                new Territory("sakurmeer", new Location(Bukkit.getWorld("world"), 850, 0, 180), new Location(Bukkit.getWorld("world"), 809, 119, 190), 250, "§dSakurmeer", "A mystical land of eternal cherry blossoms.", 20, 40),

                new Territory("murkwatch", new Location(Bukkit.getWorld("world"), -1050, 0, 425), new Location(Bukkit.getWorld("world"), -994, 69, 428), 120, "§2Murkwatch", "A city built on stilts, rising above the treacherous swamp.", 25, 30),
                new Territory("fenshade", new Location(Bukkit.getWorld("world"), -775, 0, 570), new Location(Bukkit.getWorld("world"), -779, 67, 525), 70, "§2Fenshade", "An abandoned mine where the air hangs thick with a murky mist.", 25, 30),
                new Territory("swamp-island", new Location(Bukkit.getWorld("world"), -900, 0, 500), null, 300, "§2Swamp Island", "An island in the swamp, full of hidden dangers.", 25, 30),
                new Territory("swamp-island", new Location(Bukkit.getWorld("world"), -1000, 0, 265), null, 300, "§2Swamp Island", "An island in the swamp, full of hidden dangers.", 25, 30),

                new Territory("branleaft", new Location(Bukkit.getWorld("world"), -620, 0, -300), new Location(Bukkit.getWorld("world"), -622, 69, -299), 100, "§aBranleaft", "A secluded village nestled deep within the whispering woods.", 30, 45),
                new Territory("eldarwyn", new Location(Bukkit.getWorld("world"), -880, 0, -340), new Location(Bukkit.getWorld("world"), -861, 79, -348), 150, "§bEldarwyn", "A grand castle overlooking the western seas.", 30, 45),
                new Territory("valdrin", new Location(Bukkit.getWorld("world"), -15, 0, -530), new Location(Bukkit.getWorld("world"), -16, 116, -530), 80, "§fValdrin", "A peaceful village nestled on a cliff.", 30, 50),
                new Territory("islenor", new Location(Bukkit.getWorld("world"), 496, 0, -714), new Location(Bukkit.getWorld("world"), 496, 66, -716), 100, "§9Islenor", "A quiet port town where the waves lap gently.", 45, 50),

                new Territory("zarali", new Location(Bukkit.getWorld("world"), 480, 0, 470), new Location(Bukkit.getWorld("world"), 478, 69, 473), 100, "§2Zarali", "A hidden village nestled deep within the jungle canopy.", 40, 50),
                new Territory("heartroots-chamber", new Location(Bukkit.getWorld("world"), 250, 0, 500), null, 100, "§2The Heartroots Chamber", "The hidden source of the ancient forest's life.", 40, 50),
                new Territory("glaciem", new Location(Bukkit.getWorld("world"), 950, 0, 500), new Location(Bukkit.getWorld("world"), 948, 70, 500), 100, "§bGlaciem", "A frozen tundra where blizzards rage endlessly.", 40, 55),
                new Territory("thornbark", new Location(Bukkit.getWorld("world"), 1090, 0, -350), new Location(Bukkit.getWorld("world"), 1098, 65, -353), 100, "§3Thornbark", "A dark and twisted forest of gnarled trees.", 40, 60),

                new Territory("saharijin", new Location(Bukkit.getWorld("world"), -1078, 0, -720), new Location(Bukkit.getWorld("world"), -1206, 67, -752), 300, "§eSaharijin", "A vast desert where the sun shows no mercy.", 50, 60),
                new Territory("sporefell", new Location(Bukkit.getWorld("world"), 1250, 0, -150), new Location(Bukkit.getWorld("world"), 1250, 70, -150), 180, "§5Sporefell", "A forest of colossal, glowing mushrooms.", 60, 70),
                new Territory("ethereal-nexus", new Location(Bukkit.getWorld("world"), 925, 0, -845), null, 110, "§5The Ethereal Nexus", "A swirling vortex of pure magical energy.", 70, 80),
                new Territory("aetheris", new Location(Bukkit.getWorld("world"), -135, 0, 780), new Location(Bukkit.getWorld("world"), -180, 77, 794), 150, "§dAetheris", "An ethereal forest where celestial magic courses through the trees.", 70, 90),

                new Territory("infernak", new Location(Bukkit.getWorld("world"), 1000, 0, -630), new Location(Bukkit.getWorld("world"), 933, 75, -645), 160, "§cInfernak", "A volcanic land forged in fire and brimstone.", 80, 90),

                new Territory("ethereal-nexus", new Location(Bukkit.getWorld("world"), 2518, 0, 2467), new Location(Bukkit.getWorld("world"), 2500, 112, 2457.5, -90, 0), 200, "§bTutorial Island", "The first step of every hero", 1, 1)


        );
    }
}
