package org.zuma.examplePlugin.domain.item.persistence;

import com.google.gson.Gson;
import org.bukkit.Color;
import org.bukkit.Material;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.heads.HeadTexture;
import org.zuma.examplePlugin.domain.item.constants.*;
import org.zuma.examplePlugin.domain.item.impl.EmptyContainer;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.meta.v1.*;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.SkillModifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.ArrayList;
import java.util.List;

public class ItemRepository {
    private List<AbstractItemMeta> cached = new ArrayList<>();
    private final Gson gson = new Gson();

    private VersionedJson toVersionedJson(Object obj, int version) {
        return new VersionedJson(gson.toJson(obj), version);
    }


    /* public void saveItems(List<AbstractItemMeta> items, HikariDataSource dataSource) {
        String sql = """
                    INSERT INTO items (
                        id, type, material, rarity, display_name, lore, head_texture, leather_color,
                        json_meta, json_meta_version
                    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Gson gson = gson;

            for (AbstractItemMeta item : items) {
                stmt.setInt(1, item.id());
                stmt.setString(2, item.type().name());
                stmt.setString(3, item.material().name());
                stmt.setString(4, item.rarity().name());
                stmt.setString(5, item.displayName());
                stmt.setString(6, gson.toJson(item.lore()));
                stmt.setString(7, item.headTexture());
                stmt.setString(8, String.valueOf(item.leatherColor() != null ? item.leatherColor().asRGB() : null));

                JsonMeta meta = item.jsonMeta();
                stmt.setString(9, meta != null ? meta.meta() : null);
                stmt.setInt(10, meta != null ? meta.version() : 1);

                stmt.addBatch();
            }

            stmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            // handle/log properly
        }
    }

    public List<AbstractItemMeta> loadItems(HikariDataSource dataSource) {
        String sql = "SELECT id, type, material, rarity, display_name, lore, head_texture, leather_color, json_meta, json_meta_version FROM items";
        List<AbstractItemMeta> items = new ArrayList<>();
        Gson gson = gson;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                ItemType type = ItemType.valueOf(rs.getString("type"));
                Material material = Material.valueOf(rs.getString("material"));
                ItemRarity rarity = ItemRarity.valueOf(rs.getString("rarity"));
                String displayName = rs.getString("display_name");

                // Deserialize lore JSON to List<String>
                String loreJson = rs.getString("lore");
                Type listOfStringType = new TypeToken<List<String>>() {
                }.getType();
                List<String> lore = gson.fromJson(loreJson, listOfStringType);

                String headTexture = rs.getString("head_texture");

                // Parse leather_color as Integer and convert to Color
                String leatherColorStr = rs.getString("leather_color");
                Color leatherColor = null;
                if (leatherColorStr != null && !leatherColorStr.equals("null")) {
                    try {
                        int rgb = Integer.parseInt(leatherColorStr);
                        leatherColor = Color.fromRGB(rgb);
                    } catch (NumberFormatException ignored) {
                        RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Could not load leather color for " + displayName + "! " + leatherColorStr + " is invalid.");
                    }
                }

                // Load JsonMeta
                String jsonMetaStr = rs.getString("json_meta");
                int jsonMetaVersion = rs.getInt("json_meta_version");
                JsonMeta jsonMeta = (jsonMetaStr != null) ? new JsonMeta(jsonMetaStr, jsonMetaVersion) : null;

                AbstractItemMeta item = new AbstractItemMeta(
                        type,
                        id,
                        material,
                        rarity,
                        displayName,
                        lore,
                        headTexture,
                        leatherColor,
                        jsonMeta
                );

                items.add(item);
            }

        } catch (SQLException e) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Could not load the items! ", e);
        }

        return items;
    }
    */
    public List<ItemEntity> getAll() {
        VersionedJson empty = new VersionedJson("", 1);

        return List.of(

                // ADMIN STICK! REMOVE BEFORE ALPHA!
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.ADMIN_OP_STICK.asInt(), Material.STICK, ItemRarity.COMMON, "Admin Stick", List.of("Bronze dagger, quick and light."), null, null),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.VERY_FAST, List.of(new SkillModifier(SkillType.HITPOINTS, 50)),
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_STAB, 999999),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 999999),
                                                new Modifier(ModifierType.ATTACK_CRUSH, 999999),
                                                new Modifier(ModifierType.ATTACK_SLASH, 999999)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 1))
                                )
                                , 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.COINS, ItemId.COINS.asInt(), Material.COAL, ItemRarity.NOT_APPLICABLE, "Coins", List.of("If you have this item in your inventory, please submit a bug report."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.CURRENCY, ItemId.PLATINUM_TOKEN.asInt(), Material.COAL, ItemRarity.RARE, "Platinum Token", List.of("A Platinum token that is worth 1k gold pieces."), null, null), null),

                // Arrows
                new ItemEntity(new AbstractItemMeta(ItemType.ARROW, ItemId.BRONZE_ARROW.asInt(), Material.ARROW, ItemRarity.COMMON, "Bronze Arrow", List.of("A basic bronze-tipped arrow.", "Useful for training ranged skills."), null, null), toVersionedJson(
                        new EquipableTypeMeta(null,
                                List.of(
                                        new Modifier(ModifierType.RANGED_STRENGTH, 1)
                                ), List.of(new SkillRequirement(SkillType.RANGED, 1))
                        ),
                        1)),

                new ItemEntity(new AbstractItemMeta(ItemType.ARROW, ItemId.IRON_ARROW.asInt(), Material.ARROW, ItemRarity.COMMON, "Iron Arrow", List.of("Stronger than bronze arrows.", "Commonly used by beginner archers."), null, null), toVersionedJson(
                        new EquipableTypeMeta(null,
                                List.of(
                                        new Modifier(ModifierType.RANGED_STRENGTH, 2)
                                ), List.of(new SkillRequirement(SkillType.RANGED, 10))
                        ),
                        1)),

                new ItemEntity(new AbstractItemMeta(ItemType.ARROW, ItemId.STEEL_ARROW.asInt(), Material.ARROW, ItemRarity.COMMON, "Steel Arrow", List.of("A sturdy steel-tipped arrow.", "Offers good damage and durability."), null, null), toVersionedJson(
                        new EquipableTypeMeta(null,
                                List.of(
                                        new Modifier(ModifierType.RANGED_STRENGTH, 3)
                                ), List.of(new SkillRequirement(SkillType.RANGED, 20))
                        ),
                        1)),

                new ItemEntity(new AbstractItemMeta(ItemType.ARROW, ItemId.MITHRIL_ARROW.asInt(), Material.ARROW, ItemRarity.UNCOMMON, "Mithril Arrow", List.of("Lightweight yet powerful.", "Favored by mid-level rangers."), null, null), toVersionedJson(
                        new EquipableTypeMeta(null,
                                List.of(
                                        new Modifier(ModifierType.RANGED_STRENGTH, 4)
                                ), List.of(new SkillRequirement(SkillType.RANGED, 30))
                        ),
                        1)),

                new ItemEntity(new AbstractItemMeta(ItemType.ARROW, ItemId.ADAMANT_ARROW.asInt(), Material.ARROW, ItemRarity.UNCOMMON, "Adamant Arrow", List.of("Forged from adamantite.", "High damage for experienced archers."), null, null), toVersionedJson(
                        new EquipableTypeMeta(null,
                                List.of(
                                        new Modifier(ModifierType.RANGED_STRENGTH, 6)
                                ), List.of(new SkillRequirement(SkillType.RANGED, 40))
                        ),
                        1)),

                new ItemEntity(new AbstractItemMeta(ItemType.ARROW, ItemId.RUNITE_ARROW.asInt(), Material.ARROW, ItemRarity.RARE, "Runite Arrow", List.of("Top-tier arrow made from runite.", "Delivers deadly ranged attacks."), null, null), toVersionedJson(
                        new EquipableTypeMeta(null,
                                List.of(
                                        new Modifier(ModifierType.RANGED_STRENGTH, 7)
                                ), List.of(new SkillRequirement(SkillType.RANGED, 50))
                        ),
                        1)),


                // Tools
                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.SMITHING_HAMMER.asInt(), Material.WOODEN_AXE, ItemRarity.COMMON, "Hammer", List.of("A sturdy hammer used for refining and forging equipment."), null, null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_CRUSH, 4),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 2)
                                        ), null),
                                1)),

                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.FLINT_AND_STEEL.asInt(), Material.FLINT_AND_STEEL, ItemRarity.COMMON, "Flint and Steel", List.of("A nice tool to start a fire."), null, null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        null, null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.SHEARS.asInt(), Material.SHEARS, ItemRarity.COMMON, "Shears", List.of("Used to trim wool or foliage."), null, null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        null, null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.CHISEL.asInt(), Material.WOODEN_AXE, ItemRarity.COMMON, "Chisel", List.of("Used for carving or shaping materials."), null, null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_STAB, 4),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 2)
                                        ), null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.KNIFE.asInt(), Material.WOODEN_AXE, ItemRarity.COMMON, "Knife", List.of("A small, sharp blade.", "Used for cutting, carving, and crafting."), null, null),
                        toVersionedJson(new EquipableTypeMeta(null, List.of(
                                new Modifier(ModifierType.ATTACK_SLASH, 6),
                                new Modifier(ModifierType.STRENGTH_BONUS, 2)
                        ), null), 1)),


                // Materials
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.COPPER_ORE.asInt(), Material.COAL, ItemRarity.COMMON, "Copper Ore", List.of("Raw copper ore, ready to be smelted into metal."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.TIN_ORE.asInt(), Material.COAL, ItemRarity.COMMON, "Tin Ore", List.of("Raw tin ore, ready to be smelted into metal."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.COAL_ORE.asInt(), Material.COAL, ItemRarity.COMMON, "Coal Ore", List.of("Unrefined coal ore, useful as a fuel source."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.IRON_ORE.asInt(), Material.COAL, ItemRarity.COMMON, "Iron Ore", List.of("Heavy iron ore, a basic material for crafting."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.MITHRIL_ORE.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Mithril Ore", List.of("Rare mithril ore, prized for its lightweight strength."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.ADAMANTITE_ORE.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Adamantite Ore", List.of("Tough adamantite ore, used for elite armor and weapons."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RUNITE_ORE.asInt(), Material.COAL, ItemRarity.RARE, "Runite Ore", List.of("Ancient runite ore, infused with magical properties."), null, null), null),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.BRONZE_BAR.asInt(), Material.COAL, ItemRarity.COMMON, "Bronze Bar", List.of("Smelted bronze bar, used to forge basic equipment."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.IRON_BAR.asInt(), Material.COAL, ItemRarity.COMMON, "Iron Bar", List.of("Solid iron bar, reliable for sturdy weapons and armor."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.STEEL_BAR.asInt(), Material.COAL, ItemRarity.COMMON, "Steel Bar", List.of("Refined steel bar, favored by seasoned blacksmiths."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.MITHRIL_BAR.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Mithril Bar", List.of("Light yet strong mithril bar, perfect for advanced gear."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.ADAMANTITE_BAR.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Adamantite Bar", List.of("Durable adamantite bar, for crafting superior items."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RUNITE_BAR.asInt(), Material.COAL, ItemRarity.RARE, "Runite Bar", List.of("Runite bar pulsing with arcane energy, highly prized."), null, null), null),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RAW_CHICKEN.asInt(), Material.COAL, ItemRarity.COMMON, "Raw Chicken", List.of("A slab of uncooked poultry, still fresh."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RAW_MEAT.asInt(), Material.COAL, ItemRarity.COMMON, "Raw Meat", List.of("Uncooked cut of meat, ideal for hearty meals."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RAW_HERRING.asInt(), Material.COAL, ItemRarity.COMMON, "Raw Herring", List.of("A slim, slippery river fish."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RAW_MINNOW.asInt(), Material.COAL, ItemRarity.COMMON, "Raw Minnow", List.of("A tiny fish, barely worth a bite."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RAW_TROUT.asInt(), Material.COAL, ItemRarity.COMMON, "Raw Trout", List.of("Caught fresh from the stream."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RAW_SALMON.asInt(), Material.COAL, ItemRarity.COMMON, "Raw Salmon", List.of("Firm and pink, ready to roast."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RAW_TUNA.asInt(), Material.COAL, ItemRarity.COMMON, "Raw Tuna", List.of("Heavy and dense sea catch."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RAW_CRAWLER.asInt(), Material.COAL, ItemRarity.COMMON, "Raw Crawler", List.of("Squirmy shelled creature from the reef."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RAW_PIKE.asInt(), Material.COAL, ItemRarity.COMMON, "Raw Pike", List.of("Aggressive lake fish with sharp bones."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RAW_EEL.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Raw Eel", List.of("Slippery and tough to handle."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RAW_SHARK.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Raw Shark", List.of("Muscle-bound predator, not to be eaten raw."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RAW_TURTLE.asInt(), Material.COAL, ItemRarity.RARE, "Raw Turtle", List.of("Its thick shell guards rich meat inside."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RAW_RAY.asInt(), Material.COAL, ItemRarity.RARE, "Raw Ray", List.of("Soft flesh beneath leathery wings."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.LOG.asInt(), Material.OAK_LOG, ItemRarity.COMMON, "Log", List.of("A basic log used for crafting and building."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.BIRCH_LOG.asInt(), Material.BIRCH_LOG, ItemRarity.COMMON, "Birch Log", List.of("A pale and smooth type of wood."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.MAPLE_LOG.asInt(), Material.ACACIA_LOG, ItemRarity.UNCOMMON, "Maple Log", List.of("A strong and versatile log with a reddish hue."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.YEW_LOG.asInt(), Material.DARK_OAK_LOG, ItemRarity.UNCOMMON, "Yew Log", List.of("A dense and durable wood prized in crafting."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.MAHOGANY_LOG.asInt(), Material.MANGROVE_LOG, ItemRarity.RARE, "Mahogany Log", List.of("A rich, dark wood valued for fine construction."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.MAGIC_LOG.asInt(), Material.CHERRY_LOG, ItemRarity.LEGENDARY, "Magic Log", List.of("Glows faintly with arcane energy, rare and powerful."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.ASHES.asInt(), Material.COAL, ItemRarity.COMMON, "Ashes", List.of("Fine powder left after fire consumes wood."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.MAGIC_ASHES.asInt(), Material.COAL, ItemRarity.RARE, "Magic Ashes", List.of("Glowing remnants infused with mysterious arcane power."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.BALL_OF_WOOL.asInt(), Material.COAL, ItemRarity.COMMON, "Ball of Wool", List.of("Spun from soft tufts of raw wool.", "Used in various crafting recipes."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.WOOL.asInt(), Material.COAL, ItemRarity.COMMON, "Wool", List.of("Soft tufts of freshly shorn wool", "Requires spinning before use."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.FLAX.asInt(), Material.COAL, ItemRarity.COMMON, "Flax", List.of("A bundle of fibrous plant stalks.", "Can be spun into bow string."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.BOW_STRING.asInt(), Material.COAL, ItemRarity.COMMON, "Bow String", List.of("Spun from processed flax.", "Used in bow crafting."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.MAGIC_BOW_STRING.asInt(), Material.COAL, ItemRarity.RARE, "Magic Bow String", List.of("Spun from flax and a magic ingredient.", "Used to string magic amulets or bows."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.ARROW_SHAFT.asInt(), Material.COAL, ItemRarity.COMMON, "Arrow Shaft", List.of("A basic shaft for making arrows.", "Needs feathers to complete."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.DRAGON_LEATHER.asInt(), Material.COAL, ItemRarity.RARE, "Dragon Leather", List.of("Tough and scaly leather from a dragon.", "Used to craft powerful armor."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.FEATHER.asInt(), Material.COAL, ItemRarity.COMMON, "Feather", List.of("Light and soft bird feather.", "Useful for fletching arrows."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.HARD_LEATHER.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Hard Leather", List.of("Treated for durability.", "Used to craft sturdy gear."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.HEADLESS_ARROW.asInt(), Material.COAL, ItemRarity.COMMON, "Headless Arrow", List.of("An unfinished arrow lacking a tip.", "Needs a metal head to be complete."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.LEATHER.asInt(), Material.COAL, ItemRarity.COMMON, "Leather", List.of("Tanned animal hide.", "Used in basic crafting and armor."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.BRONZE_ARROW_TIPS.asInt(), Material.COAL, ItemRarity.COMMON, "Bronze Arrowtips", List.of("Forged from bronze.", "Used to make basic arrows."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.IRON_ARROW_TIPS.asInt(), Material.COAL, ItemRarity.COMMON, "Iron Arrowtips", List.of("Sharpened iron points.", "Commonly used for training."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.STEEL_ARROW_TIPS.asInt(), Material.COAL, ItemRarity.COMMON, "Steel Arrowtips", List.of("Harder than iron.", "Used for stronger arrows."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.MITHRIL_ARROW_TIPS.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Mithril Arrowtips", List.of("Lightweight and sharp.", "Favored by skilled archers."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.ADAMANTITE_ARROW_TIPS.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Adamantite Arrowtips", List.of("Tough and deadly.", "Crafted for elite arrows."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RUNITE_ARROW_TIPS.asInt(), Material.COAL, ItemRarity.RARE, "Runite Arrowtips", List.of("Forged from rare runite.", "Used in mastercrafted ammunition."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.SHORTBOW_UNFINISHED.asInt(), Material.COAL, ItemRarity.COMMON, "Shortbow (unfinished)", List.of("An unstrung shortbow.", "Needs a bowstring to complete."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.LONGBOW_UNFINISHED.asInt(), Material.COAL, ItemRarity.COMMON, "Longbow (unfinished)", List.of("An unstrung longbow.", "Needs a bowstring to complete."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.BIRCH_SHORTBOW_UNFINISHED.asInt(), Material.COAL, ItemRarity.COMMON, "Birch Shortbow (unfinished)", List.of("An unstrung birch shortbow.", "Needs a bowstring to complete."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.BIRCH_LONGBOW_UNFINISHED.asInt(), Material.COAL, ItemRarity.COMMON, "Birch Longbow (unfinished)", List.of("An unstrung birch longbow.", "Needs a bowstring to complete."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.MAPLE_LONGBOW_UNFINISHED.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Maple Longbow (unfinished)", List.of("An unstrung maple longbow.", "Needs a bowstring to complete."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.MAPLE_SHORTBOW_UNFINISHED.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Maple Shortbow (unfinished)", List.of("An unstrung maple shortbow.", "Needs a bowstring to complete."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.YEW_SHORTBOW_UNFINISHED.asInt(), Material.COAL, ItemRarity.RARE, "Yew Shortbow (unfinished)", List.of("An unstrung yew shortbow.", "Needs a bowstring to complete."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.YEW_LONGBOW_UNFINISHED.asInt(), Material.COAL, ItemRarity.RARE, "Yew Longbow (unfinished)", List.of("An unstrung yew longbow.", "Needs a bowstring to complete."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.MAGIC_SHORTBOW_UNFINISHED.asInt(), Material.COAL, ItemRarity.COMMON, "Magic Shortbow (unfinished)", List.of("An unstrung magic shortbow.", "Needs a bowstring to complete."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.MAGIC_LONGBOW_UNFINISHED.asInt(), Material.COAL, ItemRarity.COMMON, "Magic Longbow (unfinished)", List.of("An unstrung magic longbow.", "Needs a bowstring to complete."), null, null), null),

                new ItemEntity(new AbstractItemMeta(ItemType.MISC, ItemId.BONES.asInt(), Material.COAL, ItemRarity.COMMON, "Bones", List.of("Remains of a fallen creature."), null, null), null),


                new ItemEntity(new AbstractItemMeta(ItemType.EMPTY_CONTAINER, ItemId.EMPTY_POT.asInt(), Material.COAL, ItemRarity.COMMON, "Empty Pot", List.of("A regular pot, made of clay."), null, null),
                        toVersionedJson(new EmptyContainerMeta(List.of(
                                new EmptyContainer.EmptyContainerConfig(EmptyContainer.InputType.FLOUR, ItemId.POT_OF_FLOUR.asInt())
                        )), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.POT_OF_FLOUR.asInt(), Material.COAL, ItemRarity.COMMON, "Pot of flour", List.of("A pot filled with flour."), null, null), null),

                new ItemEntity(new AbstractItemMeta(ItemType.EMPTY_CONTAINER, ItemId.EMPTY_BUCKET.asInt(), Material.COAL, ItemRarity.COMMON, "Empty Bucket", List.of("A regular bucket, made of metal."), null, null),
                        toVersionedJson(new EmptyContainerMeta(List.of(
                                new EmptyContainer.EmptyContainerConfig(EmptyContainer.InputType.WATER, ItemId.BUCKET_OF_WATER.asInt())
                        )), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.BUCKET_OF_WATER.asInt(), Material.COAL, ItemRarity.COMMON, "Bucket of water", List.of("A bucket filled with water."), null, null), null),


                new ItemEntity(new AbstractItemMeta(ItemType.EMPTY_CONTAINER, ItemId.EMPTY_VIAL.asInt(), Material.COAL, ItemRarity.COMMON, "Empty Vial", List.of("A regular vial, made of glass."), null, null),
                        toVersionedJson(new EmptyContainerMeta(List.of(
                                new EmptyContainer.EmptyContainerConfig(EmptyContainer.InputType.WATER, ItemId.VIAL_OF_WATER.asInt()),
                                new EmptyContainer.EmptyContainerConfig(EmptyContainer.InputType.LAVA, ItemId.VIAL_OF_LAVA.asInt())
                        )), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.VIAL_OF_WATER.asInt(), Material.COAL, ItemRarity.COMMON, "Vial of water", List.of("A vial filled with water."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.VIAL_OF_LAVA.asInt(), Material.COAL, ItemRarity.COMMON, "Vial of lava", List.of("A vial filled with lava."), null, null), null),

                // Fairy food
                new ItemEntity(new AbstractItemMeta(ItemType.FAIRY_FOOD, ItemId.REGULAR_FAIRY_RATION.asInt(), Material.COAL, ItemRarity.RARE, "Fairy Ration", List.of("Regular food for fairies.", "Keeps them strong and energized."), null, null),
                        toVersionedJson(new FairyFoodMeta(15), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FAIRY_FOOD, ItemId.FINE_FAIRY_RATION.asInt(), Material.COAL, ItemRarity.EPIC, "Fine Fairy Ration", List.of("Fine food for fairies.", "Keeps them strong and energized."), null, null),
                        toVersionedJson(new FairyFoodMeta(40), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FAIRY_FOOD, ItemId.SUPERIOR_FAIRY_RATION.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Superior Fairy Ration", List.of("Superior food for fairies.", "Keeps them strong and energized."), null, null),
                        toVersionedJson(new FairyFoodMeta(80), 1)),


                // Fairy
                new ItemEntity(new AbstractItemMeta(ItemType.FAIRY, ItemId.FAIRY_OF_HOPE.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Fairy of Hope", List.of("A gentle sprite whose presence inspires courage and renewal."), null, null),
                        toVersionedJson(new FairyMeta("cubee-swamps", 1), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FAIRY, ItemId.FAIRY_OF_LIGHT.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Fairy of Light", List.of("A radiant fairy that illuminates the darkest paths with pure brilliance."), null, null),
                        toVersionedJson(new FairyMeta("cubee-good", 1), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FAIRY, ItemId.FAIRY_OF_SILENCE.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Fairy of Silence", List.of("Whispers softly in the quiet, muffling sound and calm unrest."), null, null),
                        toVersionedJson(new FairyMeta("cubee-stone", 1), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FAIRY, ItemId.FAIRY_OF_DARKNESS.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Fairy of Darkness", List.of("A shadowy companion that thrives where light dares not linger."), null, null),
                        toVersionedJson(new FairyMeta("cubee-skeleton", 1), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FAIRY, ItemId.FAIRY_OF_FROST.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Fairy of Frost", List.of("A chilly wisp that freezes the air and slows the fiercest foes."), null, null),
                        toVersionedJson(new FairyMeta("cubee-snow", 1), 1)),

                // Fairy Upgrade
                new ItemEntity(new AbstractItemMeta(ItemType.FAIRY_FRUIT, ItemId.FAIRY_ATTACK_UPGRADE.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Blazing Apple",
                        List.of("A crisp red apple with a fiery aura.", "Empowers your fairy to enhance your Attack."), null, null),
                        toVersionedJson(new FairyFruitMeta(null, List.of(new SkillModifier(SkillType.ATTACK, 1))), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FAIRY_FRUIT, ItemId.FAIRY_DEFENCE_UPGRADE.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Stoneblue Berry",
                        List.of("A cluster of enchanted blueberries, dense with magic.", "Strengthens your Defence through your fairy's help."), null, null),
                        toVersionedJson(new FairyFruitMeta(null, List.of(new SkillModifier(SkillType.DEFENCE, 1))), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FAIRY_FRUIT, ItemId.FAIRY_STRENGTH_UPGRADE.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Inferno Dragonfruit",
                        List.of("A rare dragonfruit pulsing with raw power.", "Bolsters your Strength using your bonded fairy."), null, null),
                        toVersionedJson(new FairyFruitMeta(null, List.of(new SkillModifier(SkillType.STRENGTH, 1))), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FAIRY_FRUIT, ItemId.FAIRY_HITPOINTS_UPGRADE.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Golden Marmelade",
                        List.of("A jar of radiant marmelade, sweet and soothing.", "Boosts your Hitpoints through your fairy’s magic."), null, null),
                        toVersionedJson(new FairyFruitMeta(null, List.of(new SkillModifier(SkillType.HITPOINTS, 1))), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FAIRY_FRUIT, ItemId.FAIRY_RANGE_UPGRADE.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Whispering Grapes",
                        List.of("A bunch of shimmering grapes that hum with energy.", "Improves your Ranged skill via fairy essence."), null, null),
                        toVersionedJson(new FairyFruitMeta(null, List.of(new SkillModifier(SkillType.RANGED, 1))), 1)),

                // Scrolls
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL_SCROLL, ItemId.FUSION_SCROLL.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Fusion Scroll", List.of("A mystical scroll used to embed gems into gear.", "Essential for upgrading equipment with gem-based attributes."), null, null), null),


                // Gems
                new ItemEntity(new AbstractItemMeta(ItemType.GEM, ItemId.BRUTALITE_CORE.asInt(), Material.COAL, ItemRarity.MYTHIC, "Brutalite Core",
                        List.of("A gem that increases the wearer’s physical power, boosting strength attributes."), null, null),
                        toVersionedJson(
                                new GemMeta(null, List.of(new Modifier(ModifierType.STRENGTH_BONUS, 8))), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.GEM, ItemId.OBSIDIAN_BULWARK.asInt(), Material.COAL, ItemRarity.MYTHIC, "Obsidian Bulwark",
                        List.of("A gem that bolsters resilience, reinforcing the wearer’s defenses."), null, null),
                        toVersionedJson(
                                new GemMeta(null, List.of(
                                        new Modifier(ModifierType.DEFENCE_CRUSH, 6),
                                        new Modifier(ModifierType.DEFENCE_SLASH, 6),
                                        new Modifier(ModifierType.DEFENCE_STAB, 6)
                                )), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.GEM, ItemId.BLADESTONE.asInt(), Material.COAL, ItemRarity.MYTHIC, "Bladestone",
                        List.of("A gem that enhances offensive prowess, increasing successful hit chance."), null, null),
                        toVersionedJson(
                                new GemMeta(null, List.of(
                                        new Modifier(ModifierType.ATTACK_STAB, 6),
                                        new Modifier(ModifierType.ATTACK_SLASH, 6),
                                        new Modifier(ModifierType.ATTACK_CRUSH, 6)
                                )), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.GEM, ItemId.ARCHERS_EMBER.asInt(), Material.COAL, ItemRarity.MYTHIC, "Archer’s Ember",
                        List.of("A gem that sharpens precision and power at range, boosting ranged strength."), null, null),
                        toVersionedJson(
                                new GemMeta(null, List.of(new Modifier(ModifierType.RANGED_STRENGTH, 8))), 1)),

                // Misc
                new ItemEntity(new AbstractItemMeta(ItemType.MISC, ItemId.BURNT_FOOD.asInt(), Material.COAL, ItemRarity.NOT_APPLICABLE, "Charred Remains", List.of("All that's left after fire claims its meal."), null, null), null),

                // Food
                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_CHICKEN.asInt(), Material.COOKED_SALMON, ItemRarity.COMMON, "Cooked Chicken",
                        List.of("Tender meat roasted to perfection."), null, null), toVersionedJson(new FoodTypeMeta(3), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_MEAT.asInt(), Material.COOKED_SALMON, ItemRarity.COMMON, "Cooked Meat",
                        List.of("Cooked to a golden brown, still juicy inside."), null, null), toVersionedJson(new FoodTypeMeta(3), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_HERRING.asInt(), Material.COOKED_SALMON, ItemRarity.COMMON, "Cooked Herring",
                        List.of("Lightly roasted river fish, a quick meal."), null, null), toVersionedJson(new FoodTypeMeta(3), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_MINNOW.asInt(), Material.COOKED_SALMON, ItemRarity.COMMON, "Cooked Minnow",
                        List.of("Tiny fish, crisped over a fire."), null, null), toVersionedJson(new FoodTypeMeta(1), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_TROUT.asInt(), Material.COOKED_SALMON, ItemRarity.COMMON, "Cooked Trout",
                        List.of("Flaky and rich, a river-dweller's feast."), null, null), toVersionedJson(new FoodTypeMeta(7), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_SALMON.asInt(), Material.COOKED_SALMON, ItemRarity.COMMON, "Cooked Salmon",
                        List.of("Bold flavor, pink flesh, campfire ready."), null, null), toVersionedJson(new FoodTypeMeta(9), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_TUNA.asInt(), Material.COOKED_SALMON, ItemRarity.COMMON, "Cooked Tuna",
                        List.of("Dense meat, satisfying and nutritious."), null, null), toVersionedJson(new FoodTypeMeta(10), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_CRAWLER.asInt(), Material.COOKED_SALMON, ItemRarity.COMMON, "Cooked Crawler",
                        List.of("Charred crustacean, crunchy and filling."), null, null), toVersionedJson(new FoodTypeMeta(12), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_PIKE.asInt(), Material.COOKED_SALMON, ItemRarity.COMMON, "Cooked Pike",
                        List.of("A sharp-finned delicacy from cold waters."), null, null), toVersionedJson(new FoodTypeMeta(14), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_EEL.asInt(), Material.COOKED_SALMON, ItemRarity.UNCOMMON, "Cooked Eel",
                        List.of("Grilled serpent of the deep marshes."), null, null), toVersionedJson(new FoodTypeMeta(16), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_SHARK.asInt(), Material.COOKED_SALMON, ItemRarity.UNCOMMON, "Cooked Shark",
                        List.of("Meaty apex predator, expertly seared."), null, null), toVersionedJson(new FoodTypeMeta(20), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_TURTLE.asInt(), Material.COOKED_SALMON, ItemRarity.RARE, "Cooked Turtle",
                        List.of("Tender shell beast meat, slow-cooked to perfection."), null, null), toVersionedJson(new FoodTypeMeta(21), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_RAY.asInt(), Material.COOKED_SALMON, ItemRarity.RARE, "Cooked Ray",
                        List.of("Wings of the deep skyfish, rich in nutrients."), null, null), toVersionedJson(new FoodTypeMeta(22), 1)),

                // Potions
                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.DEFENCE_POTION_I.asInt(), Material.MILK_BUCKET, ItemRarity.RARE, "Defence Potion I",
                        List.of("Increases your defense temporarily.", "Brewed by skilled alchemists."), null, null), toVersionedJson(new PotionMeta(3, 3, "Potion effects...", CustomModelId.GREEN_POTION), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.ATTACK_POTION_I.asInt(), Material.MILK_BUCKET, ItemRarity.RARE, "Attack Potion I",
                        List.of("Boosts your attack power.", "Unleash your fury in battle."), null, null), toVersionedJson(new PotionMeta(3, 3, "Potion effects...", CustomModelId.BLUE_POTION), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.MAGIC_POTION_I.asInt(), Material.MILK_BUCKET, ItemRarity.RARE, "Magic Potion I",
                        List.of("Enhances your magical abilities.", "A favorite among mages."), null, null), toVersionedJson(new PotionMeta(3, 3, "Potion effects...", CustomModelId.CYAN_POTION), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.HEALTH_POTION_I.asInt(), Material.MILK_BUCKET, ItemRarity.RARE, "Health Potion I",
                        List.of("Restores a small amount of health.", "Always good to have a few."), null, null), toVersionedJson(new PotionMeta(3, 3, "Potion effects...", CustomModelId.RED_POTION), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.STRENGTH_POTION_I.asInt(), Material.MILK_BUCKET, ItemRarity.RARE, "Strength Potion I",
                        List.of("Temporarily increases strength.", "Tear through enemies with ease."), null, null), toVersionedJson(new PotionMeta(3, 3, "Potion effects...", CustomModelId.YELLOW_POTION), 1)),


                // Tools
                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.BRONZE_PICKAXE.asInt(), Material.WOODEN_PICKAXE, ItemRarity.COMMON, "Bronze Pickaxe", List.of("Bronze pickaxe, basic mining tool."), null, null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(new Modifier(ModifierType.MINING_BOOST, 1)),
                                        List.of(new SkillRequirement(SkillType.MINING, 1))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.IRON_PICKAXE.asInt(), Material.WOODEN_PICKAXE, ItemRarity.COMMON, "Iron Pickaxe", List.of("Iron pickaxe, mines ores with ease."), null, null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(new Modifier(ModifierType.MINING_BOOST, 1.1)),
                                        List.of(new SkillRequirement(SkillType.MINING, 1))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.STEEL_PICKAXE.asInt(), Material.WOODEN_PICKAXE, ItemRarity.COMMON, "Steel Pickaxe", List.of("Steel pickaxe, efficient and strong."), null, null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(new Modifier(ModifierType.MINING_BOOST, 1.2)),
                                        List.of(new SkillRequirement(SkillType.MINING, 6))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.MITHRIL_PICKAXE.asInt(), Material.WOODEN_PICKAXE, ItemRarity.UNCOMMON, "Mithril Pickaxe", List.of("Mithril pickaxe, fast and durable."), null, null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(new Modifier(ModifierType.MINING_BOOST, 1.3)),
                                        List.of(new SkillRequirement(SkillType.MINING, 21))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.ADAMANTITE_PICKAXE.asInt(), Material.WOODEN_PICKAXE, ItemRarity.UNCOMMON, "Adamantite Pickaxe", List.of("Adamantite pickaxe, mines the hardest ores."), null, null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(new Modifier(ModifierType.MINING_BOOST, 1.5)),
                                        List.of(new SkillRequirement(SkillType.MINING, 31))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.RUNITE_PICKAXE.asInt(), Material.WOODEN_PICKAXE, ItemRarity.RARE, "Runite Pickaxe", List.of("Runite pickaxe, enchanted for maximum yield."), null, null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(new Modifier(ModifierType.MINING_BOOST, 1.8)),
                                        List.of(new SkillRequirement(SkillType.MINING, 41))
                                )
                                , 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.BRONZE_AXE.asInt(), Material.WOODEN_AXE, ItemRarity.COMMON, "Bronze axe", List.of("Bronze axe, useful for combat and chopping."), null, null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(new Modifier(ModifierType.WOODCUTTING_BOOST, 1)),
                                        List.of(new SkillRequirement(SkillType.WOODCUTTING, 1))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.IRON_AXE.asInt(), Material.WOODEN_AXE, ItemRarity.COMMON, "Iron axe", List.of("Iron axe, sturdy and reliable."), null, null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(new Modifier(ModifierType.WOODCUTTING_BOOST, 1.1)),
                                        List.of(new SkillRequirement(SkillType.WOODCUTTING, 1))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.STEEL_AXE.asInt(), Material.WOODEN_AXE, ItemRarity.COMMON, "Steel axe", List.of("Steel axe, stronger and more durable."), null, null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(new Modifier(ModifierType.WOODCUTTING_BOOST, 1.2)),
                                        List.of(new SkillRequirement(SkillType.WOODCUTTING, 6))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.MITHRIL_AXE.asInt(), Material.WOODEN_AXE, ItemRarity.UNCOMMON, "Mithril axe", List.of("Mithril axe, light and efficient."), null, null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(new Modifier(ModifierType.WOODCUTTING_BOOST, 1.3)),
                                        List.of(new SkillRequirement(SkillType.WOODCUTTING, 21))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.ADAMANTITE_AXE.asInt(), Material.WOODEN_AXE, ItemRarity.UNCOMMON, "Adamantite axe", List.of("Adamantite axe, built to last."), null, null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(new Modifier(ModifierType.WOODCUTTING_BOOST, 1.5)),
                                        List.of(new SkillRequirement(SkillType.WOODCUTTING, 31))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.RUNITE_AXE.asInt(), Material.WOODEN_AXE, ItemRarity.RARE, "Runite axe", List.of("Runite axe glowing with magical energy."), null, null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(new Modifier(ModifierType.WOODCUTTING_BOOST, 1.8)),
                                        List.of(new SkillRequirement(SkillType.WOODCUTTING, 41))
                                )
                                , 1)
                ),

                // Fishing
                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.FISHING_NET.asInt(), Material.COAL, ItemRarity.COMMON, "Fishing Net",
                        List.of("A durable net for catching small fish like herring and minnows."), null, null), toVersionedJson(
                        new EquipableTypeMeta(null,
                                List.of(
                                        new Modifier(ModifierType.BASIC_FISHING, 1)
                                ),
                                List.of(
                                        new SkillRequirement(SkillType.FISHING, 1)
                                ))
                        , 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.FISHING_ROD.asInt(), Material.COAL, ItemRarity.COMMON, "Fishing Rod",
                        List.of("A reliable fishing rod suited for catching medium-sized fish such as trout and salmon."), null, null), toVersionedJson(
                        new EquipableTypeMeta(null,
                                List.of(
                                        new Modifier(ModifierType.REGULAR_FISHING, 1)
                                ),
                                List.of(
                                        new SkillRequirement(SkillType.FISHING, 15)
                                ))
                        , 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.REINFORCED_FISHING_ROD.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Reinforced Fishing Rod",
                        List.of("A sturdy rod designed for challenging catches like sharks and other deepwater fish."), null, null), toVersionedJson(
                        new EquipableTypeMeta(null,
                                List.of(
                                        new Modifier(ModifierType.ADVANCED_FISHING, 1)
                                ),
                                List.of(
                                        new SkillRequirement(SkillType.FISHING, 75)
                                ))
                        , 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.FISHING_TRAP.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Fishing Trap",
                        List.of("A baited trap used to catch slow-moving aquatic creatures like turtles and rays."), null, null), toVersionedJson(
                        new EquipableTypeMeta(null,
                                List.of(
                                        new Modifier(ModifierType.TRAP_FISHING, 1)
                                ),
                                List.of(
                                        new SkillRequirement(SkillType.FISHING, 85)
                                ))
                        , 1)),


                // Armor - Helmets
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.COIF.asInt(), Material.LEATHER_HELMET, ItemRarity.COMMON, "Coif", List.of("Simple leather helmet offering minimal head protection."), HeadTexture.BRONZE_MED_HELM.asBase64(), Color.fromRGB(74, 56, 7)),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 2),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 3),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 1),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 3),
                                                new Modifier(ModifierType.DEFENCE_RANGED, 6)
                                        ),
                                        null
                                ), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.COWL.asInt(), Material.LEATHER_HELMET, ItemRarity.COMMON, "Cowl", List.of("A light hood offering minimal protection."), HeadTexture.BRONZE_MED_HELM.asBase64(), Color.fromRGB(74, 56, 7)),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 2),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 3),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 1),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 5),
                                                new Modifier(ModifierType.DEFENCE_RANGED, 8)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 10))
                                ), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.BRONZE_MEDHELM.asInt(), Material.PLAYER_HEAD, ItemRarity.COMMON, "Bronze Med Helm", List.of("Simple bronze helmet offering minimal head protection."), HeadTexture.BRONZE_MED_HELM.asBase64(), Color.fromRGB(210, 170, 120)),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(new Modifier(ModifierType.DEFENCE_STAB, 1), new Modifier(ModifierType.DEFENCE_SLASH, 1), new Modifier(ModifierType.DEFENCE_CRUSH, 1)),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.IRON_MEDHELM.asInt(), Material.PLAYER_HEAD, ItemRarity.COMMON, "Iron Med Helm", List.of("Sturdy iron helmet to guard against blows."), HeadTexture.IRON_MED_HELM.asBase64(), null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(new Modifier(ModifierType.DEFENCE_STAB, 3), new Modifier(ModifierType.DEFENCE_SLASH, 4), new Modifier(ModifierType.DEFENCE_CRUSH, 2)),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.STEEL_MEDHELM.asInt(), Material.PLAYER_HEAD, ItemRarity.COMMON, "Steel Med Helm", List.of("Well-crafted steel helmet for enhanced defense."), HeadTexture.STEEL_MED_HELM.asBase64(), null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(new Modifier(ModifierType.DEFENCE_STAB, 5), new Modifier(ModifierType.DEFENCE_SLASH, 6), new Modifier(ModifierType.DEFENCE_CRUSH, 5)),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 5))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.MITHRIL_MEDHELM.asInt(), Material.PLAYER_HEAD, ItemRarity.UNCOMMON, "Mithril Med Helm", List.of("Lightweight mithril helmet, combining agility and defense."), HeadTexture.MITHRIL_MED_HELM.asBase64(), null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(new Modifier(ModifierType.DEFENCE_STAB, 8), new Modifier(ModifierType.DEFENCE_SLASH, 9), new Modifier(ModifierType.DEFENCE_CRUSH, 7)),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 20))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.ADAMANT_MEDHELM.asInt(), Material.PLAYER_HEAD, ItemRarity.UNCOMMON, "Adamantite Med Helm", List.of("Heavy adamantite helmet, favored by elite warriors."), HeadTexture.ADAMANT_MED_HELM.asBase64(), null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(new Modifier(ModifierType.DEFENCE_STAB, 12), new Modifier(ModifierType.DEFENCE_SLASH, 13), new Modifier(ModifierType.DEFENCE_CRUSH, 11)),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 30))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.RUNE_MEDHELM.asInt(), Material.PLAYER_HEAD, ItemRarity.RARE, "Runite Med Helm", List.of("Runite helmet radiating magical protection."), HeadTexture.RUNE_MED_HELM.asBase64(), null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(new Modifier(ModifierType.DEFENCE_STAB, 18), new Modifier(ModifierType.DEFENCE_SLASH, 19), new Modifier(ModifierType.DEFENCE_CRUSH, 17)),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 40))
                                )
                                , 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.BRONZE_FULLHELM.asInt(), Material.PLAYER_HEAD, ItemRarity.COMMON, "Bronze Full Helm", List.of("A full bronze helmet that offers basic protection."), HeadTexture.BRONZE_FULL_HELM.asBase64(), Color.fromRGB(210, 170, 120)),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 2),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 3),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 4)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1))
                                ), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.IRON_FULLHELM.asInt(), Material.PLAYER_HEAD, ItemRarity.COMMON, "Iron Full Helm", List.of("A sturdy iron full helm for better protection."), HeadTexture.IRON_FULL_HELM.asBase64(), null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 5),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 6),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 7)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1))
                                ), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.STEEL_FULLHELM.asInt(), Material.PLAYER_HEAD, ItemRarity.COMMON, "Steel Full Helm", List.of("A well-made steel full helm that offers decent defense."), HeadTexture.STEEL_FULL_HELM.asBase64(), null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 7),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 8),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 9)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 5))
                                ), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.MITHRIL_FULLHELM.asInt(), Material.PLAYER_HEAD, ItemRarity.UNCOMMON, "Mithril Full Helm", List.of("A lightweight mithril full helm with solid defense."), HeadTexture.MITHRIL_FULL_HELM.asBase64(), null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 10),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 11),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 12)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 20))
                                ), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.ADAMANT_FULLHELM.asInt(), Material.PLAYER_HEAD, ItemRarity.UNCOMMON, "Adamant Full Helm", List.of("A heavy adamant full helm for superior defense."), HeadTexture.ADAMANT_FULL_HELM.asBase64(), null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 14),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 15),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 16)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 30))
                                ), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.RUNE_FULLHELM.asInt(), Material.PLAYER_HEAD, ItemRarity.RARE, "Rune Full Helm", List.of("A full helmet forged from runite, offering excellent defense."), HeadTexture.RUNE_FULL_HELM.asBase64(), null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 20),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 22),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 21)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 40))
                                ), 1)
                ),


                // Armor - Chestplates
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.LEATHER_BODY.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Leather Body", List.of("Basic leather torso armor."), HeadTexture.BRONZE_MED_HELM.asBase64(), Color.fromRGB(74, 56, 7)),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 3),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 4),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 2),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 1),
                                                new Modifier(ModifierType.DEFENCE_RANGED, 8)
                                        ),
                                        null
                                ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.HARD_LEATHER_BODY.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Hard Leather Body", List.of("Stiffened leather torso protection."), HeadTexture.BRONZE_MED_HELM.asBase64(), Color.fromRGB(33, 15, 2)),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 7),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 7),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 7),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 8),
                                                new Modifier(ModifierType.DEFENCE_RANGED, 12)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 10))
                                ), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.STUDDED_LEATHER_BODY.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Studded Body", List.of("Leather armor reinforced with studs."), HeadTexture.BRONZE_MED_HELM.asBase64(), Color.fromRGB(38, 29, 15)),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 8),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 8),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 7),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 7),
                                                new Modifier(ModifierType.DEFENCE_RANGED, 16)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 20))
                                ), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.GREEN_DHIDE_BODY.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Green D'hide Body", List.of("Torso armor made of green dragonhide."), HeadTexture.BRONZE_MED_HELM.asBase64(), Color.fromRGB(12, 130, 12)),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 15),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 15),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 14),
                                                new Modifier(ModifierType.DEFENCE_MAGIC, -6),
                                                new Modifier(ModifierType.DEFENCE_RANGED, 30),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 20)
                                        ),
                                        List.of(
                                                new SkillRequirement(SkillType.RANGED, 40),
                                                new SkillRequirement(SkillType.DEFENCE, 40)
                                        )
                                ), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.BRONZE_BODY.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Bronze Body", List.of("Basic bronze chestplate, shields vital organs."), null, Color.fromRGB(210, 170, 120)),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 6),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 8),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 7)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.IRON_BODY.asInt(), Material.CHAINMAIL_CHESTPLATE, ItemRarity.COMMON, "Iron Body", List.of("Iron chestplate providing solid body armor."), null, null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 10),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 13),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 11)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.STEEL_BODY.asInt(), Material.IRON_CHESTPLATE, ItemRarity.COMMON, "Steel Body", List.of("Tough steel chestplate for frontline combat."), null, null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 18),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 19),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 17)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 5))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.MITHRIL_BODY.asInt(), Material.GOLDEN_CHESTPLATE, ItemRarity.UNCOMMON, "Mithril Body", List.of("Mithril chestplate offering balance of speed and defense."), null, null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 24),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 25),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 23)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 20))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.ADAMANTITE_BODY.asInt(), Material.DIAMOND_CHESTPLATE, ItemRarity.UNCOMMON, "Adamantite Body", List.of("Heavy adamantite chestplate for supreme protection."), null, null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 30),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 31),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 29)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 30))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.RUNITE_BODY.asInt(), Material.NETHERITE_CHESTPLATE, ItemRarity.RARE, "Runite Body", List.of("Runite chestplate enchanted for extra resilience."), null, null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 40),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 42),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 38)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 40))
                                )
                                , 1)
                ),

                // Armor - platelegs
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.LEATHER_LEGGINGS.asInt(), Material.LEATHER_LEGGINGS, ItemRarity.COMMON, "Leather Chaps", List.of("Basic leather leg armor."), HeadTexture.BRONZE_MED_HELM.asBase64(), Color.fromRGB(74, 56, 7)),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 2),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 3),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 2),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 2),
                                                new Modifier(ModifierType.DEFENCE_RANGED, 8)
                                        ),
                                        null
                                ), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.HARD_LEATHER_LEGGINGS.asInt(), Material.LEATHER_LEGGINGS, ItemRarity.COMMON, "Hard Leather Chaps", List.of("Stiffened leather leg protection."), HeadTexture.BRONZE_MED_HELM.asBase64(), Color.fromRGB(33, 15, 2)),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 7),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 7),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 6),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 5),
                                                new Modifier(ModifierType.DEFENCE_RANGED, 12)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 10))
                                ), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.STUDDED_LEATHER_LEGGINGS.asInt(), Material.LEATHER_LEGGINGS, ItemRarity.COMMON, "Studded Chaps", List.of("Leather chaps reinforced with studs."), HeadTexture.BRONZE_MED_HELM.asBase64(), Color.fromRGB(38, 29, 15)),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 8),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 8),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 7),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 6),
                                                new Modifier(ModifierType.DEFENCE_RANGED, 14)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 20))
                                ), 1)
                ),
                new ItemEntity(
                        new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.GREEN_DHIDE_LEGGINGS.asInt(), Material.LEATHER_LEGGINGS, ItemRarity.COMMON, "Green D'hide Chaps", List.of("Leg armor made of green dragonhide."), HeadTexture.BRONZE_MED_HELM.asBase64(), Color.fromRGB(12, 130, 12)),
                        toVersionedJson(
                                new EquipableTypeMeta(null, List.of(
                                        new Modifier(ModifierType.DEFENCE_STAB, 6),
                                        new Modifier(ModifierType.DEFENCE_SLASH, 8),
                                        new Modifier(ModifierType.DEFENCE_CRUSH, 7),
                                        new Modifier(ModifierType.DEFENCE_MAGIC, -6),
                                        new Modifier(ModifierType.RANGED_ACCURACY, 15),
                                        new Modifier(ModifierType.DEFENCE_RANGED, 20)
                                ), List.of(new SkillRequirement(SkillType.RANGED, 40))), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.BRONZE_PLATELEGS.asInt(), Material.LEATHER_LEGGINGS, ItemRarity.COMMON, "Bronze Platelegs", List.of("Bronze platelegs, basic leg armor."), null, Color.fromRGB(210, 170, 120)),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 4),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 5),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 3)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.IRON_PLATELEGS.asInt(), Material.CHAINMAIL_LEGGINGS, ItemRarity.COMMON, "Iron Platelegs", List.of("Iron platelegs for reliable leg protection."), null, null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 7),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 8),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 6)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.STEEL_PLATELEGS.asInt(), Material.IRON_LEGGINGS, ItemRarity.COMMON, "Steel Platelegs", List.of("Steel platelegs favored by seasoned fighters."), null, null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 12),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 13),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 11)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 5))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.MITHRIL_PLATELEGS.asInt(), Material.GOLDEN_LEGGINGS, ItemRarity.UNCOMMON, "Mithril Platelegs", List.of("Mithril platelegs combining speed and defense."), null, null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 16),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 17),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 15)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 20))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.ADAMANTITE_PLATELEGS.asInt(), Material.DIAMOND_LEGGINGS, ItemRarity.UNCOMMON, "Adamantite Platelegs", List.of("Adamantite platelegs, thick and durable."), null, null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 21),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 22),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 20)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 30))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.RUNITE_PLATELEGS.asInt(), Material.NETHERITE_LEGGINGS, ItemRarity.RARE, "Runite Platelegs", List.of("Runite platelegs shimmering with magical energy."), null, null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 28),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 30),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 26)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 40))
                                )
                                , 1)
                ),

                // Armor - Boots
                new ItemEntity(
                        new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.LEATHER_BOOTS.asInt(), Material.LEATHER_BOOTS, ItemRarity.COMMON, "Leather Boots", List.of("Basic leather footwear."), HeadTexture.BRONZE_MED_HELM.asBase64(), Color.fromRGB(74, 56, 7)),
                        toVersionedJson(
                                new EquipableTypeMeta(null, List.of(
                                        new Modifier(ModifierType.DEFENCE_STAB, 1),
                                        new Modifier(ModifierType.DEFENCE_SLASH, 2),
                                        new Modifier(ModifierType.DEFENCE_CRUSH, 2),
                                        new Modifier(ModifierType.DEFENCE_RANGED, 4)
                                ), null), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.BRONZE_BOOTS.asInt(), Material.LEATHER_BOOTS, ItemRarity.COMMON, "Bronze Boots", List.of("Bronze boots, offering basic foot protection."), null, Color.fromRGB(210, 170, 120)),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 1),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 2),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 1)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.IRON_BOOTS.asInt(), Material.CHAINMAIL_BOOTS, ItemRarity.COMMON, "Iron Boots", List.of("Iron boots, sturdy and reliable."), null, null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 2),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 3),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 2)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.STEEL_BOOTS.asInt(), Material.IRON_BOOTS, ItemRarity.COMMON, "Steel Boots", List.of("Steel boots, made for tough terrain."), null, null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 3),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 4),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 3)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 5))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.MITHRIL_BOOTS.asInt(), Material.GOLDEN_BOOTS, ItemRarity.UNCOMMON, "Mithril Boots", List.of("Mithril boots, light and swift."), null, null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 5),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 6),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 5)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 20))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.ADAMANTITE_BOOTS.asInt(), Material.DIAMOND_BOOTS, ItemRarity.UNCOMMON, "Adamantite Boots", List.of("Adamantite boots, heavy and protective."), null, null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 7),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 8),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 7)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 30))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.RUNITE_BOOTS.asInt(), Material.NETHERITE_BOOTS, ItemRarity.RARE, "Runite Boots", List.of("Runite boots imbued with arcane power."), null, null),
                        toVersionedJson(
                                new EquipableTypeMeta(null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 10),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 11),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 9)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 40))
                                )
                                , 1)
                ),


                // Weapons - Swords
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.BRONZE_SWORD.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Bronze Sword", List.of("Bronze sword, a beginner’s trusty blade."), null, null),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.NORMAL, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 4),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 3)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 1))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.IRON_SWORD.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Iron Sword", List.of("Iron sword, reliable for close combat."), null, null),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.NORMAL, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 6),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 5)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 1))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.STEEL_SWORD.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Steel Sword", List.of("Steel sword, sharp and durable."), null, null),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.NORMAL, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 10),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 9)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 5))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.MITHRIL_SWORD.asInt(), Material.WOODEN_SWORD, ItemRarity.UNCOMMON, "Mithril Sword", List.of("Mithril sword, light and deadly."), null, null),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.NORMAL, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 16),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 14)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 20))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON_WITH_GEM_SOCKETS, ItemId.ADAMANTITE_SWORD.asInt(), Material.WOODEN_SWORD, ItemRarity.UNCOMMON, "Adamantite Sword", List.of("Adamantite sword, powerful and heavy."), null, null),
                        toVersionedJson(
                                new SocketedWeaponEquipableMeta(new EquipableWeaponTypeMeta(WeaponSpeed.NORMAL, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 21),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 19)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 30))
                                ), null, 2)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON_WITH_GEM_SOCKETS, ItemId.RUNITE_SWORD.asInt(), Material.WOODEN_SWORD, ItemRarity.RARE, "Runite Sword", List.of("Runite sword crackling with magical energy."), null, null),
                        toVersionedJson(
                                new SocketedWeaponEquipableMeta(new EquipableWeaponTypeMeta(WeaponSpeed.NORMAL, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 30),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 29)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 40))
                                ), null, 3)
                                , 1)
                ),

                // Weapons - Daggers
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.BRONZE_DAGGER.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Bronze Dagger", List.of("Bronze dagger, quick and light."), null, null),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.FAST, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_STAB, 3),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 2)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 1))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.IRON_DAGGER.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Iron Dagger", List.of("Iron dagger, sharp and practical."), null, null),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.FAST, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_STAB, 5),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 3)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 1))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.STEEL_DAGGER.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Steel Dagger", List.of("Steel dagger, deadly in skilled hands."), null, null),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.FAST, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_STAB, 8),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 6)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 5))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.MITHRIL_DAGGER.asInt(), Material.WOODEN_SWORD, ItemRarity.UNCOMMON, "Mithril Dagger", List.of("Mithril dagger, swift and precise."), null, null),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.FAST, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_STAB, 14),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 10)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 20))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.ADAMANTITE_DAGGER.asInt(), Material.WOODEN_SWORD, ItemRarity.UNCOMMON, "Adamantite Dagger", List.of("Adamantite dagger, strong and lethal."), null, null),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.FAST, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_STAB, 18),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 14)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 30))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.RUNITE_DAGGER.asInt(), Material.WOODEN_SWORD, ItemRarity.RARE, "Runite Dagger", List.of("Runite dagger glowing with mystical power."), null, null),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.FAST, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_STAB, 24),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 19)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 40))
                                )
                                , 1)
                ),

                // Weapons - Battleaxes
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.BRONZE_BATTLEAXE.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Bronze Battleaxe", List.of("Bronze battleaxe, heavy but slow."), null, null),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 5),
                                                new Modifier(ModifierType.ATTACK_CRUSH, 6),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 6)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 1))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.IRON_BATTLEAXE.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Iron Battleaxe", List.of("Iron battleaxe, powerful cleaving weapon."), null, null),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 8),
                                                new Modifier(ModifierType.ATTACK_CRUSH, 9),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 9)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 1))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.STEEL_BATTLEAXE.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Steel Battleaxe", List.of("Steel battleaxe, favored by warriors."), null, null),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 12),
                                                new Modifier(ModifierType.ATTACK_CRUSH, 13),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 13)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 5))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.MITHRIL_BATTLEAXE.asInt(), Material.WOODEN_SWORD, ItemRarity.UNCOMMON, "Mithril Battleaxe", List.of("Mithril battleaxe, fast and deadly."), null, null),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 18),
                                                new Modifier(ModifierType.ATTACK_CRUSH, 19),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 20)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 20))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.ADAMANTITE_BATTLEAXE.asInt(), Material.WOODEN_SWORD, ItemRarity.UNCOMMON, "Adamantite Battleaxe", List.of("Adamantite battleaxe, crushing blows guaranteed."), null, null),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 23),
                                                new Modifier(ModifierType.ATTACK_CRUSH, 24),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 25)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 30))
                                )
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.RUNITE_BATTLEAXE.asInt(), Material.WOODEN_SWORD, ItemRarity.RARE, "Runite Battleaxe", List.of("Runite battleaxe, imbued with arcane fury."), null, null),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 32),
                                                new Modifier(ModifierType.ATTACK_CRUSH, 33),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 34)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 40))
                                ),
                                1)
                ),


                // SHORTBOWS

                new ItemEntity(new AbstractItemMeta(ItemType.RANGED_WEAPON, ItemId.SHORT_BOW.asInt(), Material.BOW, ItemRarity.COMMON, "Oak Shortbow", List.of("A basic shortbow made from oak."), null, null),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.RANGED_STRENGTH, 5),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 6)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 1))
                                ),
                                1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.RANGED_WEAPON, ItemId.BIRCH_SHORT_BOW.asInt(), Material.BOW, ItemRarity.COMMON, "Birch Shortbow", List.of("Light and flexible, but not very strong."), null, null),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.RANGED_STRENGTH, 7),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 9)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 5))
                                ),
                                1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.RANGED_WEAPON, ItemId.MAPLE_SHORT_BOW.asInt(), Material.BOW, ItemRarity.UNCOMMON, "Maple Shortbow", List.of("A well-balanced shortbow made from maple."), null, null),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.RANGED_STRENGTH, 12),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 15)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 20))
                                ),
                                1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.RANGED_WEAPON, ItemId.YEW_SHORT_BOW.asInt(), Material.BOW, ItemRarity.RARE, "Yew Shortbow", List.of("A shortbow made of yew, powerful and efficient."), null, null),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.RANGED_STRENGTH, 17),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 20)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 30))
                                ),
                                1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.RANGED_WEAPON, ItemId.MAGIC_SHORT_BOW.asInt(), Material.BOW, ItemRarity.RARE, "Magic Shortbow", List.of("A magically enhanced shortbow with high accuracy."), null, null),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.RANGED_STRENGTH, 22),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 28)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 50))
                                ),
                                1)
                ),

                // LONGBOWS

                new ItemEntity(new AbstractItemMeta(ItemType.RANGED_WEAPON, ItemId.LONG_BOW.asInt(), Material.BOW, ItemRarity.COMMON, "Oak Longbow", List.of("A standard longbow with increased range."), null, null),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.VERY_SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.RANGED_STRENGTH, 6),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 8)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 1))
                                ),
                                1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.RANGED_WEAPON, ItemId.BIRCH_LONG_BOW.asInt(), Material.BOW, ItemRarity.COMMON, "Birch Longbow", List.of("A birch longbow, decent for beginners."), null, null),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.VERY_SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.RANGED_STRENGTH, 9),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 12)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 5))
                                ),
                                1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.RANGED_WEAPON, ItemId.MAPLE_LONG_BOW.asInt(), Material.BOW, ItemRarity.UNCOMMON, "Maple Longbow", List.of("A longbow crafted from sturdy maple."), null, null),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.VERY_SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.RANGED_STRENGTH, 14),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 18)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 20))
                                ),
                                1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.RANGED_WEAPON, ItemId.YEW_LONG_BOW.asInt(), Material.BOW, ItemRarity.RARE, "Yew Longbow", List.of("A longbow made of yew, strong and accurate."), null, null),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.VERY_SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.RANGED_STRENGTH, 19),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 24)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 30))
                                ),
                                1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.RANGED_WEAPON, ItemId.MAGIC_LONG_BOW.asInt(), Material.BOW, ItemRarity.EPIC, "Magic Longbow", List.of("An enchanted longbow with great range and power."), null, null),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.VERY_SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.RANGED_STRENGTH, 24),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 32)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 50))
                                ),
                                1)
                ),

                // Crossbows

                new ItemEntity(new AbstractItemMeta(ItemType.RANGED_WEAPON, ItemId.IRON_CROSSBOW.asInt(), Material.CROSSBOW, ItemRarity.UNCOMMON, "Iron Crossbow", List.of("A sturdy iron crossbow."), null, null),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.VERY_SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.RANGED_STRENGTH, 15),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 18)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 20))
                                ),
                                1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.RANGED_WEAPON, ItemId.STEEL_CROSSBOW.asInt(), Material.CROSSBOW, ItemRarity.UNCOMMON, "Steel Crossbow", List.of("A reliable steel crossbow."), null, null),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.VERY_SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.RANGED_STRENGTH, 20),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 22)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 30))
                                ),
                                1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.RANGED_WEAPON, ItemId.MITHRIL_CROSSBOW.asInt(), Material.CROSSBOW, ItemRarity.RARE, "Mithril Crossbow", List.of("A lightweight mithril crossbow with enhanced power."), null, null),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.VERY_SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.RANGED_STRENGTH, 28),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 30)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 40))
                                ),
                                1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.RANGED_WEAPON, ItemId.ADAMANT_CROSSBOW.asInt(), Material.CROSSBOW, ItemRarity.RARE, "Adamant Crossbow", List.of("A powerful adamant crossbow with great accuracy."), null, null),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.VERY_SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.RANGED_STRENGTH, 35),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 38)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 50))
                                ),
                                1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.RANGED_WEAPON, ItemId.RUNITE_CROSSBOW.asInt(), Material.CROSSBOW, ItemRarity.LEGENDARY, "Runite Crossbow", List.of("The ultimate runite crossbow with unmatched precision."), null, null),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.VERY_SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.RANGED_STRENGTH, 42),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 45)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 60))
                                ),
                                1)
                )


        );
    }

    public void setCache(List<AbstractItemMeta> items) {
        this.cached = items;
    }
}

