package org.zuma.examplePlugin.domain.territory;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.zuma.examplePlugin.core.constants.MusicType;
import org.zuma.examplePlugin.core.constants.SoundType;

import java.util.List;

public class TerritoryRepository {

    public List<Territory> getAll() {
        return List.of(
                new Territory("drakkar",
                        new Location(Bukkit.getWorld("world"), 200, 0, -68),
                        new Location(Bukkit.getWorld("world"), 192, 73, -71),
                        150,
                        "§3Drakkar",
                        "The starting point for all adventurers.",
                        "An ancient city built on the ruins of a forgotten kingdom.",
                        1, 10, false, true, false)
                        .setAmbientMusic(MusicType.DRAKKAR_THEME),

                new Territory("drakkar-outskirts",
                        new Location(Bukkit.getWorld("world"), 200, 0, -68),
                        null,
                        200,
                        "§bDrakkar outskirts",
                        "The training grounds just outside the city walls.",
                        "Fields and pathways worn by generations of warriors-in-training.",
                        3, 10, false, true, false)
                        .setAmbientMusic(MusicType.DRAKKAR_THEME),

                new Territory("oakshade",
                        new Location(Bukkit.getWorld("world"), 535, 0, 0),
                        new Location(Bukkit.getWorld("world"), 503, 68, -3),
                        100,
                        "§aOakshade",
                        "A quiet village located near Drakkar.",
                        "Surrounded by dense forests rumored to be inhabited by spirits.",
                        3, 10, false, true, false)
                        .setAmbientMusic(MusicType.DRAKKAR_THEME),


                new Territory("amber-cottage",
                        new Location(Bukkit.getWorld("world"), 250, 0, -270),
                        new Location(Bukkit.getWorld("world"), 243, 68, -250),
                        60,
                        "§6Amber farm",
                        "A tranquil farm where crops grow.",
                        "Once the estate of a minor noble, now overgrown with golden fields.",
                        3, 10, false, true, false)
                        .setAmbientMusic(MusicType.DRAKKAR_THEME),

                new Territory("ashwale",
                        new Location(Bukkit.getWorld("world"), -80, 0, -80),
                        new Location(Bukkit.getWorld("world"), -86, 68, -78),
                        100,
                        "§8Ashwale",
                        "Small village neighboring Drakkar.",
                        "Built at the edge of a forest long rumored to conceal ancient relics.",
                        3, 10, false, true, false)
                        .setAmbientMusic(MusicType.DRAKKAR_THEME),


                new Territory("brineport",
                        new Location(Bukkit.getWorld("world"), -445, 0, 230),
                        new Location(Bukkit.getWorld("world"), -479, 65, 206),
                        100,
                        "§3Brineport",
                        "A coastal town overlooking the sea.",
                        "Founded by merchants who braved storms to establish trade routes.",
                        20, 30, false, true, false)
                        .setAmbientMusic(MusicType.BRINEPORT_THEME),


                new Territory("sakurmeer",
                        new Location(Bukkit.getWorld("world"), 850, 0, 180),
                        new Location(Bukkit.getWorld("world"), 809, 119, 190),
                        250,
                        "§dSakurmeer",
                        "A mystical land of eternal cherry blossoms.",
                        "Legends speak of a hidden shrine where the first blossom fell.",
                        20, 40, false, true, false)
                        .setAmbientMusic(MusicType.SAKURMEER_THEME),

                new Territory("murkwatch",
                        new Location(Bukkit.getWorld("world"), -1050, 0, 425),
                        new Location(Bukkit.getWorld("world"), -994, 69, 428),
                        120,
                        "§2Murkwatch",
                        "A city built on stilts, rising above the treacherous swamp.",
                        "Once a refuge for exiles, its streets echo with old grudges.",
                        25, 30, false, true, false)
                        .setAmbientMusic(MusicType.SWAMP_THEME),

                new Territory("fenshade",
                        new Location(Bukkit.getWorld("world"), -775, 0, 570),
                        new Location(Bukkit.getWorld("world"), -779, 67, 525),
                        70,
                        "§2Fenshade",
                        "An abandoned mine where the air hangs thick with a murky mist.",
                        "Miners vanished decades ago; some say the mist carries their whispers.",
                        25, 30, false, true, false)
                        .setAmbientMusic(MusicType.SWAMP_THEME),

                new Territory("swamp-island",
                        new Location(Bukkit.getWorld("world"), -900, 0, 500),
                        null,
                        300,
                        "§2Swamp Island",
                        "An island in the swamp, full of hidden dangers.",
                        "The marsh conceals ruins of an old cult long forgotten by time.",
                        25, 30, false, false, false)
                        .setAmbientMusic(MusicType.SWAMP_THEME),

                new Territory("swamp-island",
                        new Location(Bukkit.getWorld("world"), -1000, 0, 265),
                        null,
                        300,
                        "§2Swamp Island",
                        "An island in the swamp, full of hidden dangers.",
                        "Legends say strange lights move through the fog on moonless nights.",
                        25, 30, false, false, false)
                        .setAmbientMusic(MusicType.SWAMP_THEME),

                new Territory("branleaft",
                        new Location(Bukkit.getWorld("world"), -620, 0, -300),
                        new Location(Bukkit.getWorld("world"), -622, 69, -299),
                        100,
                        "§aBranleaft",
                        "A secluded village nestled deep within the whispering woods.",
                        "Trees here are said to remember the names of those who pass.",
                        30, 45, false, true, false)
                        .setAmbientMusic(MusicType.ELDARWYN_THEME),


                new Territory("eldarwyn",
                        new Location(Bukkit.getWorld("world"), -880, 0, -340),
                        new Location(Bukkit.getWorld("world"), -861, 79, -348),
                        150,
                        "§bEldarwyn",
                        "A grand castle overlooking the western seas.",
                        "Once the seat of a legendary family whose heirs vanished mysteriously.",
                        30, 45, false, true, false)
                        .setAmbientMusic(MusicType.ELDARWYN_THEME),

                new Territory("valdrin",
                        new Location(Bukkit.getWorld("world"), -15, 0, -530),
                        new Location(Bukkit.getWorld("world"), -16, 116, -530),
                        80,
                        "§fValdrin",
                        "A peaceful village nestled on a cliff.",
                        "Cliffside tombs tell of warriors who defended these lands long ago.",
                        30, 50, false, true, false)
                        .setAmbientMusic(MusicType.VALDRIN_THEME),

                new Territory("islenor",
                        new Location(Bukkit.getWorld("world"), 496, 0, -714),
                        new Location(Bukkit.getWorld("world"), 496, 66, -716),
                        100,
                        "§9Islenor",
                        "A quiet port town where the waves lap gently.",
                        "Shipwrecks along the coast hint at a forgotten maritime empire.",
                        45, 50, false, true, false)
                        .setAmbientMusic(MusicType.ISLENOR_THEME),

                new Territory("zarali",
                        new Location(Bukkit.getWorld("world"), 480, 0, 470),
                        new Location(Bukkit.getWorld("world"), 478, 69, 473),
                        100,
                        "§2Zarali",
                        "A hidden village nestled deep within the jungle canopy.",
                        "Ancient murals speak of a civilization swallowed by the jungle.",
                        40, 50, false, true, false)
                        .setAmbientMusic(MusicType.ZARALI_THEME),

                new Territory("heartroots-chamber",
                        new Location(Bukkit.getWorld("world"), 250, 0, 500),
                        null,
                        100,
                        "§2The Heartroots Chamber",
                        "The hidden source of the ancient forest's life.",
                        "The roots pulse with magic that predates written history.",
                        40, 50, false, true, false),

                new Territory("glaciem",
                        new Location(Bukkit.getWorld("world"), 950, 0, 500),
                        new Location(Bukkit.getWorld("world"), 948, 70, 500),
                        100,
                        "§bGlaciem",
                        "A frozen tundra where blizzards rage endlessly.",
                        "Ice winds carry tales of explorers lost to the frost.",
                        40, 55, false, true, false)
                        .setAmbientMusic(MusicType.GLACIEM_THEME),

                new Territory("thornbark",
                        new Location(Bukkit.getWorld("world"), 1090, 0, -350),
                        new Location(Bukkit.getWorld("world"), 1098, 65, -353),
                        100,
                        "§3Thornbark",
                        "A dark and twisted forest of gnarled trees.",
                        "Thorns and shadows hide secrets few dare to seek.",
                        40, 60, false, true, false)
                        .setAmbientMusic(MusicType.THORNBARK_THEME),

                new Territory("saharijin-pvp-arena",
                        new Location(Bukkit.getWorld("world"), -1208.5, 0, -698.5),
                        null,
                        21,
                        "§cSaharijin Arena",
                        "An unforgiving desert arena where only the strongest survive.",
                        null,
                        50, 60, true, true, false)
                        .addRequiredQuestId("saharijin-pvp-arena-intro")
                        .setEntryDenyMessage("§4You must speak to the §6Arena Overseer§4 to enter the arena.")
                        .setDropItemsOnDeath(true)
                        .setAmbientMusic(MusicType.SAHARIJIN_ARENA_THEME),

                new Territory("saharijin",
                        new Location(Bukkit.getWorld("world"), -1078, 0, -720),
                        new Location(Bukkit.getWorld("world"), -1206, 67, -752),
                        300,
                        "§eSaharijin",
                        "A vast desert where the sun shows no mercy.",
                        "Bones and relics surface after relentless sandstorms.",
                        50, 60, false, true, false)
                        .setAmbientMusic(MusicType.SAHARIJIN_THEME),


                new Territory("sporefell",
                        new Location(Bukkit.getWorld("world"), 1250, 0, -150),
                        new Location(Bukkit.getWorld("world"), 1250, 70, -150),
                        180,
                        "§5Sporefell",
                        "A forest of colossal, glowing mushrooms.",
                        "The spores shine with an otherworldly glow, hinting at ancient magic.",
                        60, 70, false, true, false)
                        .setAmbientMusic(MusicType.SPOREFELL_THEME),

                new Territory("ethereal-nexus",
                        new Location(Bukkit.getWorld("world"), 925, 0, -845),
                        null,
                        110,
                        "§5The Ethereal Nexus",
                        "A swirling vortex of pure magical energy.",
                        "Energy crackles here, a remnant of a forgotten arcane war.",
                        70, 80, false, true, false),

                new Territory("aetheris",
                        new Location(Bukkit.getWorld("world"), -135, 0, 780),
                        new Location(Bukkit.getWorld("world"), -180, 77, 794),
                        150,
                        "§dAetheris",
                        "An ethereal forest where celestial magic courses through the trees.",
                        "Trees hum with celestial energy, older than any mortal memory.",
                        70, 90, false, true, false)
                        .setAmbientMusic(MusicType.AETHERIS_THEME),

                new Territory("infernak",
                        new Location(Bukkit.getWorld("world"), 1000, 0, -630),
                        new Location(Bukkit.getWorld("world"), 933, 75, -645),
                        160,
                        "§cInfernak",
                        "A volcanic land forged in fire and brimstone.",
                        "Rivers of lava carve paths through the scorched land, ever restless.",
                        80, 90, false, true, false)
                        .setAmbientMusic(MusicType.INFERNAK_THEME),

                new Territory("tutorial-island",
                        new Location(Bukkit.getWorld("world"), 2518, 0, 2467),
                        new Location(Bukkit.getWorld("world"), 2500, 112, 2457.5, -90, 0),
                        200,
                        "§bTutorial Island",
                        "The first step of every hero.",
                        "Once a training ground for fledgling heroes, untouched by time.",
                        1, 1, false, true, false)
                        .setAmbientMusic(MusicType.TUTORIAL_ISLAND_THEME),


                new Territory("rogues-haven",
                        new Location(Bukkit.getWorld("world"), -130, 0, -1050),
                        null,
                        170,
                        "§cRogue's Haven",
                        "A lawless island brimming with untapped riches, abandoned by all who once lived here.",
                        "Once a sanctuary for traders and exiles alike, now only the bold dare claim its treasures.",
                        70, 90, true, true, false)
                        .setDropItemsOnDeath(true)
                        .setAmbientSounds(List.of(SoundType.ROGUE_HAVEN_AMBIENT_1, SoundType.ROGUE_HAVEN_AMBIENT_2))
                        .setAmbientMusic(MusicType.ROGUES_HAVEN_THEME),

                new Territory("rogues-haven",
                        new Location(Bukkit.getWorld("world"), 115, 0, -1050),
                        null,
                        170,
                        "§cRogue's Haven",
                        "A lawless island brimming with untapped riches, abandoned by all who once lived here.",
                        "Once a sanctuary for traders and exiles alike, now only the bold dare claim its treasures.",
                        70, 90, true, true, false)
                        .setDropItemsOnDeath(true).setAmbientSounds(List.of(SoundType.ROGUE_HAVEN_AMBIENT_1, SoundType.ROGUE_HAVEN_AMBIENT_2))
                        .setAmbientMusic(MusicType.ROGUES_HAVEN_THEME)


        );
    }
}
