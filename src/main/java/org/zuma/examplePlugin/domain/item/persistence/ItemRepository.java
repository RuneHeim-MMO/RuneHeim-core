package org.zuma.examplePlugin.domain.item.persistence;

import com.google.gson.Gson;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.inventory.EquipmentSlot;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.block.constants.BlockType;
import org.zuma.examplePlugin.domain.block.meta.v1.InventoryOpenerMeta;
import org.zuma.examplePlugin.domain.block.meta.v1.LightableBlockMeta;
import org.zuma.examplePlugin.domain.droptable.DropTableId;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.housing.records.HouseSlot;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.StatsEffect;
import org.zuma.examplePlugin.domain.item.constants.*;
import org.zuma.examplePlugin.domain.item.impl.EmptyContainer;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.meta.v1.*;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.SkillModifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.quest.constants.QuestId;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.ArrayList;
import java.util.List;

public class ItemRepository {
    private static final double CRAFTED_ITEM_MULTIPLIER = 0.95;
    private final Gson gson = new Gson();
    private List<AbstractItemMeta> cached = new ArrayList<>();

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
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.ADMIN_OP_STICK.asInt(), Material.STICK, ItemRarity.COMMON, "Admin Stick", List.of("Bronze dagger, quick and light."), null, true, 1.0),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.VERY_FAST, List.of(new SkillModifier(SkillType.HITPOINTS, 50, null)),
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_STAB, 999999),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 999999),
                                                new Modifier(ModifierType.ATTACK_CRUSH, 999999),
                                                new Modifier(ModifierType.ATTACK_SLASH, 999999)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 1)),
                                        null)
                                , 1)
                ),


                new ItemEntity(new AbstractItemMeta(ItemType.BULK_ITEM, ItemId.BULK_10_GENIE_XP_LAMP.asInt(), Material.IRON_INGOT, ItemRarity.LEGENDARY, "10x Genie Lamp", List.of("Box, containing a bulk of items."), null, false, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new BulkItemMeta(List.of(new ItemRecord(ItemId.GENIE_XP_LAMP, 10)), CustomModelId.BULK_GENIE_LAMP), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.BULK_ITEM, ItemId.BULK_10_PLATINUM_TOKEN.asInt(), Material.IRON_INGOT, ItemRarity.LEGENDARY, "10x Platinum Tokens", List.of("Box, containing a bulk of items."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new BulkItemMeta(List.of(new ItemRecord(ItemId.PLATINUM_TOKEN, 10)), CustomModelId.BULK_PLATINUM_TOKEN), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.BULK_ITEM, ItemId.BULK_3_OVERLOAD_POTION.asInt(), Material.IRON_INGOT, ItemRarity.LEGENDARY, "3x Overload Potions", List.of("Box, containing a bulk of items."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new BulkItemMeta(List.of(new ItemRecord(ItemId.OVERLOAD_POTION, 3)), CustomModelId.BULK_OVERLOAD), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.BULK_ITEM, ItemId.BULK_3_ETHEREAL_RESTORATION.asInt(), Material.IRON_INGOT, ItemRarity.LEGENDARY, "3x Ethereal Restoration Potions", List.of("Box, containing a bulk of items."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new BulkItemMeta(List.of(new ItemRecord(ItemId.ETHEREAL_RESTORATION, 3)), CustomModelId.BULK_ETHEREAL_RESTORATION), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.BULK_ITEM, ItemId.BULK_3_DRAGONS_MIGHT_POTION.asInt(), Material.IRON_INGOT, ItemRarity.LEGENDARY, "3x Dragons Might Potions", List.of("Box, containing a bulk of items."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new BulkItemMeta(List.of(new ItemRecord(ItemId.DRAGONS_MIGHT_POTION, 3)), CustomModelId.BULK_DRAGONS_MIGHT), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.BULK_ITEM, ItemId.BULK_3_ULTIMATE_POWER_POTION.asInt(), Material.IRON_INGOT, ItemRarity.MYTHIC, "3x Ultimate Power Potions", List.of("Box, containing a bulk of items."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new BulkItemMeta(List.of(new ItemRecord(ItemId.ULTIMATE_POWER_POTION, 3)), CustomModelId.BULK_ULTIMATE_POWER), 1)),

                // Herblore
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.DEWLEAF_ESSENCE.asInt(), Material.COAL, ItemRarity.COMMON, "Dewleaf Essence", List.of("A fresh essence used in restorative and healing potions."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.SUNROOT_ESSENCE.asInt(), Material.COAL, ItemRarity.COMMON, "Sunroot Essence", List.of("A warm essence that empowers attack and gathering brews."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.MOONSHADE_ESSENCE.asInt(), Material.COAL, ItemRarity.COMMON, "Moonshade Essence", List.of("A shadowed essence tied to strength and skill enhancement."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.VITALITY_ESSENCE.asInt(), Material.COAL, ItemRarity.COMMON, "Vitality Essence", List.of("A stabilizing essence commonly used in defensive potions."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.BALANCE_ESSENCE.asInt(), Material.COAL, ItemRarity.COMMON, "Balance Essence", List.of("A precise essence favored in ranged and control brews."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.CRIMSON_ESSENCE.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Crimson Essence", List.of("A potent essence that amplifies raw combat power."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.FROST_ESSENCE.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Frost Essence", List.of("A chilling essence used in endurance and recovery brews."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.GLOW_ESSENCE.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Glow Essence", List.of("A radiant essence linked to focus and spiritual restoration."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.GUARDIAN_ESSENCE.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Guardian Essence", List.of("A steadfast essence prized for powerful defensive potions."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.PRECISION_ESSENCE.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Precision Essence", List.of("A refined essence that enhances accuracy and efficiency."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.STORM_ESSENCE.asInt(), Material.COAL, ItemRarity.RARE, "Storm Essence", List.of("A volatile essence infused with raw elemental force."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.BLOOD_ESSENCE.asInt(), Material.COAL, ItemRarity.RARE, "Blood Essence", List.of("A dangerous essence used in powerful life-altering brews."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.DRAGON_ESSENCE.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Dragon Essence", List.of("An ancient essence radiating overwhelming combat power."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.ETHEREAL_ESSENCE.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Ethereal Essence", List.of("A rare essence that bends vitality beyond mortal limits."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.DEWLEAF.asInt(), Material.COAL, ItemRarity.COMMON, "Dewleaf", List.of("A small bluish-green leaf, still glistening with morning dew."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(50), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.SUNROOT.asInt(), Material.COAL, ItemRarity.COMMON, "Sunroot", List.of("A thick golden root that stores the warmth of the sun."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(140), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.MOONSHADE.asInt(), Material.COAL, ItemRarity.COMMON, "Moonshade", List.of("Pale silver leaves that seem to shimmer under moonlight."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(40), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.CRIMSON_THISTLE.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Crimson Thistle", List.of("A red, spiky flower with jagged green leaves and tiny thorns."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(200), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.FROSTCAP.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Frostcap", List.of("A blue-capped mushroom speckled with frost."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(150), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.GLOWFERN.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Glowfern", List.of("Delicate fern fronds that emit a faint blue glow in the dark."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(150), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.STORMVINE.asInt(), Material.COAL, ItemRarity.RARE, "Stormvine", List.of("Dark twisting vines that tingle with the energy of storms."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(400), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.BLOODORCHID.asInt(), Material.COAL, ItemRarity.RARE, "Blood Orchid", List.of("A deep red orchid with thick jagged leaves, rumored to hold regenerative powers."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(400), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.DRAGONS_BREATH.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Dragons Breath", List.of("Spiky orange-red petals that seem to radiate heat and energy."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(1400), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.ETHEREAL_BLOOM.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Ethereal Bloom", List.of("A shimmering multicolor flower that glows faintly and seems almost unreal."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(1500), 1)),


                // Furniture
                new ItemEntity(new AbstractItemMeta(ItemType.INTERACTIVE_FURNITURE, ItemId.BREWING_CAULDRON_FURNITURE.asInt(), Material.CAULDRON, ItemRarity.RARE, "Brewing Cauldron", List.of("Used for essence extraction and potion breweing."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new InteractiveFurnitureMeta(HouseSlot.HouseSlotType.INTERACTIVE, BlockType.InventoryOpener, toVersionedJson(new InventoryOpenerMeta(InventoryId.BREWING_CAULDRON.asInt(), Material.DIAMOND_BLOCK, null, null, null, "Brewing Cauldron", Icon.HERBLORE.get()), 1)), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.INTERACTIVE_FURNITURE, ItemId.COMBAT_DUMMY_FURNITURE.asInt(), Material.ARMOR_STAND, ItemRarity.RARE, "Combat Dummy", List.of("Used to track your max hit."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new InteractiveFurnitureMeta(HouseSlot.HouseSlotType.INTERACTIVE, BlockType.CombatDummyFurniture, null), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.INTERACTIVE_FURNITURE, ItemId.ANVIL_FURNITURE.asInt(), Material.ANVIL, ItemRarity.RARE, "Anvil", List.of("Used to craft and repair equipment."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new InteractiveFurnitureMeta(HouseSlot.HouseSlotType.INTERACTIVE, BlockType.InventoryOpener, toVersionedJson(new InventoryOpenerMeta(InventoryId.ANVIL.asInt(), Material.ANVIL, null, null, null, "Anvil", Icon.ANVIL.get()), 1)), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.INTERACTIVE_FURNITURE, ItemId.STOVE_FURNITURE.asInt(), Material.FURNACE, ItemRarity.RARE, "Stove", List.of("Used to cook food and meals."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new InteractiveFurnitureMeta(HouseSlot.HouseSlotType.INTERACTIVE, BlockType.InventoryOpener, toVersionedJson(new InventoryOpenerMeta(InventoryId.STOVE.asInt(), Material.FURNACE, null, null, null, "Stove", Icon.FIRE.get()), 1)), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.INTERACTIVE_FURNITURE, ItemId.CRAFTING_TABLE_FURNITURE.asInt(), Material.CRAFTING_TABLE, ItemRarity.RARE, "Crafting Table", List.of("Used to craft items and components."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new InteractiveFurnitureMeta(HouseSlot.HouseSlotType.INTERACTIVE, BlockType.InventoryOpener, toVersionedJson(new InventoryOpenerMeta(InventoryId.CRAFTING_TABLE.asInt(), Material.CRAFTING_TABLE, null, null, null, "Crafting Table", Icon.CRAFTING.get()), 1)), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.INTERACTIVE_FURNITURE, ItemId.FLETCHING_TABLE_FURNITURE.asInt(), Material.FLETCHING_TABLE, ItemRarity.RARE, "Fletching Table", List.of("Used to craft bows, arrows, and ranged gear."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new InteractiveFurnitureMeta(HouseSlot.HouseSlotType.INTERACTIVE, BlockType.InventoryOpener, toVersionedJson(new InventoryOpenerMeta(InventoryId.FLETCHING_TABLE.asInt(), Material.FLETCHING_TABLE, null, null, null, "Fletching Table", Icon.ARROW.get()), 1)), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.INTERACTIVE_FURNITURE, ItemId.BLAST_FURNACE_FURNITURE.asInt(), Material.BLAST_FURNACE, ItemRarity.LEGENDARY, "Blast Furnace", List.of("Portable furnace", "Used to smelt ores and refine metals."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new InteractiveFurnitureMeta(HouseSlot.HouseSlotType.INTERACTIVE, BlockType.InventoryOpener, toVersionedJson(new InventoryOpenerMeta(InventoryId.BASIC_FURNACE.asInt(), Material.BLAST_FURNACE, null, null, null, "Smelting Furnace", Icon.FURNACE.get()), 1)), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.INTERACTIVE_FURNITURE, ItemId.ALTAR_FURNITURE.asInt(), Material.LECTERN, ItemRarity.LEGENDARY, "Altar", List.of("Used to pray and receive blessings."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new InteractiveFurnitureMeta(HouseSlot.HouseSlotType.INTERACTIVE, BlockType.Altar, null), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.INTERACTIVE_FURNITURE, ItemId.LANTERN_FURNITURE.asInt(), Material.LANTERN, ItemRarity.UNCOMMON, "Lantern", List.of("Provides steady light."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new InteractiveFurnitureMeta(HouseSlot.HouseSlotType.DECORATION, BlockType.LightableBlock, toVersionedJson(new LightableBlockMeta(Material.LANTERN), 1)), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.INTERACTIVE_FURNITURE, ItemId.SOUL_LANTERN_FURNITURE.asInt(), Material.SOUL_LANTERN, ItemRarity.RARE, "Blue Lantern", List.of("Emits an eerie blue glow."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new InteractiveFurnitureMeta(HouseSlot.HouseSlotType.DECORATION, BlockType.LightableBlock, toVersionedJson(new LightableBlockMeta(Material.SOUL_LANTERN), 1)), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.INTERACTIVE_FURNITURE, ItemId.REDSTONE_LAMP_FURNITURE.asInt(), Material.REDSTONE_LAMP, ItemRarity.UNCOMMON, "Lamp", List.of("Provides steady light."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new InteractiveFurnitureMeta(HouseSlot.HouseSlotType.DECORATION, BlockType.LightableBlock, toVersionedJson(new LightableBlockMeta(Material.REDSTONE_LAMP), 1)), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.INTERACTIVE_FURNITURE, ItemId.WHITE_CANDLE_FURNITURE.asInt(), Material.CANDLE, ItemRarity.COMMON, "Candle", List.of("A small candle for decoration."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new InteractiveFurnitureMeta(HouseSlot.HouseSlotType.DECORATION, BlockType.LightableBlock, toVersionedJson(new LightableBlockMeta(Material.CANDLE), 1)), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.INTERACTIVE_FURNITURE, ItemId.WHITE_CANDLE_CAKE_FURNITURE.asInt(), Material.CAKE, ItemRarity.COMMON, "Candle Cake", List.of("A cake topped with a decorative candle."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new InteractiveFurnitureMeta(HouseSlot.HouseSlotType.DECORATION, BlockType.LightableBlock, toVersionedJson(new LightableBlockMeta(Material.CANDLE_CAKE), 1)), 1)
                ),


                // New materials
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.CHICKEN_EGG.asInt(), Material.COAL, ItemRarity.COMMON, "Chicken Egg", List.of("A fresh egg laid by a chicken."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(45), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.HOGLIN_TUSK.asInt(), Material.COAL, ItemRarity.COMMON, "Hoglin Tusk", List.of("A sharp tusk from a wild hoglin."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(90), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.BAMBOO.asInt(), Material.COAL, ItemRarity.COMMON, "Bamboo", List.of("A sturdy bamboo stalk from the jungle."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(135), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.DEER_ANTLER.asInt(), Material.COAL, ItemRarity.COMMON, "Deer Antler", List.of("A branching antler shed by a deer."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(180), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.EAGLE_CLAW.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Eagle Claw", List.of("A sharp talon from a mighty eagle."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(2250), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.SLIMY_SAC.asInt(), Material.COAL, ItemRarity.COMMON, "Slimy Sac", List.of("A mucus-filled sac from a swamp creature."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(225), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.PREDATOR_CLAW.asInt(), Material.COAL, ItemRarity.COMMON, "Predator Claw", List.of("A sharp claw from a jungle hunter."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(225), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.SOFT_CUB_FUR.asInt(), Material.COAL, ItemRarity.COMMON, "Soft Cub Fur", List.of("The fluffy fur of a young leopard."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(270), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.UNDERGROUND_FUNGUS.asInt(), Material.COAL, ItemRarity.COMMON, "Underground Fungus", List.of("A pale mushroom grown in dark tunnels."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(1125), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.MOLDY_CHEESE.asInt(), Material.COOKED_SALMON, ItemRarity.COMMON, "Moldy Cheese",
                        List.of("A piece of cheese nibbled by rodents."), null, true, 1), toVersionedJson(new FoodTypeMeta(-2, 30), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.SOFT_FUR.asInt(), Material.COAL, ItemRarity.COMMON, "Soft Fur", List.of("Fine fur from a gentle creature."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(180), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.INSECT_WINGS.asInt(), Material.COAL, ItemRarity.COMMON, "Insect Wings", List.of("Delicate wings from various bugs."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(45), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.COCKROACH_LEG.asInt(), Material.COAL, ItemRarity.COMMON, "Cockroach Leg", List.of("A spiny leg from a resilient insect."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(67), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.SURVIVAL_GLAND.asInt(), Material.COAL, ItemRarity.RARE, "Survival Gland", List.of("A special organ that grants resilience."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(1350), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.ANT_EGGS.asInt(), Material.COAL, ItemRarity.COMMON, "Ant Eggs", List.of("Tiny eggs from an ant colony."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(200), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.ANT_MANDIBLES.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Ant Mandibles", List.of("Sharp pincers from a worker ant."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(450), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.VENOM_SAC.asInt(), Material.COAL, ItemRarity.RARE, "Venom Sac", List.of("A sac filled with potent toxin."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(1800), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.SCORPION_STINGER.asInt(), Material.COAL, ItemRarity.COMMON, "Scorpion Stinger", List.of("A barbed stinger from a desert scorpion."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(675), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.CHITIN_PLATES.asInt(), Material.COAL, ItemRarity.COMMON, "Chitin Plates", List.of("Hard protective plates from insects."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(225), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.BOAR_TUSK.asInt(), Material.COAL, ItemRarity.COMMON, "Boar Tusk", List.of("A curved tusk from a wild boar."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(180), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.THICK_HIDE.asInt(), Material.COAL, ItemRarity.COMMON, "Thick Hide", List.of("Durable hide from a tough animal."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(270), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.ARMADILLO_CLAW.asInt(), Material.COAL, ItemRarity.COMMON, "Armadillo Claw", List.of("A digging claw from an armadillo."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(360), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.SHARP_ARMADILLO_CLAW.asInt(), Material.COAL, ItemRarity.RARE, "Sharp Armadillo Claw", List.of("An exceptionally sharp digging claw."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(2250), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.ARMADILLO_SCALE.asInt(), Material.COAL, ItemRarity.COMMON, "Armadillo Scale", List.of("A protective scale from an armadillo's shell."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(450), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.SPIDER_SILK.asInt(), Material.COAL, ItemRarity.COMMON, "Spider Silk", List.of("Fine, silvery thread harvested from giant spiders."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(200), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.LINEN_FABRIC.asInt(), Material.COAL, ItemRarity.COMMON, "Linen Fabric", List.of("A sheet of woven flax fibers.", "Durable, breathable, and used in mid-tier tailoring."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(0), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.STEEL_BUCKLE.asInt(), Material.COAL, ItemRarity.COMMON, "Steel Buckle", List.of("A sturdy clasp forged from refined steel.", "Used to fasten straps on armor, bags, and other gear."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(0), 1)),


                // Storage items
                new ItemEntity(new AbstractItemMeta(ItemType.STORAGE_ITEM, ItemId.TOOL_BAG.asInt(), Material.COAL, ItemRarity.RARE, "Tool Bag (9 slots)", List.of("Bag for all of your tools."), null, true, 1.0),
                        toVersionedJson(new StorageItemMeta(InventoryId.TOOL_BAG), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.STORAGE_ITEM, ItemId.SMALL_ITEM_BAG.asInt(), Material.COAL, ItemRarity.RARE, "Small Item Bag (6 slots)", List.of("A compact bag, perfect for carrying essentials on short quests."), null, true, 1.0),
                        toVersionedJson(new StorageItemMeta(InventoryId.SMALL_STORAGE_BAG), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.STORAGE_ITEM, ItemId.MEDIUM_ITEM_BAG.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Medium Item Bag (9 slots)", List.of("A sturdy bag with ample space for adventurer's tools and trinkets."), null, true, 1.0),
                        toVersionedJson(new StorageItemMeta(InventoryId.MEDIUM_STORAGE_BAG), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.STORAGE_ITEM, ItemId.LARGE_ITEM_BAG.asInt(), Material.COAL, ItemRarity.MYTHIC, "Large Item Bag (12 slots)", List.of("A massive bag capable of holding the spoils of even the longest journeys."), null, true, 1.0),
                        toVersionedJson(new StorageItemMeta(InventoryId.LARGE_STORAGE_BAG), 1)),


                // Dungeon
                new ItemEntity(new AbstractItemMeta(ItemType.MISC, ItemId.LAVA_DUNGEON_KEY_A.asInt(), Material.COAL, ItemRarity.NOT_APPLICABLE, "Dungeon Key A", List.of("A weird looking key."), null, true, 1.0), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MISC, ItemId.LAVA_DUNGEON_KEY_B.asInt(), Material.COAL, ItemRarity.NOT_APPLICABLE, "Dungeon Key B", List.of("A weird looking key."), null, true, 1.0), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MISC, ItemId.LAVA_DUNGEON_KEY_C.asInt(), Material.COAL, ItemRarity.NOT_APPLICABLE, "Dungeon Key C", List.of("A weird looking key."), null, true, 1.0), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MISC, ItemId.LAVA_DUNGEON_KEY_D.asInt(), Material.COAL, ItemRarity.NOT_APPLICABLE, "Dungeon Key D", List.of("A weird looking key."), null, true, 1.0), null),


                // Quest items
                // The Sick Camels
                new ItemEntity(new AbstractItemMeta(
                        ItemType.QUEST,
                        ItemId.FROG_EYE_THE_SICK_CAMELS_QUEST.asInt(),
                        Material.IRON_NUGGET,
                        ItemRarity.COMMON,
                        "Frog Eye",
                        List.of("An eye taken from a swamp frog.", "Might be useful for a potion."),
                        null, false, 1.0),
                        toVersionedJson(new QuestItemMeta(QuestId.THE_SICK_CAMELS.getId(), true), 1)),

                new ItemEntity(new AbstractItemMeta(
                        ItemType.QUEST,
                        ItemId.WOLF_FANG_THE_SICK_CAMELS_QUEST.asInt(),
                        Material.IRON_NUGGET,
                        ItemRarity.COMMON,
                        "Wolf Fang",
                        List.of("A sharp fang from a wolf.", "Could be used as an ingredient."),
                        null, false, 1.0),
                        toVersionedJson(new QuestItemMeta(QuestId.THE_SICK_CAMELS.getId(), true), 1)),

                new ItemEntity(new AbstractItemMeta(
                        ItemType.QUEST,
                        ItemId.CAMEL_CURE_THE_SICK_CAMELS_QUEST.asInt(),
                        Material.IRON_NUGGET,
                        ItemRarity.COMMON,
                        "Camel Cure",
                        List.of("A potent mixture to cure sick camels.", "Deliver it to the Rafiq."),
                        null, false, 1.0),
                        toVersionedJson(new QuestItemMeta(QuestId.THE_SICK_CAMELS.getId(), false), 1)),

                // Promise in a Box
                new ItemEntity(new AbstractItemMeta(
                        ItemType.QUEST,
                        ItemId.PROMISE_IN_A_BOX_ENGAGEMENT_RING.asInt(),
                        Material.IRON_NUGGET,
                        ItemRarity.COMMON,
                        "Engagement ring",
                        List.of("A beautiful ring symbolizing a promise.", "Deliver it to its rightful owner."),
                        null, false, 1.0),
                        toVersionedJson(new QuestItemMeta(QuestId.PROMISE_IN_A_BOX.getId(), false), 1)),


                // Mounts

                // Low-end horses
                new ItemEntity(new AbstractItemMeta(
                        ItemType.MOUNT,
                        ItemId.BROWN_FARM_HORSE_MOUNT.asInt(),
                        Material.SADDLE,
                        ItemRarity.COMMON,
                        "Brown Farm Horse",
                        List.of("A humble brown horse, perfect for beginners."),
                        null, true, 1.0),
                        toVersionedJson(new MountMeta(null, EntityType.HORSE, Horse.Style.NONE, Horse.Color.BROWN, 5, 0.15, 0.4, 0.02, 0.05, null, null), 1)),

                new ItemEntity(new AbstractItemMeta(
                        ItemType.MOUNT,
                        ItemId.GRAY_MULE_MOUNT.asInt(),
                        Material.SADDLE,
                        ItemRarity.UNCOMMON,
                        "Brown Mule",
                        List.of("A sturdy gray mule, reliable and patient."),
                        null, true, 1.0),
                        toVersionedJson(new MountMeta(null, EntityType.MULE, null, null, 6, 0.15, 0.45, 0.025, 0.06, null, null), 1)),

                new ItemEntity(new AbstractItemMeta(
                        ItemType.MOUNT,
                        ItemId.CHESTNUT_DONKEY_MOUNT.asInt(),
                        Material.SADDLE,
                        ItemRarity.UNCOMMON,
                        "Chestnut Donkey",
                        List.of("A chestnut donkey, gentle and dependable."),
                        null, true, 1.0),
                        toVersionedJson(new MountMeta(null, EntityType.DONKEY, Horse.Style.NONE, Horse.Color.CHESTNUT, 6, 0.15, 0.45, 0.025, 0.06, null, null), 1)),

                // Mid-tier horses
                new ItemEntity(new AbstractItemMeta(
                        ItemType.MOUNT,
                        ItemId.CREAMY_STEED_MOUNT.asInt(),
                        Material.SADDLE,
                        ItemRarity.RARE,
                        "Creamy Steed",
                        List.of("A light cream horse with soft markings, elegant and swift."),
                        null, true, 1.0),
                        toVersionedJson(new MountMeta(null, EntityType.HORSE, Horse.Style.WHITEFIELD, Horse.Color.CREAMY, 8, 0.15, 0.5, 0.03, 0.07, null, null), 1)),

                new ItemEntity(new AbstractItemMeta(
                        ItemType.MOUNT,
                        ItemId.SNOW_WHITE_HORSE_MOUNT.asInt(),
                        Material.SADDLE,
                        ItemRarity.RARE,
                        "Snow White Horse",
                        List.of("A pure white horse, fast and noble."),
                        null, true, 1.0),
                        toVersionedJson(new MountMeta(null, EntityType.HORSE, Horse.Style.WHITE, Horse.Color.WHITE, 8, 0.15, 0.52, 0.03, 0.075, null, null), 1)),

                new ItemEntity(new AbstractItemMeta(
                        ItemType.MOUNT,
                        ItemId.BLACK_STALLION_MOUNT.asInt(),
                        Material.SADDLE,
                        ItemRarity.EPIC,
                        "Black Stallion",
                        List.of("A sleek black horse, powerful and swift."),
                        null, true, 1.0),
                        toVersionedJson(new MountMeta(null, EntityType.HORSE, Horse.Style.BLACK_DOTS, Horse.Color.BLACK, 9, 0.16, 0.55, 0.035, 0.08, null, null), 1)),

                // High-end horses
                new ItemEntity(new AbstractItemMeta(
                        ItemType.MOUNT,
                        ItemId.DARK_BROWN_WARHORSE_MOUNT.asInt(),
                        Material.SADDLE,
                        ItemRarity.EPIC,
                        "Dark Brown Warhorse",
                        List.of("A dark brown horse, strong and fearsome in battle."),
                        null, true, 1.0),
                        toVersionedJson(new MountMeta(null, EntityType.HORSE, Horse.Style.NONE, Horse.Color.DARK_BROWN, 10, 0.17, 0.57, 0.035, 0.08, null, null), 1)),

                new ItemEntity(new AbstractItemMeta(
                        ItemType.MOUNT,
                        ItemId.UNDEAD_NIGHTMARE_MOUNT.asInt(),
                        Material.SADDLE,
                        ItemRarity.LEGENDARY,
                        "Undead Nightmare",
                        List.of("An undead horse, untiring and fearsome."),
                        null, true, 1.0),
                        toVersionedJson(new MountMeta(null, EntityType.ZOMBIE_HORSE, Horse.Style.NONE, Horse.Color.BLACK, 12, 0.2, 0.6, 0.04, 0.09, null, null), 1)),

                new ItemEntity(new AbstractItemMeta(
                        ItemType.MOUNT,
                        ItemId.SKELETAL_CHARGER_MOUNT.asInt(),
                        Material.SADDLE,
                        ItemRarity.LEGENDARY,
                        "Skeletal Charger",
                        List.of("A skeletal horse, swift and terrifying."),
                        null, true, 1.0),
                        toVersionedJson(new MountMeta(null, EntityType.SKELETON_HORSE, Horse.Style.NONE, Horse.Color.WHITE, 12, 0.2, 0.62, 0.04, 0.09, null, null), 1)),

                // Llama (no style/color)
                new ItemEntity(new AbstractItemMeta(
                        ItemType.MOUNT,
                        ItemId.BROWN_LLAMA_MOUNT.asInt(),
                        Material.SADDLE,
                        ItemRarity.RARE,
                        "Brown Llama",
                        List.of("A sturdy brown llama, reliable for travel."),
                        null, true, 1.0),
                        toVersionedJson(new MountMeta(null, EntityType.LLAMA, null, null, 7, 0.14, 0.48, 0.03, 0.065, null, null), 1)),

                // Camel
                new ItemEntity(new AbstractItemMeta(
                        ItemType.MOUNT,
                        ItemId.CAMEL.asInt(),
                        Material.SADDLE,
                        ItemRarity.LEGENDARY,
                        "Camel",
                        List.of("A hardy desert beast, built for endurance and long journeys.", "Can hold up to 2 passengers."),
                        null, true, 1.0),
                        toVersionedJson(new MountMeta(null, EntityType.CAMEL, null, null, 10, 0.14, 0.2, 0.02, 0.015, null, null), 1)),


                new ItemEntity(new AbstractItemMeta(ItemType.MOUNT, ItemId.OBELIX_MOUNT.asInt(), Material.SADDLE, ItemRarity.LEGENDARY, "Obelix", List.of("Your own personal obelix"), null, true, 1.0),
                        toVersionedJson(new MountMeta("chieftain", null, null, null, 10, 0.15, 0.5, 0.03, 0.075, List.of(new SkillRequirement(SkillType.ATTACK, 5)),
                                null), 1)),


                // Accessories
                // Rings

                new ItemEntity(new AbstractItemMeta(ItemType.ACCESSORY_RING, ItemId.BRONZE_RING.asInt(), Material.COAL, ItemRarity.COMMON, "Bronze Ring", List.of("A simple ring that slightly improves your combat."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new EquipableTypeMeta(null, null, null,
                                List.of(
                                        new Modifier(ModifierType.ATTACK_STAB, 1),
                                        new Modifier(ModifierType.DEFENCE_STAB, 1)
                                ), List.of(new SkillRequirement(SkillType.ATTACK, 1),
                                new SkillRequirement(SkillType.DEFENCE, 1)),
                                null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.ACCESSORY_RING, ItemId.IRON_RING.asInt(), Material.COAL, ItemRarity.COMMON, "Iron Ring", List.of("A sturdy ring that provides minor attack and defence bonuses."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new EquipableTypeMeta(null, null, null,
                                List.of(
                                        new Modifier(ModifierType.ATTACK_SLASH, 2),
                                        new Modifier(ModifierType.DEFENCE_SLASH, 2)
                                ), List.of(new SkillRequirement(SkillType.ATTACK, 5),
                                new SkillRequirement(SkillType.DEFENCE, 5)),
                                null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.ACCESSORY_RING, ItemId.MITHRIL_RING.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Mithril Ring", List.of("A well-crafted ring that slightly boosts attack and defence."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new EquipableTypeMeta(null, null, null,
                                List.of(
                                        new Modifier(ModifierType.ATTACK_CRUSH, 3),
                                        new Modifier(ModifierType.DEFENCE_CRUSH, 3)
                                ), List.of(new SkillRequirement(SkillType.ATTACK, 20),
                                new SkillRequirement(SkillType.DEFENCE, 20)),
                                null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.ACCESSORY_RING, ItemId.ADAMANT_RING.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Adamantite Ring", List.of("A strong ring offering moderate boosts to attack and defence."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new EquipableTypeMeta(null, null, null,
                                List.of(
                                        new Modifier(ModifierType.ATTACK_STAB, 4),
                                        new Modifier(ModifierType.ATTACK_SLASH, 4),
                                        new Modifier(ModifierType.DEFENCE_STAB, 4),
                                        new Modifier(ModifierType.DEFENCE_SLASH, 4)
                                ), List.of(new SkillRequirement(SkillType.ATTACK, 30),
                                new SkillRequirement(SkillType.DEFENCE, 30)),
                                null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.ACCESSORY_RING, ItemId.RUNE_RING.asInt(), Material.COAL, ItemRarity.RARE, "Runite Ring", List.of("A rare ring that greatly improves your combat prowess."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new EquipableTypeMeta(null, null, null,
                                List.of(
                                        new Modifier(ModifierType.ATTACK_STAB, 6),
                                        new Modifier(ModifierType.ATTACK_SLASH, 6),
                                        new Modifier(ModifierType.ATTACK_CRUSH, 6),
                                        new Modifier(ModifierType.DEFENCE_STAB, 6),
                                        new Modifier(ModifierType.DEFENCE_SLASH, 6),
                                        new Modifier(ModifierType.DEFENCE_CRUSH, 6),
                                        new Modifier(ModifierType.STRENGTH_BONUS, 3)
                                ), List.of(new SkillRequirement(SkillType.ATTACK, 40),
                                new SkillRequirement(SkillType.DEFENCE, 40)),
                                null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.ACCESSORY_RING, ItemId.GOLD_RING.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Gold Ring", List.of("A gold ring offering mild strength advantage."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new EquipableTypeMeta(null, null, null,
                                List.of(new Modifier(ModifierType.STRENGTH_BONUS, 3)), List.of(new SkillRequirement(SkillType.STRENGTH, 15)),
                                null), 1)),

                // Defence Amulets
                new ItemEntity(new AbstractItemMeta(ItemType.ACCESSORY_AMULET, ItemId.AMULET_OF_GUARDING.asInt(), Material.COAL, ItemRarity.COMMON, "Amulet of Guarding", List.of("Reduces damage taken from all melee attacks."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new EquipableTypeMeta(null, null, null,
                                List.of(
                                        new Modifier(ModifierType.DEFENCE_STAB, 1),
                                        new Modifier(ModifierType.DEFENCE_SLASH, 1),
                                        new Modifier(ModifierType.DEFENCE_CRUSH, 1)
                                ), List.of(new SkillRequirement(SkillType.DEFENCE, 1)),
                                null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.ACCESSORY_AMULET, ItemId.AMULET_OF_THE_PROTECTOR.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Amulet of the Protector", List.of("Better protection against all attacks."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new EquipableTypeMeta(null, null, null,
                                List.of(
                                        new Modifier(ModifierType.DEFENCE_STAB, 3),
                                        new Modifier(ModifierType.DEFENCE_SLASH, 3),
                                        new Modifier(ModifierType.DEFENCE_CRUSH, 3),
                                        new Modifier(ModifierType.DEFENCE_MAGIC, 1)
                                ), List.of(new SkillRequirement(SkillType.DEFENCE, 10)),
                                null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.ACCESSORY_AMULET, ItemId.AMULET_OF_FORTITUDE.asInt(), Material.COAL, ItemRarity.RARE, "Amulet of Fortitude", List.of("Significantly increases your defensive capabilities."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new EquipableTypeMeta(null, null, null,
                                List.of(
                                        new Modifier(ModifierType.DEFENCE_STAB, 5),
                                        new Modifier(ModifierType.DEFENCE_SLASH, 5),
                                        new Modifier(ModifierType.DEFENCE_CRUSH, 5),
                                        new Modifier(ModifierType.DEFENCE_MAGIC, 3)
                                ), List.of(new SkillRequirement(SkillType.DEFENCE, 20)),
                                null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.ACCESSORY_AMULET, ItemId.AMULET_OF_THE_WARD.asInt(), Material.COAL, ItemRarity.EPIC, "Amulet of the Ward", List.of("Greatly enhances your defensive strength."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new EquipableTypeMeta(null, null, null,
                                List.of(
                                        new Modifier(ModifierType.DEFENCE_STAB, 8),
                                        new Modifier(ModifierType.DEFENCE_SLASH, 8),
                                        new Modifier(ModifierType.DEFENCE_CRUSH, 8),
                                        new Modifier(ModifierType.DEFENCE_MAGIC, 5)
                                ), List.of(new SkillRequirement(SkillType.DEFENCE, 40)),
                                null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.ACCESSORY_AMULET, ItemId.AMULET_OF_INVINCIBILITY.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Amulet of Invincibility", List.of("Provides maximum protection against all forms of attack."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new EquipableTypeMeta(null, null, null,
                                List.of(
                                        new Modifier(ModifierType.DEFENCE_STAB, 12),
                                        new Modifier(ModifierType.DEFENCE_SLASH, 12),
                                        new Modifier(ModifierType.DEFENCE_CRUSH, 12),
                                        new Modifier(ModifierType.DEFENCE_MAGIC, 8)
                                ), List.of(new SkillRequirement(SkillType.DEFENCE, 60)),
                                null), 1)),

                // Offensive Amulets
                new ItemEntity(new AbstractItemMeta(ItemType.ACCESSORY_AMULET, ItemId.AMULET_OF_THE_NOVICE_BLADE.asInt(), Material.COAL, ItemRarity.COMMON, "Amulet of the Novice Blade", List.of("Slightly increases melee attack and strength."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new EquipableTypeMeta(null, null, null,
                                List.of(
                                        new Modifier(ModifierType.ATTACK_STAB, 1),
                                        new Modifier(ModifierType.ATTACK_SLASH, 1),
                                        new Modifier(ModifierType.ATTACK_CRUSH, 1),
                                        new Modifier(ModifierType.STRENGTH_BONUS, 1)
                                ), List.of(new SkillRequirement(SkillType.ATTACK, 1)),
                                null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.ACCESSORY_AMULET, ItemId.AMULET_OF_MIGHT.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Amulet of Might", List.of("Boosts your attack and strength more effectively."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new EquipableTypeMeta(null, null, null,
                                List.of(
                                        new Modifier(ModifierType.ATTACK_STAB, 3),
                                        new Modifier(ModifierType.ATTACK_SLASH, 3),
                                        new Modifier(ModifierType.ATTACK_CRUSH, 3),
                                        new Modifier(ModifierType.STRENGTH_BONUS, 3)
                                ), List.of(new SkillRequirement(SkillType.ATTACK, 10)),
                                null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.ACCESSORY_AMULET, ItemId.AMULET_OF_THE_DUELIST.asInt(), Material.COAL, ItemRarity.RARE, "Amulet of the Duelist", List.of("Greatly enhances your melee combat effectiveness."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new EquipableTypeMeta(null, null, null,
                                List.of(
                                        new Modifier(ModifierType.ATTACK_STAB, 5),
                                        new Modifier(ModifierType.ATTACK_SLASH, 5),
                                        new Modifier(ModifierType.ATTACK_CRUSH, 5),
                                        new Modifier(ModifierType.STRENGTH_BONUS, 5)
                                ), List.of(new SkillRequirement(SkillType.ATTACK, 20)),
                                null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.ACCESSORY_AMULET, ItemId.AMULET_OF_THE_GLADIATOR.asInt(), Material.COAL, ItemRarity.EPIC, "Amulet of the Gladiator", List.of("Significantly boosts melee attack and strength."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new EquipableTypeMeta(null, null, null,
                                List.of(
                                        new Modifier(ModifierType.ATTACK_STAB, 8),
                                        new Modifier(ModifierType.ATTACK_SLASH, 8),
                                        new Modifier(ModifierType.ATTACK_CRUSH, 8),
                                        new Modifier(ModifierType.STRENGTH_BONUS, 8)
                                ), List.of(new SkillRequirement(SkillType.ATTACK, 40)),
                                null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.ACCESSORY_AMULET, ItemId.AMULET_OF_ULTIMATE_POWER.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Amulet of Ultimate Power", List.of("Maximizes your melee attack and strength."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new EquipableTypeMeta(null, null, null,
                                List.of(
                                        new Modifier(ModifierType.ATTACK_STAB, 12),
                                        new Modifier(ModifierType.ATTACK_SLASH, 12),
                                        new Modifier(ModifierType.ATTACK_CRUSH, 12),
                                        new Modifier(ModifierType.STRENGTH_BONUS, 12)
                                ), List.of(new SkillRequirement(SkillType.ATTACK, 60)),
                                null), 1)),

                // Ranged Amulets
                new ItemEntity(new AbstractItemMeta(ItemType.ACCESSORY_AMULET, ItemId.AMULET_OF_THE_ARCHER.asInt(), Material.COAL, ItemRarity.COMMON, "Amulet of the Archer", List.of("Slightly improves ranged accuracy and strength."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new EquipableTypeMeta(null, null, null,
                                List.of(
                                        new Modifier(ModifierType.RANGED_ACCURACY, 1),
                                        new Modifier(ModifierType.RANGED_STRENGTH, 1)
                                ), List.of(new SkillRequirement(SkillType.RANGED, 1)),
                                null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.ACCESSORY_AMULET, ItemId.AMULET_OF_PRECISION.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Amulet of Precision", List.of("Increases your ranged combat capabilities."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new EquipableTypeMeta(null, null, null,
                                List.of(
                                        new Modifier(ModifierType.RANGED_ACCURACY, 3),
                                        new Modifier(ModifierType.RANGED_STRENGTH, 3)
                                ), List.of(new SkillRequirement(SkillType.RANGED, 10)),
                                null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.ACCESSORY_AMULET, ItemId.AMULET_OF_THE_HUNTER.asInt(), Material.COAL, ItemRarity.RARE, "Amulet of the Hunter", List.of("Significantly boosts ranged combat effectiveness."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new EquipableTypeMeta(null, null, null,
                                List.of(
                                        new Modifier(ModifierType.RANGED_ACCURACY, 5),
                                        new Modifier(ModifierType.RANGED_STRENGTH, 5)
                                ), List.of(new SkillRequirement(SkillType.RANGED, 20)),
                                null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.ACCESSORY_AMULET, ItemId.AMULET_OF_THE_SHARPSHOOTER.asInt(), Material.COAL, ItemRarity.EPIC, "Amulet of the Sharpshooter", List.of("Greatly enhances ranged attacks."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new EquipableTypeMeta(null, null, null,
                                List.of(
                                        new Modifier(ModifierType.RANGED_ACCURACY, 8),
                                        new Modifier(ModifierType.RANGED_STRENGTH, 8)
                                ), List.of(new SkillRequirement(SkillType.RANGED, 40)),
                                null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.ACCESSORY_AMULET, ItemId.AMULET_OF_THE_EAGLE_EYE.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Amulet of the Eagle Eye", List.of("Maximizes your ranged attack and strength."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new EquipableTypeMeta(null, null, null,
                                List.of(
                                        new Modifier(ModifierType.RANGED_ACCURACY, 12),
                                        new Modifier(ModifierType.RANGED_STRENGTH, 12),
                                        new Modifier(ModifierType.ARROW_RECOVERY_PERCENT, 10)
                                ), List.of(new SkillRequirement(SkillType.RANGED, 60)),
                                null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.ACCESSORY_AMULET, ItemId.GOLD_AMULET.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Gold Amulet", List.of("Gold amulet, slightly enhances your strength."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new EquipableTypeMeta(null, null, null,
                                List.of(new Modifier(ModifierType.STRENGTH_BONUS, 5)), List.of(new SkillRequirement(SkillType.STRENGTH, 15)), null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.ACCESSORY_AMULET, ItemId.IRON_AMULET.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Iron Amulet", List.of("Iron amulet. Barely improves your strength."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new EquipableTypeMeta(null, null, null,
                                List.of(new Modifier(ModifierType.STRENGTH_BONUS, 2)), List.of(new SkillRequirement(SkillType.STRENGTH, 8)), null), 1)),


                // XP Lamps
                new ItemEntity(new AbstractItemMeta(ItemType.XP_LAMP, ItemId.GENIE_XP_LAMP.asInt(), Material.COAL, ItemRarity.RARE, "Genie XP Lamp",
                        List.of("Grants a magical XP boost.", "Use wisely to level up faster."), null, false, 1.0), toVersionedJson(new XPLampMeta(500, List.of()), 1)),


                // Lotteries
                new ItemEntity(new AbstractItemMeta(ItemType.CHANCE_ITEM, ItemId.SCRATCH_CARD.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Scratch card", List.of("A mysterious ticket covered in silvery foil.", "Scratch away the surface to reveal your fortune.", "Win up to §62000§r coins - if luck’s on your side!"), null, true, 103), toVersionedJson(
                        new ChanceItemMeta(List.of(DropTableId.SCRATCH_CARD_COINS_UNDER_2K_COINS.asInt()), SoundType.SCRATCH_TICKET_START, SoundType.SCRATCH_TICKET_FINISH), 1)),


                // Currency
                new ItemEntity(new AbstractItemMeta(ItemType.COINS, ItemId.COINS.asInt(), Material.COAL, ItemRarity.NOT_APPLICABLE, "Coins", List.of("Shiny gold!"), null, true, 1.0), null),
                new ItemEntity(new AbstractItemMeta(ItemType.CURRENCY, ItemId.PLATINUM_TOKEN.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Platinum Token", List.of("A Platinum token that is worth approximately 1k gold pieces."), null, true, 1.0), null),
                new ItemEntity(new AbstractItemMeta(ItemType.CURRENCY, ItemId.RECRUIT_TOKEN.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Recruit Token", List.of("A Recruit Token is proof of persuasion. Whether through charm, promise, or necessity", "You convinced another to step into the fray. The Guild does not ask how.", "It only records that the world has grown more crowded."), null, false, 1.0), null),

                // Arrows
                new ItemEntity(new AbstractItemMeta(ItemType.ARROW, ItemId.BRONZE_ARROW.asInt(), Material.ARROW, ItemRarity.COMMON, "Bronze Arrow", List.of("A basic bronze-tipped arrow.", "Useful for training ranged skills."), null, true, CRAFTED_ITEM_MULTIPLIER), toVersionedJson(
                        new EquipableTypeMeta(null, null, null,
                                List.of(
                                        new Modifier(ModifierType.RANGED_STRENGTH, 3)
                                ), List.of(new SkillRequirement(SkillType.RANGED, 1)),
                                null),
                        1)),

                new ItemEntity(new AbstractItemMeta(ItemType.ARROW, ItemId.IRON_ARROW.asInt(), Material.ARROW, ItemRarity.COMMON, "Iron Arrow", List.of("Stronger than bronze arrows.", "Commonly used by beginner archers."), null, true, CRAFTED_ITEM_MULTIPLIER), toVersionedJson(
                        new EquipableTypeMeta(null, null, null,
                                List.of(
                                        new Modifier(ModifierType.RANGED_STRENGTH, 7)
                                ), List.of(new SkillRequirement(SkillType.RANGED, 10)),
                                null),
                        1)),

                new ItemEntity(new AbstractItemMeta(ItemType.ARROW, ItemId.STEEL_ARROW.asInt(), Material.ARROW, ItemRarity.COMMON, "Steel Arrow", List.of("A sturdy steel-tipped arrow.", "Offers good damage and durability."), null, true, CRAFTED_ITEM_MULTIPLIER), toVersionedJson(
                        new EquipableTypeMeta(null, null, null,
                                List.of(
                                        new Modifier(ModifierType.RANGED_STRENGTH, 9)
                                ), List.of(new SkillRequirement(SkillType.RANGED, 20)),
                                null),
                        1)),

                new ItemEntity(new AbstractItemMeta(ItemType.ARROW, ItemId.MITHRIL_ARROW.asInt(), Material.ARROW, ItemRarity.UNCOMMON, "Mithril Arrow", List.of("Lightweight yet powerful.", "Favored by mid-level rangers."), null, true, CRAFTED_ITEM_MULTIPLIER), toVersionedJson(
                        new EquipableTypeMeta(null, null, null,
                                List.of(
                                        new Modifier(ModifierType.RANGED_STRENGTH, 12)
                                ), List.of(new SkillRequirement(SkillType.RANGED, 30)),
                                null),
                        1)),

                new ItemEntity(new AbstractItemMeta(ItemType.ARROW, ItemId.ADAMANT_ARROW.asInt(), Material.ARROW, ItemRarity.UNCOMMON, "Adamant Arrow", List.of("Forged from adamantite.", "High damage for experienced archers."), null, true, CRAFTED_ITEM_MULTIPLIER), toVersionedJson(
                        new EquipableTypeMeta(null, null, null,
                                List.of(
                                        new Modifier(ModifierType.RANGED_STRENGTH, 14)
                                ), List.of(new SkillRequirement(SkillType.RANGED, 40)),
                                null),
                        1)),

                new ItemEntity(new AbstractItemMeta(ItemType.ARROW, ItemId.RUNE_ARROW.asInt(), Material.ARROW, ItemRarity.RARE, "Runite Arrow", List.of("Top-tier arrow made from runite.", "Delivers deadly ranged attacks."), null, true, CRAFTED_ITEM_MULTIPLIER), toVersionedJson(
                        new EquipableTypeMeta(null, null, null,
                                List.of(
                                        new Modifier(ModifierType.RANGED_STRENGTH, 22)
                                ), List.of(new SkillRequirement(SkillType.RANGED, 50)),
                                null),
                        1)),


                // Tools
                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.AMULET_MOULD.asInt(), Material.COAL, ItemRarity.COMMON, "Necklace Mould", List.of("A mould for casting metal necklaces."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new EquipableTypeMeta(null, null, null, null, null, null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.RING_MOULD.asInt(), Material.COAL, ItemRarity.COMMON, "Ring Mould", List.of("A mould for casting rings."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new EquipableTypeMeta(null, null, null, null, null, null), 1)),


                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.NEEDLE.asInt(), Material.COAL, ItemRarity.COMMON, "Needle", List.of("A small, sharp tool used for sewing or crafting."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new EquipableTypeMeta(null, null, null, null, null, null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.THREAD.asInt(), Material.COAL, ItemRarity.COMMON, "Thread", List.of("A thin strand of fiber used with a needle for sewing or crafting."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(6), 1)),


                new ItemEntity(new AbstractItemMeta(ItemType.COMPASS, ItemId.COMPASS.asInt(), Material.COMPASS, ItemRarity.COMMON, "Compass", List.of("A simple tool to help you find your way."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(null, null, null,
                                        null, null, null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.SMITHING_HAMMER.asInt(), Material.WOODEN_AXE, ItemRarity.COMMON, "Hammer", List.of("A sturdy hammer used for refining and forging equipment."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(null, null, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_CRUSH, 4),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 2)
                                        ), null, null),
                                1)),

                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.FLINT_AND_STEEL.asInt(), Material.FLINT_AND_STEEL, ItemRarity.COMMON, "Flint and Steel", List.of("A nice tool to start a fire."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(null, null, null,
                                        null, null, null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.SHEARS.asInt(), Material.SHEARS, ItemRarity.COMMON, "Shears", List.of("Used to trim wool or foliage."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(null, null, null,
                                        null, null, null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.CHISEL.asInt(), Material.WOODEN_AXE, ItemRarity.COMMON, "Chisel", List.of("Used for carving or shaping materials."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(null, null, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_STAB, 4),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 2)
                                        ), null, null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.KNIFE.asInt(), Material.WOODEN_AXE, ItemRarity.COMMON, "Knife", List.of("A small, sharp blade.", "Used for cutting, carving, and crafting."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new EquipableTypeMeta(null, null, null, List.of(
                                new Modifier(ModifierType.ATTACK_SLASH, 6),
                                new Modifier(ModifierType.STRENGTH_BONUS, 2)
                        ), null, null), 1)),


                // Materials
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.COPPER_ORE.asInt(), Material.COAL, ItemRarity.COMMON, "Copper Ore", List.of("Raw copper ore, ready to be smelted into metal."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(80), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.TIN_ORE.asInt(), Material.COAL, ItemRarity.COMMON, "Tin Ore", List.of("Raw tin ore, ready to be smelted into metal."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(60), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.COAL_ORE.asInt(), Material.COAL, ItemRarity.COMMON, "Coal Ore", List.of("Unrefined coal ore, useful as a fuel source."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(150), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.PURE_COAL_ORE.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Purified Coal Ore", List.of("Refined coal ore without impurities."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(700), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.IRON_ORE.asInt(), Material.COAL, ItemRarity.COMMON, "Iron Ore", List.of("Heavy iron ore, a basic material for crafting."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(175), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.GOLD_ORE.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Gold Ore", List.of("Raw rock flecked with gold."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(220), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.MITHRIL_ORE.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Mithril Ore", List.of("Rare mithril ore, prized for its lightweight strength."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(600), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.ADAMANT_ORE.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Adamantite Ore", List.of("Tough adamantite ore, used for elite armor and weapons."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(1500), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RUNE_ORE.asInt(), Material.COAL, ItemRarity.RARE, "Runite Ore", List.of("Ancient runite ore, infused with magical properties."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(4000), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.BRONZE_BAR.asInt(), Material.COAL, ItemRarity.COMMON, "Bronze Bar", List.of("Smelted bronze bar, used to forge basic equipment."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.IRON_BAR.asInt(), Material.COAL, ItemRarity.COMMON, "Iron Bar", List.of("Solid iron bar, reliable for sturdy weapons and armor."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.STEEL_BAR.asInt(), Material.COAL, ItemRarity.COMMON, "Steel Bar", List.of("Refined steel bar, favored by seasoned blacksmiths."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.GOLD_BAR.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Gold Bar", List.of("Refined gold cast into a bar."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.MITHRIL_BAR.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Mithril Bar", List.of("Light yet strong mithril bar, perfect for advanced gear."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.ADAMANT_BAR.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Adamantite Bar", List.of("Durable adamantite bar, for crafting superior items."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RUNE_BAR.asInt(), Material.COAL, ItemRarity.RARE, "Runite Bar", List.of("Runite bar pulsing with arcane energy, highly prized."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RAW_CHICKEN.asInt(), Material.COAL, ItemRarity.COMMON, "Raw Chicken", List.of("A slab of uncooked poultry, still fresh."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(150), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RAW_MEAT.asInt(), Material.COAL, ItemRarity.COMMON, "Raw Meat", List.of("Uncooked cut of meat, ideal for hearty meals."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(150), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RAW_HERRING.asInt(), Material.COAL, ItemRarity.COMMON, "Raw Herring", List.of("A slim, slippery river fish."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(150), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RAW_MINNOW.asInt(), Material.COAL, ItemRarity.COMMON, "Raw Minnow", List.of("A tiny fish, barely worth a bite."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(100), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RAW_TROUT.asInt(), Material.COAL, ItemRarity.COMMON, "Raw Trout", List.of("Caught fresh from the stream."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(250), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RAW_SALMON.asInt(), Material.COAL, ItemRarity.COMMON, "Raw Salmon", List.of("Firm and pink, ready to roast."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(300), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RAW_TUNA.asInt(), Material.COAL, ItemRarity.COMMON, "Raw Tuna", List.of("Heavy and dense sea catch."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(320), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RAW_CRAWLER.asInt(), Material.COAL, ItemRarity.COMMON, "Raw Crawler", List.of("Squirmy shelled creature from the reef."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(420), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RAW_PIKE.asInt(), Material.COAL, ItemRarity.COMMON, "Raw Pike", List.of("Aggressive lake fish with sharp bones."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(500), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RAW_EEL.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Raw Eel", List.of("Slippery and tough to handle."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(700), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RAW_SHARK.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Raw Shark", List.of("Muscle-bound predator, not to be eaten raw."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(1500), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RAW_TURTLE.asInt(), Material.COAL, ItemRarity.RARE, "Raw Turtle", List.of("Its thick shell guards rich meat inside."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(1600), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RAW_RAY.asInt(), Material.COAL, ItemRarity.RARE, "Raw Ray", List.of("Soft flesh beneath leathery wings."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(1900), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.LOG.asInt(), Material.OAK_LOG, ItemRarity.COMMON, "Log", List.of("A basic log used for crafting and building."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(150), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.BIRCH_LOG.asInt(), Material.BIRCH_LOG, ItemRarity.COMMON, "Birch Log", List.of("A pale and smooth type of wood."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(250), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.MAPLE_LOG.asInt(), Material.ACACIA_LOG, ItemRarity.UNCOMMON, "Maple Log", List.of("A strong and versatile log with a reddish hue."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(400), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.YEW_LOG.asInt(), Material.DARK_OAK_LOG, ItemRarity.UNCOMMON, "Yew Log", List.of("A dense and durable wood prized in crafting."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(650), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.MAHOGANY_LOG.asInt(), Material.MANGROVE_LOG, ItemRarity.UNCOMMON, "Mahogany Log", List.of("A rich, dark wood valued for fine construction."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(1050), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.MAGIC_LOG.asInt(), Material.CHERRY_LOG, ItemRarity.RARE, "Magic Log", List.of("Glows faintly with arcane energy, rare and powerful."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(1400), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.ASHES.asInt(), Material.COAL, ItemRarity.COMMON, "Ashes", List.of("Fine powder left after fire consumes wood."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(1), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.MAGIC_ASHES.asInt(), Material.COAL, ItemRarity.RARE, "Magic Ashes", List.of("Glowing remnants infused with mysterious arcane power."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(500), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.BALL_OF_WOOL.asInt(), Material.COAL, ItemRarity.COMMON, "Ball of Wool", List.of("Spun from soft tufts of raw wool.", "Used in various crafting recipes."), null, true, 2),
                        toVersionedJson(new MaterialMeta(0), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.WOOL.asInt(), Material.COAL, ItemRarity.COMMON, "Wool", List.of("Soft tufts of freshly shorn wool", "Requires spinning before use."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(30), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.FLAX.asInt(), Material.COAL, ItemRarity.COMMON, "Flax", List.of("A bundle of fibrous plant stalks.", "Can be spun into bow string."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(10), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.BOW_STRING.asInt(), Material.COAL, ItemRarity.COMMON, "Bow String", List.of("Spun from processed flax.", "Used in bow crafting."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(25), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.MAGIC_BOW_STRING.asInt(), Material.COAL, ItemRarity.RARE, "Magic Bow String", List.of("Spun from flax and a magic ingredient.", "Used to string magic amulets or bows."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.ARROW_SHAFT.asInt(), Material.COAL, ItemRarity.COMMON, "Arrow Shaft", List.of("A basic shaft for making arrows.", "Needs feathers to complete."), null, true, 0.75),
                        toVersionedJson(new MaterialMeta(0), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.DRAGON_LEATHER.asInt(), Material.COAL, ItemRarity.RARE, "Dragon Leather", List.of("Tough and scaly leather from a dragon.", "Used to craft powerful armor."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(1500), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.FEATHER.asInt(), Material.COAL, ItemRarity.COMMON, "Feather", List.of("Light and soft bird feather.", "Useful for fletching arrows."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(5), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.HARD_LEATHER.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Hard Leather", List.of("Treated for durability.", "Used to craft sturdy gear."), null, true, 1.5),
                        toVersionedJson(new MaterialMeta(200), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.HEADLESS_ARROW.asInt(), Material.COAL, ItemRarity.COMMON, "Headless Arrow", List.of("An unfinished arrow lacking a tip.", "Needs a metal head to be complete."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.LEATHER.asInt(), Material.COAL, ItemRarity.COMMON, "Leather", List.of("Tanned animal hide.", "Used in basic crafting and armor."), null, true, 1.5),
                        toVersionedJson(new MaterialMeta(100), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.BRONZE_ARROW_TIPS.asInt(), Material.COAL, ItemRarity.COMMON, "Bronze Arrowtips", List.of("Forged from bronze.", "Used to make basic arrows."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.IRON_ARROW_TIPS.asInt(), Material.COAL, ItemRarity.COMMON, "Iron Arrowtips", List.of("Sharpened iron points.", "Commonly used for training."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.STEEL_ARROW_TIPS.asInt(), Material.COAL, ItemRarity.COMMON, "Steel Arrowtips", List.of("Harder than iron.", "Used for stronger arrows."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.MITHRIL_ARROW_TIPS.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Mithril Arrowtips", List.of("Lightweight and sharp.", "Favored by skilled archers."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.ADAMANT_ARROW_TIPS.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Adamantite Arrowtips", List.of("Tough and deadly.", "Crafted for elite arrows."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RUNE_ARROW_TIPS.asInt(), Material.COAL, ItemRarity.RARE, "Runite Arrowtips", List.of("Forged from rare runite.", "Used in mastercrafted ammunition."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.SHORTBOW_UNFINISHED.asInt(), Material.COAL, ItemRarity.COMMON, "Shortbow (unfinished)", List.of("An unstrung shortbow.", "Needs a bowstring to complete."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.LONGBOW_UNFINISHED.asInt(), Material.COAL, ItemRarity.COMMON, "Longbow (unfinished)", List.of("An unstrung longbow.", "Needs a bowstring to complete."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.BIRCH_SHORTBOW_UNFINISHED.asInt(), Material.COAL, ItemRarity.COMMON, "Birch Shortbow (unfinished)", List.of("An unstrung birch shortbow.", "Needs a bowstring to complete."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.BIRCH_LONGBOW_UNFINISHED.asInt(), Material.COAL, ItemRarity.COMMON, "Birch Longbow (unfinished)", List.of("An unstrung birch longbow.", "Needs a bowstring to complete."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.MAPLE_LONGBOW_UNFINISHED.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Maple Longbow (unfinished)", List.of("An unstrung maple longbow.", "Needs a bowstring to complete."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.MAPLE_SHORTBOW_UNFINISHED.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Maple Shortbow (unfinished)", List.of("An unstrung maple shortbow.", "Needs a bowstring to complete."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.YEW_SHORTBOW_UNFINISHED.asInt(), Material.COAL, ItemRarity.RARE, "Yew Shortbow (unfinished)", List.of("An unstrung yew shortbow.", "Needs a bowstring to complete."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.YEW_LONGBOW_UNFINISHED.asInt(), Material.COAL, ItemRarity.RARE, "Yew Longbow (unfinished)", List.of("An unstrung yew longbow.", "Needs a bowstring to complete."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.MAGIC_SHORTBOW_UNFINISHED.asInt(), Material.COAL, ItemRarity.COMMON, "Magic Shortbow (unfinished)", List.of("An unstrung magic shortbow.", "Needs a bowstring to complete."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.MAGIC_LONGBOW_UNFINISHED.asInt(), Material.COAL, ItemRarity.COMMON, "Magic Longbow (unfinished)", List.of("An unstrung magic longbow.", "Needs a bowstring to complete."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(new MaterialMeta(0), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.COWHIDE.asInt(), Material.COAL, ItemRarity.COMMON, "Cowhide", List.of("A thick, durable hide taken from a domesticated cow.", "Can be used for crafting light armor."), null, true, 1.5),
                        toVersionedJson(new MaterialMeta(50), 1)),


                // Gems
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.UNCUT_SAPPHIRE.asInt(), Material.COAL, ItemRarity.COMMON, "Uncut Sapphire", List.of("A rough, uncut sapphire.", "Can be cut with a chisel."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(900), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.UNCUT_EMERALD.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Uncut Emerald", List.of("A rough, uncut emerald.", "Can be cut with a chisel."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(1600), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.UNCUT_RUBY.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Uncut Ruby", List.of("A rough, uncut ruby.", "Can be cut with a chisel."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(2400), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.UNCUT_DIAMOND.asInt(), Material.COAL, ItemRarity.RARE, "Uncut Diamond", List.of("A rough, uncut diamond.", "Can be cut with a chisel."), null, true, 1.0),
                        toVersionedJson(new MaterialMeta(3200), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.SAPPHIRE.asInt(), Material.COAL, ItemRarity.COMMON, "Sapphire", List.of("A polished sapphire gem.", "Used for crafting jewelry."), null, true, 1.3),
                        toVersionedJson(new MaterialMeta(0), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.EMERALD.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Emerald", List.of("A polished emerald gem.", "Used for crafting jewelry."), null, true, 1.3),
                        toVersionedJson(new MaterialMeta(0), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.RUBY.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Ruby", List.of("A polished ruby gem.", "Used for crafting jewelry."), null, true, 1.3),
                        toVersionedJson(new MaterialMeta(0), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.DIAMOND.asInt(), Material.COAL, ItemRarity.RARE, "Diamond", List.of("A polished diamond gem.", "Used for crafting jewelry."), null, true, 1.3),
                        toVersionedJson(new MaterialMeta(0), 1)),


                new ItemEntity(new AbstractItemMeta(
                        ItemType.REMAINS, ItemId.BONES.asInt(), Material.COAL, ItemRarity.COMMON,
                        "Bones",
                        List.of("Weathered fragments of a fallen creature."),
                        null, true, 1.0),
                        toVersionedJson(new RemainsItemMeta(10, 10, 1000), 1)),

                new ItemEntity(new AbstractItemMeta(
                        ItemType.REMAINS, ItemId.BIG_BONES.asInt(), Material.COAL, ItemRarity.COMMON,
                        "Big Bones",
                        List.of("Sturdy bones from a larger beast, still faintly echoing its strength."),
                        null, true, 1.0),
                        toVersionedJson(new RemainsItemMeta(20, 25, 2000), 1)),

                new ItemEntity(new AbstractItemMeta(
                        ItemType.REMAINS, ItemId.LARGE_BONES.asInt(), Material.COAL, ItemRarity.COMMON,
                        "Large Bones",
                        List.of("Massive skeletal remains, once supporting a formidable creature."),
                        null, true, 1.0),
                        toVersionedJson(new RemainsItemMeta(150, 70, 3000), 1)),

                new ItemEntity(new AbstractItemMeta(
                        ItemType.REMAINS, ItemId.HUGE_BONES.asInt(), Material.COAL, ItemRarity.COMMON,
                        "Huge Bones",
                        List.of("Colossal bones that whisper of a beast towering above mortals."),
                        null, true, 1.0),
                        toVersionedJson(new RemainsItemMeta(500, 120, 3000), 1)),


                new ItemEntity(new AbstractItemMeta(ItemType.EMPTY_CONTAINER, ItemId.EMPTY_POT.asInt(), Material.COAL, ItemRarity.COMMON, "Empty Pot", List.of("A regular pot, made of clay."), null, true, 1.0),
                        toVersionedJson(new EmptyContainerMeta(List.of(
                                new EmptyContainer.EmptyContainerConfig(EmptyContainer.InputType.FLOUR, ItemId.POT_OF_FLOUR.asInt())
                        )), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.POT_OF_FLOUR.asInt(), Material.COAL, ItemRarity.COMMON, "Pot of flour", List.of("A pot filled with flour."), null, true, 1.0), null),

                new ItemEntity(new AbstractItemMeta(ItemType.EMPTY_CONTAINER, ItemId.EMPTY_BUCKET.asInt(), Material.COAL, ItemRarity.COMMON, "Empty Bucket", List.of("A regular bucket, made of metal."), null, true, 1.0),
                        toVersionedJson(new EmptyContainerMeta(List.of(
                                new EmptyContainer.EmptyContainerConfig(EmptyContainer.InputType.WATER, ItemId.BUCKET_OF_WATER.asInt())
                        )), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.BUCKET_OF_WATER.asInt(), Material.COAL, ItemRarity.COMMON, "Bucket of water", List.of("A bucket filled with water."), null, true, 1.0), null),


                new ItemEntity(new AbstractItemMeta(ItemType.EMPTY_CONTAINER, ItemId.EMPTY_VIAL.asInt(), Material.COAL, ItemRarity.COMMON, "Empty Vial", List.of("A regular vial, made of glass."), null, true, 1.0),
                        toVersionedJson(new EmptyContainerMeta(List.of(
                                new EmptyContainer.EmptyContainerConfig(EmptyContainer.InputType.WATER, ItemId.VIAL_OF_WATER.asInt()),
                                new EmptyContainer.EmptyContainerConfig(EmptyContainer.InputType.LAVA, ItemId.VIAL_OF_LAVA.asInt())
                        )), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.VIAL_OF_WATER.asInt(), Material.COAL, ItemRarity.COMMON, "Vial of water", List.of("A vial filled with water."), null, true, 1.0), toVersionedJson(new MaterialMeta(60), 1)),
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL, ItemId.VIAL_OF_LAVA.asInt(), Material.COAL, ItemRarity.COMMON, "Vial of lava", List.of("A vial filled with lava."), null, true, 1.0), toVersionedJson(new MaterialMeta(80), 1)),

                // Fairy food
                new ItemEntity(new AbstractItemMeta(ItemType.FAIRY_FOOD, ItemId.REGULAR_FAIRY_RATION.asInt(), Material.COAL, ItemRarity.RARE, "Fairy Ration", List.of("Regular food for fairies.", "Keeps them strong and energized."), null, true, 1.0),
                        toVersionedJson(new FairyFoodMeta(15), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FAIRY_FOOD, ItemId.FINE_FAIRY_RATION.asInt(), Material.COAL, ItemRarity.EPIC, "Fine Fairy Ration", List.of("Fine food for fairies.", "Keeps them strong and energized."), null, true, 1.0),
                        toVersionedJson(new FairyFoodMeta(40), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FAIRY_FOOD, ItemId.SUPERIOR_FAIRY_RATION.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Superior Fairy Ration", List.of("Superior food for fairies.", "Keeps them strong and energized."), null, true, 1.0),
                        toVersionedJson(new FairyFoodMeta(80), 1)),


                // Fairy
                new ItemEntity(new AbstractItemMeta(ItemType.FAIRY, ItemId.FAIRY_OF_HOPE.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Fairy of Hope", List.of("A gentle sprite whose presence inspires courage and renewal."), null, true, 1.0),
                        toVersionedJson(new FairyMeta("cubee-swamps", 1), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FAIRY, ItemId.FAIRY_OF_LIGHT.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Fairy of Light", List.of("A radiant fairy that illuminates the darkest paths with pure brilliance."), null, true, 1.0),
                        toVersionedJson(new FairyMeta("cubee-good", 1), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FAIRY, ItemId.FAIRY_OF_SILENCE.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Fairy of Silence", List.of("Whispers softly in the quiet, muffling sound and calm unrest."), null, true, 1.0),
                        toVersionedJson(new FairyMeta("cubee-stone", 1), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FAIRY, ItemId.FAIRY_OF_DARKNESS.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Fairy of Darkness", List.of("A shadowy companion that thrives where light dares not linger."), null, true, 1.0),
                        toVersionedJson(new FairyMeta("cubee-skeleton", 1), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FAIRY, ItemId.FAIRY_OF_FROST.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Fairy of Frost", List.of("A chilly wisp that freezes the air and slows the fiercest foes."), null, true, 1.0),
                        toVersionedJson(new FairyMeta("cubee-snow", 1), 1)),

                // Fairy Upgrade
                new ItemEntity(new AbstractItemMeta(ItemType.FAIRY_FRUIT, ItemId.FAIRY_ATTACK_UPGRADE.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Blazing Apple",
                        List.of("A crisp red apple with a fiery aura.", "Empowers your fairy to enhance your Attack."), null, true, 1.0),
                        toVersionedJson(new FairyFruitMeta(null, List.of(new SkillModifier(SkillType.ATTACK, 1, null))), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FAIRY_FRUIT, ItemId.FAIRY_DEFENCE_UPGRADE.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Stoneblue Berry",
                        List.of("A cluster of enchanted blueberries, dense with magic.", "Strengthens your Defence through your fairy's help."), null, true, 1.0),
                        toVersionedJson(new FairyFruitMeta(null, List.of(new SkillModifier(SkillType.DEFENCE, 1, null))), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FAIRY_FRUIT, ItemId.FAIRY_STRENGTH_UPGRADE.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Inferno Dragonfruit",
                        List.of("A rare dragonfruit pulsing with raw power.", "Bolsters your Strength using your bonded fairy."), null, true, 1.0),
                        toVersionedJson(new FairyFruitMeta(null, List.of(new SkillModifier(SkillType.STRENGTH, 1, null))), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FAIRY_FRUIT, ItemId.FAIRY_HITPOINTS_UPGRADE.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Golden Marmelade",
                        List.of("A jar of radiant marmelade, sweet and soothing.", "Boosts your Hitpoints through your fairy’s magic."), null, true, 1.0),
                        toVersionedJson(new FairyFruitMeta(null, List.of(new SkillModifier(SkillType.HITPOINTS, 1, null))), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FAIRY_FRUIT, ItemId.FAIRY_RANGE_UPGRADE.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Whispering Grapes",
                        List.of("A bunch of shimmering grapes that hum with energy.", "Improves your Ranged skill via fairy essence."), null, true, 1.0),
                        toVersionedJson(new FairyFruitMeta(null, List.of(new SkillModifier(SkillType.RANGED, 1, null))), 1)),

                // Scrolls
                new ItemEntity(new AbstractItemMeta(ItemType.MATERIAL_SCROLL, ItemId.FUSION_SCROLL.asInt(), Material.COAL, ItemRarity.LEGENDARY, "Fusion Scroll", List.of("A mystical scroll used to embed gems into gear.", "Essential for upgrading equipment with gem-based attributes."), null, true, 1.0), null),


                // Gems
                new ItemEntity(new AbstractItemMeta(ItemType.GEM, ItemId.BRUTALITE_CORE.asInt(), Material.COAL, ItemRarity.MYTHIC, "Brutalite Core",
                        List.of("A gem that increases the wearer’s physical power, boosting strength attributes."), null, true, 1.0),
                        toVersionedJson(
                                new GemMeta(null, List.of(new Modifier(ModifierType.STRENGTH_BONUS, 8))), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.GEM, ItemId.OBSIDIAN_BULWARK.asInt(), Material.COAL, ItemRarity.MYTHIC, "Obsidian Bulwark",
                        List.of("A gem that bolsters resilience, reinforcing the wearer’s defenses."), null, true, 1.0),
                        toVersionedJson(
                                new GemMeta(null, List.of(
                                        new Modifier(ModifierType.DEFENCE_CRUSH, 6),
                                        new Modifier(ModifierType.DEFENCE_SLASH, 6),
                                        new Modifier(ModifierType.DEFENCE_STAB, 6)
                                )), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.GEM, ItemId.BLADESTONE.asInt(), Material.COAL, ItemRarity.MYTHIC, "Bladestone",
                        List.of("A gem that enhances offensive prowess, increasing successful hit chance."), null, true, 1.0),
                        toVersionedJson(
                                new GemMeta(null, List.of(
                                        new Modifier(ModifierType.ATTACK_STAB, 6),
                                        new Modifier(ModifierType.ATTACK_SLASH, 6),
                                        new Modifier(ModifierType.ATTACK_CRUSH, 6)
                                )), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.GEM, ItemId.ARCHERS_EMBER.asInt(), Material.COAL, ItemRarity.MYTHIC, "Archer’s Ember",
                        List.of("A gem that sharpens precision and power at range, boosting ranged strength."), null, true, 1.0),
                        toVersionedJson(
                                new GemMeta(null, List.of(new Modifier(ModifierType.RANGED_STRENGTH, 8))), 1)),

                // Misc
                new ItemEntity(new AbstractItemMeta(ItemType.MISC, ItemId.BURNT_FOOD.asInt(), Material.COAL, ItemRarity.NOT_APPLICABLE, "Charred Remains", List.of("All that's left after fire claims its meal."), null, true, 1.0), null),

                // Food
                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.POTATO.asInt(), Material.COOKED_SALMON, ItemRarity.COMMON, "Potato",
                        List.of("A nutritious potato."), null, true, CRAFTED_ITEM_MULTIPLIER), toVersionedJson(new FoodTypeMeta(2, 25), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.CARROT.asInt(), Material.COOKED_SALMON, ItemRarity.COMMON, "Carrot",
                        List.of("A crunchy, sweet root vegetable."), null, true, 1.3), toVersionedJson(new FoodTypeMeta(1, 15), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_CHICKEN.asInt(), Material.COOKED_SALMON, ItemRarity.COMMON, "Cooked Chicken",
                        List.of("Tender meat roasted to perfection."), null, true, 1.3), toVersionedJson(new FoodTypeMeta(3, null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_MEAT.asInt(), Material.COOKED_SALMON, ItemRarity.COMMON, "Cooked Meat",
                        List.of("Cooked to a golden brown, still juicy inside."), null, true, 1.3), toVersionedJson(new FoodTypeMeta(3, null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_HERRING.asInt(), Material.COOKED_SALMON, ItemRarity.COMMON, "Cooked Herring",
                        List.of("Lightly roasted river fish, a quick meal."), null, true, 1.3), toVersionedJson(new FoodTypeMeta(3, null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_MINNOW.asInt(), Material.COOKED_SALMON, ItemRarity.COMMON, "Cooked Minnow",
                        List.of("Tiny fish, crisped over a fire."), null, true, 1.3), toVersionedJson(new FoodTypeMeta(1, null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_TROUT.asInt(), Material.COOKED_SALMON, ItemRarity.COMMON, "Cooked Trout",
                        List.of("Flaky and rich, a river-dweller's feast."), null, true, 1.3), toVersionedJson(new FoodTypeMeta(7, null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_SALMON.asInt(), Material.COOKED_SALMON, ItemRarity.COMMON, "Cooked Salmon",
                        List.of("Bold flavor, pink flesh, campfire ready."), null, true, 1.3), toVersionedJson(new FoodTypeMeta(9, null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_TUNA.asInt(), Material.COOKED_SALMON, ItemRarity.COMMON, "Cooked Tuna",
                        List.of("Dense meat, satisfying and nutritious."), null, true, 1.3), toVersionedJson(new FoodTypeMeta(10, null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_CRAWLER.asInt(), Material.COOKED_SALMON, ItemRarity.COMMON, "Cooked Crawler",
                        List.of("Charred crustacean, crunchy and filling."), null, true, 1.3), toVersionedJson(new FoodTypeMeta(12, null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_PIKE.asInt(), Material.COOKED_SALMON, ItemRarity.COMMON, "Cooked Pike",
                        List.of("A sharp-finned delicacy from cold waters."), null, true, 1.3), toVersionedJson(new FoodTypeMeta(14, null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_EEL.asInt(), Material.COOKED_SALMON, ItemRarity.UNCOMMON, "Cooked Eel",
                        List.of("Grilled serpent of the deep marshes."), null, true, 1.3), toVersionedJson(new FoodTypeMeta(16, null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_SHARK.asInt(), Material.COOKED_SALMON, ItemRarity.UNCOMMON, "Cooked Shark",
                        List.of("Meaty apex predator, expertly seared."), null, true, 1.3), toVersionedJson(new FoodTypeMeta(20, null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_TURTLE.asInt(), Material.COOKED_SALMON, ItemRarity.RARE, "Cooked Turtle",
                        List.of("Tender shell beast meat, slow-cooked to perfection."), null, true, 1.3), toVersionedJson(new FoodTypeMeta(21, null), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_RAY.asInt(), Material.COOKED_SALMON, ItemRarity.RARE, "Cooked Ray",
                        List.of("Wings of the deep skyfish, rich in nutrients."), null, true, 1.3), toVersionedJson(new FoodTypeMeta(22, null), 1)),

                // Potions
                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.DEFENCE_POTION_I.asInt(), Material.MILK_BUCKET, ItemRarity.UNCOMMON, "Defence Potion I",
                        List.of("Increases your defense temporarily.", "Brewed by skilled alchemists."), null, true, 1.0), toVersionedJson(new PotionMeta(2, 2,
                        new StatsEffect("defence-potion", 120, List.of(), List.of(new SkillModifier(SkillType.DEFENCE, 3, null))), CustomModelId.DEFENCE_POTION_I), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.ATTACK_POTION_I.asInt(), Material.MILK_BUCKET, ItemRarity.UNCOMMON, "Attack Potion I",
                        List.of("Boosts your attack power.", "Unleash your fury in battle."), null, true, 1.0), toVersionedJson(new PotionMeta(2, 2,
                        new StatsEffect("attack-potion", 120, List.of(), List.of(new SkillModifier(SkillType.ATTACK, 3, null))), CustomModelId.ATTACK_POTION_I), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.RANGED_POTION_I.asInt(), Material.MILK_BUCKET, ItemRarity.UNCOMMON, "Ranged Potion I",
                        List.of("Enhances your ranged abilities.", "A favorite among archers."), null, true, 1.0), toVersionedJson(new PotionMeta(2, 2,
                        new StatsEffect("ranged-potion", 120, List.of(), List.of(new SkillModifier(SkillType.RANGED, 3, null))), CustomModelId.RANGED_POTION_I), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.MAX_HEALTH_POTION_I.asInt(), Material.MILK_BUCKET, ItemRarity.UNCOMMON, "Max Health Potion I",
                        List.of("Boosts small amount of your maximum health.", "Always good to have a few."), null, true, 1.0), toVersionedJson(new PotionMeta(2, 2,
                        new StatsEffect("max-health-potion", 120, List.of(), List.of(new SkillModifier(SkillType.HITPOINTS, 3, null))), CustomModelId.MAX_HP_POTION_I), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.STRENGTH_POTION_I.asInt(), Material.MILK_BUCKET, ItemRarity.UNCOMMON, "Strength Potion I",
                        List.of("Temporarily increases strength.", "Tear through enemies with ease."), null, true, 1.0), toVersionedJson(new PotionMeta(2, 2,
                        new StatsEffect("strength-potion", 120, List.of(), List.of(new SkillModifier(SkillType.STRENGTH, 3, null))), CustomModelId.STRENGTH_POTION_I), 1)),


                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.DEFENCE_POTION_II.asInt(), Material.MILK_BUCKET, ItemRarity.RARE, "Defence Potion II",
                        List.of("Increases your defense temporarily.", "Brewed by skilled alchemists."), null, true, 1.0), toVersionedJson(new PotionMeta(3, 3,
                        new StatsEffect("defence-potion", 240, List.of(), List.of(new SkillModifier(SkillType.DEFENCE, 5, null))), CustomModelId.DEFENCE_POTION_II), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.ATTACK_POTION_II.asInt(), Material.MILK_BUCKET, ItemRarity.RARE, "Attack Potion II",
                        List.of("Boosts your attack power.", "Unleash your fury in battle."), null, true, 1.0), toVersionedJson(new PotionMeta(3, 3,
                        new StatsEffect("attack-potion", 240, List.of(), List.of(new SkillModifier(SkillType.ATTACK, 5, null))), CustomModelId.ATTACK_POTION_II), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.RANGED_POTION_II.asInt(), Material.MILK_BUCKET, ItemRarity.RARE, "Ranged Potion II",
                        List.of("Enhances your ranged abilities.", "A favorite among archers."), null, true, 1.0), toVersionedJson(new PotionMeta(3, 3,
                        new StatsEffect("ranged-potion", 240, List.of(), List.of(new SkillModifier(SkillType.RANGED, 5, null))), CustomModelId.RANGED_POTION_II), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.MAX_HEALTH_POTION_II.asInt(), Material.MILK_BUCKET, ItemRarity.RARE, "Max Health Potion II",
                        List.of("Boosts small amount of your maximum health.", "Always good to have a few."), null, true, 1.0), toVersionedJson(new PotionMeta(3, 3,
                        new StatsEffect("max-health-potion", 240, List.of(), List.of(new SkillModifier(SkillType.HITPOINTS, 5, null))), CustomModelId.HEALTH_POTION_II), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.STRENGTH_POTION_II.asInt(), Material.MILK_BUCKET, ItemRarity.RARE, "Strength Potion II",
                        List.of("Temporarily increases strength.", "Tear through enemies with ease."), null, true, 1.0), toVersionedJson(new PotionMeta(3, 3,
                        new StatsEffect("strength-potion", 240, List.of(), List.of(new SkillModifier(SkillType.STRENGTH, 5, null))), CustomModelId.STRENGTH_POTION_II), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.MINOR_HEALING_POTION.asInt(), Material.MILK_BUCKET, ItemRarity.COMMON, "Minor Healing Potion",
                        List.of("Restores a small amount of health.", "Basic healing for minor wounds."), null, true, 1.0), toVersionedJson(new PotionMeta(1, 1,
                        new StatsEffect("healing-potion", 10, List.of(new Modifier(ModifierType.HEALTH_REGENERATION, 1)), List.of()), CustomModelId.MINOR_HEALING_POTION), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.SUPREME_ATTACK_POTION.asInt(), Material.MILK_BUCKET, ItemRarity.EPIC, "Supreme Attack Potion",
                        List.of("Greatly boosts your attack power.", "Unleash supreme fury in battle."), null, true, 1.0), toVersionedJson(new PotionMeta(3, 3,
                        new StatsEffect("attack-potion", 300, List.of(), List.of(new SkillModifier(SkillType.ATTACK, 0, 10.0))), CustomModelId.SUPREME_ATTACK_POTION), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.SUPREME_STRENGTH_POTION.asInt(), Material.MILK_BUCKET, ItemRarity.EPIC, "Supreme Strength Potion",
                        List.of("Greatly increases strength temporarily.", "Tear through enemies with supreme power."), null, true, 1.0), toVersionedJson(new PotionMeta(3, 3,
                        new StatsEffect("strength-potion", 300, List.of(), List.of(new SkillModifier(SkillType.STRENGTH, 0, 10.0))), CustomModelId.SUPREME_STRENGTH_POTION), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.SUPREME_DEFENCE_POTION.asInt(), Material.MILK_BUCKET, ItemRarity.EPIC, "Supreme Defence Potion",
                        List.of("Greatly increases your defence temporarily.", "Brewed by master alchemists."), null, true, 1.0), toVersionedJson(new PotionMeta(3, 3,
                        new StatsEffect("defence-potion", 300, List.of(), List.of(new SkillModifier(SkillType.DEFENCE, 0, 10.0))), CustomModelId.SUPREME_DEFENCE_POTION), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.SUPREME_HEALTH_POTION.asInt(), Material.MILK_BUCKET, ItemRarity.EPIC, "Supreme Health Potion",
                        List.of("Boosts a large amount of your maximum health.", "The pinnacle of health enhancement."), null, true, 1.0), toVersionedJson(new PotionMeta(2, 2,
                        new StatsEffect("healing-potion", 5, List.of(new Modifier(ModifierType.HEALTH_REGENERATION, 10)), List.of()), CustomModelId.SUPREME_HEALTH_POTION), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.SUPREME_RANGED_POTION.asInt(), Material.MILK_BUCKET, ItemRarity.EPIC, "Supreme Ranged Potion",
                        List.of("Greatly enhances your ranged abilities.", "For the ultimate archer."), null, true, 1.0), toVersionedJson(new PotionMeta(3, 3,
                        new StatsEffect("ranged-potion", 300, List.of(new Modifier(ModifierType.ARROW_RECOVERY_PERCENT, 30)), List.of(new SkillModifier(SkillType.RANGED, 0, 10.0))), CustomModelId.SUPREME_RANGED_POTION), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.DRAGONS_MIGHT_POTION.asInt(), Material.MILK_BUCKET, ItemRarity.EPIC, "Dragon's Might Potion",
                        List.of("Harnesses the power of dragons.", "Feel the might of ancient beasts."), null, true, 1.0), toVersionedJson(new PotionMeta(3, 3,
                        new StatsEffect("overload-potion", 300, List.of(), List.of(new SkillModifier(SkillType.ATTACK, 0, 20.0), new SkillModifier(SkillType.STRENGTH, 0, 20.0))), CustomModelId.DRAGONS_MIGHT_POTION), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.ETHEREAL_RESTORATION.asInt(), Material.MILK_BUCKET, ItemRarity.EPIC, "Ethereal Restoration",
                        List.of("Restores energy from the ethereal plane.", "A mystical concoction."), null, true, 1.0), toVersionedJson(new PotionMeta(2, 2,
                        new StatsEffect("healing-potion", 120, List.of(new Modifier(ModifierType.HEALTH_REGENERATION, 3)), List.of()), CustomModelId.ETHEREAL_RESTORATION), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.OVERLOAD_POTION.asInt(), Material.MILK_BUCKET, ItemRarity.LEGENDARY, "Overload Potion",
                        List.of("Overloads your combat abilities.", "Pushes your limits beyond normal."), null, true, 1.0), toVersionedJson(new PotionMeta(3, 3,
                        new StatsEffect("overload-potion", 240, List.of(new Modifier(ModifierType.COMBAT_XP_BONUS_ADDITIVE, 0.10)), List.of(new SkillModifier(SkillType.ATTACK, 0, 15.0), new SkillModifier(SkillType.STRENGTH, 0, 15.0), new SkillModifier(SkillType.DEFENCE, 0, 15.0), new SkillModifier(SkillType.RANGED, 0, 15.0))), CustomModelId.OVERLOAD_POTION), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.ULTIMATE_POWER_POTION.asInt(), Material.MILK_BUCKET, ItemRarity.MYTHIC, "Ultimate Power Potion",
                        List.of("Unleashes ultimate power.", "The peak of alchemical achievement."), null, true, 1.0), toVersionedJson(new PotionMeta(3, 3,
                        new StatsEffect("overload-potion", 300, List.of(new Modifier(ModifierType.COMBAT_XP_BONUS_ADDITIVE, 0.15)), List.of(new SkillModifier(SkillType.ATTACK, 0, 20.0), new SkillModifier(SkillType.STRENGTH, 0, 20.0), new SkillModifier(SkillType.DEFENCE, 0, 20.0), new SkillModifier(SkillType.RANGED, 0, 20.0))), CustomModelId.ULTIMATE_POWER_POTION), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.GATHERING_POTION.asInt(), Material.MILK_BUCKET, ItemRarity.RARE, "Gathering Potion",
                        List.of("Enhances your gathering abilities.", "Useful for resource collection."), null, true, 1.0), toVersionedJson(new PotionMeta(3, 3,
                        new StatsEffect("gathering-potion", 300, List.of(new Modifier(ModifierType.GATHERING_LUCK, 25)), List.of()), CustomModelId.GATHERING_POTION), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.SKILLING_POTION.asInt(), Material.MILK_BUCKET, ItemRarity.RARE, "Skilling Potion",
                        List.of("Enchances your skilling learning abilities"), null, true, 1.0), toVersionedJson(new PotionMeta(3, 3,
                        new StatsEffect("skilling-potion", 300, List.of(new Modifier(ModifierType.SKILLING_XP_BONUS_ADDITIVE, 0.25)), List.of()), CustomModelId.SKILLING_POTION), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.COMBAT_POTION.asInt(), Material.MILK_BUCKET, ItemRarity.RARE, "Combat Potion",
                        List.of("Enhances your combat learning abilities."), null, true, 1.0), toVersionedJson(new PotionMeta(3, 3,
                        new StatsEffect("combat-potion", 300, List.of(new Modifier(ModifierType.COMBAT_XP_BONUS_ADDITIVE, 0.25)), List.of()), CustomModelId.COMBAT_POTION), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.PRAYER_POTION.asInt(), Material.MILK_BUCKET, ItemRarity.RARE, "Prayer Potion",
                        List.of("Restores prayer points.", "For the devout warrior."), null, true, 1.0), toVersionedJson(new PotionMeta(3, 3,
                        new StatsEffect("prayer-potion", 5, List.of(new Modifier(ModifierType.PRAYER_REGENERATION, 8)), List.of()), CustomModelId.PRAYER_POTION), 1)),

                // Tools
                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.BRONZE_PICKAXE.asInt(), Material.WOODEN_PICKAXE, ItemRarity.COMMON, "Bronze Pickaxe", List.of("Bronze pickaxe, basic mining tool."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(null, null, null,
                                        List.of(new Modifier(ModifierType.MINING_BOOST, 1)),
                                        List.of(new SkillRequirement(SkillType.MINING, 1)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.IRON_PICKAXE.asInt(), Material.WOODEN_PICKAXE, ItemRarity.COMMON, "Iron Pickaxe", List.of("Iron pickaxe, mines ores with ease."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(null, null, null,
                                        List.of(new Modifier(ModifierType.MINING_BOOST, 1.1)),
                                        List.of(new SkillRequirement(SkillType.MINING, 1)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.STEEL_PICKAXE.asInt(), Material.WOODEN_PICKAXE, ItemRarity.COMMON, "Steel Pickaxe", List.of("Steel pickaxe, efficient and strong."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(null, null, null,
                                        List.of(new Modifier(ModifierType.MINING_BOOST, 1.2)),
                                        List.of(new SkillRequirement(SkillType.MINING, 6)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.MITHRIL_PICKAXE.asInt(), Material.WOODEN_PICKAXE, ItemRarity.UNCOMMON, "Mithril Pickaxe", List.of("Mithril pickaxe, fast and durable."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(null, null, null,
                                        List.of(new Modifier(ModifierType.MINING_BOOST, 1.3)),
                                        List.of(new SkillRequirement(SkillType.MINING, 21)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.ADAMANT_PICKAXE.asInt(), Material.WOODEN_PICKAXE, ItemRarity.UNCOMMON, "Adamantite Pickaxe", List.of("Adamantite pickaxe, mines the hardest ores."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(null, null, null,
                                        List.of(new Modifier(ModifierType.MINING_BOOST, 1.5)),
                                        List.of(new SkillRequirement(SkillType.MINING, 31)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.TOOL_WITH_GEM_SOCKETS, ItemId.RUNE_PICKAXE.asInt(), Material.WOODEN_PICKAXE, ItemRarity.RARE, "Runite Pickaxe", List.of("Runite pickaxe, enchanted for maximum yield."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new SocketedToolMeta(new EquipableTypeMeta(null, null, null,
                                        List.of(new Modifier(ModifierType.MINING_BOOST, 1.8)),
                                        List.of(new SkillRequirement(SkillType.MINING, 41)),
                                        null), null, 1)
                                , 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.BRONZE_AXE.asInt(), Material.WOODEN_AXE, ItemRarity.COMMON, "Bronze Axe", List.of("Bronze axe, useful for combat and chopping."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(null, null, null,
                                        List.of(new Modifier(ModifierType.WOODCUTTING_BOOST, 1)),
                                        List.of(new SkillRequirement(SkillType.WOODCUTTING, 1)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.IRON_AXE.asInt(), Material.WOODEN_AXE, ItemRarity.COMMON, "Iron Axe", List.of("Iron axe, sturdy and reliable."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(null, null, null,
                                        List.of(new Modifier(ModifierType.WOODCUTTING_BOOST, 1.1)),
                                        List.of(new SkillRequirement(SkillType.WOODCUTTING, 1)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.STEEL_AXE.asInt(), Material.WOODEN_AXE, ItemRarity.COMMON, "Steel Axe", List.of("Steel axe, stronger and more durable."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(null, null, null,
                                        List.of(new Modifier(ModifierType.WOODCUTTING_BOOST, 1.2)),
                                        List.of(new SkillRequirement(SkillType.WOODCUTTING, 6)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.MITHRIL_AXE.asInt(), Material.WOODEN_AXE, ItemRarity.UNCOMMON, "Mithril Axe", List.of("Mithril axe, light and efficient."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(null, null, null,
                                        List.of(new Modifier(ModifierType.WOODCUTTING_BOOST, 1.3)),
                                        List.of(new SkillRequirement(SkillType.WOODCUTTING, 21)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.ADAMANT_AXE.asInt(), Material.WOODEN_AXE, ItemRarity.UNCOMMON, "Adamantite Axe", List.of("Adamantite axe, built to last."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(null, null, null,
                                        List.of(new Modifier(ModifierType.WOODCUTTING_BOOST, 1.5)),
                                        List.of(new SkillRequirement(SkillType.WOODCUTTING, 31)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.TOOL_WITH_GEM_SOCKETS, ItemId.RUNE_AXE.asInt(), Material.WOODEN_AXE, ItemRarity.RARE, "Runite Axe", List.of("Runite axe glowing with magical energy."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new SocketedToolMeta(new EquipableTypeMeta(null, null, null,
                                        List.of(new Modifier(ModifierType.WOODCUTTING_BOOST, 1.8)),
                                        List.of(new SkillRequirement(SkillType.WOODCUTTING, 41)),
                                        null), null, 1)
                                , 1)
                ),

                // Fishing
                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.FISHING_NET.asInt(), Material.COAL, ItemRarity.COMMON, "Fishing Net",
                        List.of("A durable net for catching small fish like herring and minnows."), null, true, CRAFTED_ITEM_MULTIPLIER), toVersionedJson(
                        new EquipableTypeMeta(null, null, null,
                                List.of(
                                        new Modifier(ModifierType.BASIC_FISHING, 1)
                                ),
                                List.of(
                                        new SkillRequirement(SkillType.FISHING, 1)
                                ), null)
                        , 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.FISHING_ROD.asInt(), Material.COAL, ItemRarity.COMMON, "Fishing Rod",
                        List.of("A reliable fishing rod suited for catching medium-sized fish such as trout and salmon."), null, true, CRAFTED_ITEM_MULTIPLIER), toVersionedJson(
                        new EquipableTypeMeta(null, null, null,
                                List.of(
                                        new Modifier(ModifierType.REGULAR_FISHING, 1)
                                ),
                                List.of(
                                        new SkillRequirement(SkillType.FISHING, 15)
                                ), null)
                        , 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.REINFORCED_FISHING_ROD.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Reinforced Fishing Rod",
                        List.of("A sturdy rod designed for challenging catches like sharks and other deepwater fish."), null, true, CRAFTED_ITEM_MULTIPLIER), toVersionedJson(
                        new EquipableTypeMeta(null, null, null,
                                List.of(
                                        new Modifier(ModifierType.ADVANCED_FISHING, 1)
                                ),
                                List.of(
                                        new SkillRequirement(SkillType.FISHING, 75)
                                ), null)
                        , 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.TOOL, ItemId.FISHING_TRAP.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Fishing Trap",
                        List.of("A baited trap used to catch slow-moving aquatic creatures like turtles and rays."), null, true, CRAFTED_ITEM_MULTIPLIER), toVersionedJson(
                        new EquipableTypeMeta(null, null, null,
                                List.of(
                                        new Modifier(ModifierType.TRAP_FISHING, 1)
                                ),
                                List.of(
                                        new SkillRequirement(SkillType.FISHING, 85)
                                ), null)
                        , 1)),


                // Armor - Helmets
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.COIF.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Coif", List.of("Simple leather helmet offering minimal head protection."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.HEAD, "coif", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 2),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 3),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 1),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 3),
                                                new Modifier(ModifierType.DEFENCE_RANGED, 6)
                                        ),
                                        null,
                                        null), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.COWL.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Cowl", List.of("A light hood offering minimal protection."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.HEAD, "cowl", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 2),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 3),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 1),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 5),
                                                new Modifier(ModifierType.DEFENCE_RANGED, 8)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 10)),
                                        null), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.BRONZE_MEDHELM.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Bronze Med Helm", List.of("Simple bronze helmet offering minimal head protection."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.HEAD, "bronze_med_helm", null,
                                        List.of(new Modifier(ModifierType.DEFENCE_STAB, 1), new Modifier(ModifierType.DEFENCE_SLASH, 1), new Modifier(ModifierType.DEFENCE_CRUSH, 1)),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.IRON_MEDHELM.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Iron Med Helm", List.of("Sturdy iron helmet to guard against blows."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.HEAD, "iron_med_helm", null,
                                        List.of(new Modifier(ModifierType.DEFENCE_STAB, 3), new Modifier(ModifierType.DEFENCE_SLASH, 4), new Modifier(ModifierType.DEFENCE_CRUSH, 2)),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.STEEL_MEDHELM.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Steel Med Helm", List.of("Well-crafted steel helmet for enhanced defense."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.HEAD, "steel_med_helm", null,
                                        List.of(new Modifier(ModifierType.DEFENCE_STAB, 5), new Modifier(ModifierType.DEFENCE_SLASH, 6), new Modifier(ModifierType.DEFENCE_CRUSH, 5)),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 5)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.MITHRIL_MEDHELM.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.UNCOMMON, "Mithril Med Helm", List.of("Lightweight mithril helmet, combining agility and defense."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.HEAD, "mithril_med_helm", null,
                                        List.of(new Modifier(ModifierType.DEFENCE_STAB, 8), new Modifier(ModifierType.DEFENCE_SLASH, 9), new Modifier(ModifierType.DEFENCE_CRUSH, 7)),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 20)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.ADAMANT_MEDHELM.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.UNCOMMON, "Adamantite Med Helm", List.of("Heavy adamantite helmet, favored by elite warriors."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.HEAD, "adamant_med_helm", null,
                                        List.of(new Modifier(ModifierType.DEFENCE_STAB, 12), new Modifier(ModifierType.DEFENCE_SLASH, 13), new Modifier(ModifierType.DEFENCE_CRUSH, 11)),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 30)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT_WITH_GEM_SOCKETS, ItemId.RUNE_MEDHELM.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.RARE, "Runite Med Helm", List.of("Runite helmet radiating magical protection."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new SocketedEquipableMeta(new EquipableTypeMeta(EquipmentSlot.HEAD, "rune_med_helm", null,
                                        List.of(new Modifier(ModifierType.DEFENCE_STAB, 18), new Modifier(ModifierType.DEFENCE_SLASH, 19), new Modifier(ModifierType.DEFENCE_CRUSH, 17)),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 40)),
                                        null), null, 1)
                                , 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.BRONZE_FULLHELM.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Bronze Full Helm", List.of("A full bronze helmet that offers basic protection."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.HEAD, "bronze_full_helm", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 2),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 3),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 4)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1)),
                                        null), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.IRON_FULLHELM.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Iron Full Helm", List.of("A sturdy iron full helm for better protection."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.HEAD, "iron_full_helm", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 5),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 6),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 7)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1)),
                                        null), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.STEEL_FULLHELM.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Steel Full Helm", List.of("A well-made steel full helm that offers decent defense."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.HEAD, "steel_full_helm", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 7),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 8),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 9)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 5)),
                                        null), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.MITHRIL_FULLHELM.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.UNCOMMON, "Mithril Full Helm", List.of("A lightweight mithril full helm with solid defense."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.HEAD, "mithril_full_helm", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 10),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 11),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 12)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 20)),
                                        null), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.ADAMANT_FULLHELM.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.UNCOMMON, "Adamant Full Helm", List.of("A heavy adamant full helm for superior defense."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.HEAD, "adamant_full_helm", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 14),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 15),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 16)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 30)),
                                        null), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT_WITH_GEM_SOCKETS, ItemId.RUNE_FULLHELM.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.RARE, "Rune Full Helm", List.of("A full helmet forged from runite, offering excellent defense."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new SocketedEquipableMeta(new EquipableTypeMeta(EquipmentSlot.HEAD, "rune_full_helm", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 20),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 22),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 21)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 40)),
                                        null), null, 1), 1)
                ),


                // Armor - Chestplates
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.LEATHER_BODY.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Leather Body", List.of("Basic leather torso armor."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.CHEST, "leather_chestplate", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 3),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 4),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 2),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 1),
                                                new Modifier(ModifierType.DEFENCE_RANGED, 8)
                                        ),
                                        null,
                                        null), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.HARD_LEATHER_BODY.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Hard Leather Body", List.of("Stiffened leather torso protection."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.CHEST, "hardleather_chestplate", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 7),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 7),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 7),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 8),
                                                new Modifier(ModifierType.DEFENCE_RANGED, 12)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 10)),
                                        null), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.STUDDED_LEATHER_BODY.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Studded Body", List.of("Leather armor reinforced with studs."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.CHEST, "studded_leather_body", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 8),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 8),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 7),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 7),
                                                new Modifier(ModifierType.DEFENCE_RANGED, 16)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 20)),
                                        null), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.GREEN_DHIDE_BODY.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Green D'hide Body", List.of("Torso armor made of green dragonhide."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.CHEST, "green_dhide_chestplate", null,
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
                                        ),
                                        null), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.BRONZE_BODY.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Bronze Body", List.of("Basic bronze chestplate, shields vital organs."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.CHEST, "bronze_chestplate", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 6),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 8),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 7)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.IRON_BODY.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Iron Body", List.of("Iron chestplate providing solid body armor."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.CHEST, "iron_chestplate", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 10),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 13),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 11)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.STEEL_BODY.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Steel Body", List.of("Tough steel chestplate for frontline combat."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.CHEST, "steel_chestplate", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 18),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 19),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 17)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 5)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.MITHRIL_BODY.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.UNCOMMON, "Mithril Body", List.of("Mithril chestplate offering balance of speed and defense."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.CHEST, "mithril_chestplate", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 24),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 25),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 23)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 20)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.ADAMANT_BODY.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.UNCOMMON, "Adamantite Body", List.of("Heavy adamantite chestplate for supreme protection."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.CHEST, "adamant_chestplate", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 30),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 31),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 29)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 30)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT_WITH_GEM_SOCKETS, ItemId.RUNE_BODY.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.RARE, "Runite Body", List.of("Runite chestplate enchanted for extra resilience."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new SocketedEquipableMeta(new EquipableTypeMeta(EquipmentSlot.CHEST, "runite_chestplate", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 40),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 42),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 38)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 40)),
                                        null), null, 1), 1)
                ),

                // Armor -- chainbody
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.BRONZE_CHAINBODY.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Bronze Chainbody", List.of("Basic bronze chainbody offering minimal protection."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.CHEST, "bronze_chainbody", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 4),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 6),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 5)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.IRON_CHAINBODY.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Iron Chainbody", List.of("Sturdy iron chainbody for reliable defense."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.CHEST, "iron_chainbody", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 8),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 11),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 9)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.STEEL_CHAINBODY.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Steel Chainbody", List.of("Tough steel chainbody, ideal for frontline combat."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.CHEST, "steel_chainbody", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 13),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 16),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 15)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 5)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.MITHRIL_CHAINBODY.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.UNCOMMON, "Mithril Chainbody", List.of("Lightweight mithril chainbody balancing agility and defense."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.CHEST, "mithril_chainbody", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 20),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 23),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 22)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 20)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.ADAMANT_CHAINBODY.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.UNCOMMON, "Adamantite Chainbody", List.of("Heavy adamantite chainbody offering superior protection."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.CHEST, "adamant_chainbody", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 28),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 27),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 24)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 30)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT_WITH_GEM_SOCKETS, ItemId.RUNE_CHAINBODY.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.RARE, "Runite Chainbody", List.of("Runite chainbody reinforced and enchanted for elite defense."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new SocketedEquipableMeta(new EquipableTypeMeta(EquipmentSlot.CHEST, "runite_chainbody", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 34),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 35),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 34)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 40)),
                                        null), null, 1), 1)
                ),


                // Armor - platelegs
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.LEATHER_LEGGINGS.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Leather Chaps", List.of("Basic leather leg armor."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.LEGS, "leather_leggings", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 2),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 3),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 2),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 2),
                                                new Modifier(ModifierType.DEFENCE_RANGED, 8)
                                        ),
                                        null,
                                        null), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.HARD_LEATHER_LEGGINGS.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Hard Leather Chaps", List.of("Stiffened leather leg protection."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.LEGS, "hardleather_leggings", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 7),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 7),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 6),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 5),
                                                new Modifier(ModifierType.DEFENCE_RANGED, 12)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 10)),
                                        null), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.STUDDED_LEATHER_LEGGINGS.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Studded Chaps", List.of("Leather chaps reinforced with studs."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.LEGS, "studded_leather_leggings", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 8),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 8),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 7),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 6),
                                                new Modifier(ModifierType.DEFENCE_RANGED, 14)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 20)),
                                        null), 1)
                ),
                new ItemEntity(
                        new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.GREEN_DHIDE_LEGGINGS.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Green D'hide Chaps", List.of("Leg armor made of green dragonhide."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.LEGS, "green_dhide_leggings", null, List.of(
                                        new Modifier(ModifierType.DEFENCE_STAB, 6),
                                        new Modifier(ModifierType.DEFENCE_SLASH, 8),
                                        new Modifier(ModifierType.DEFENCE_CRUSH, 7),
                                        new Modifier(ModifierType.DEFENCE_MAGIC, -6),
                                        new Modifier(ModifierType.RANGED_ACCURACY, 15),
                                        new Modifier(ModifierType.DEFENCE_RANGED, 20)
                                ), List.of(new SkillRequirement(SkillType.RANGED, 40)), null), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.BRONZE_PLATELEGS.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Bronze Platelegs", List.of("Bronze platelegs, basic leg armor."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.LEGS, "bronze_leggings", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 4),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 5),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 3)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.IRON_PLATELEGS.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Iron Platelegs", List.of("Iron platelegs for reliable leg protection."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.LEGS, "iron_leggings", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 7),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 8),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 6)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.STEEL_PLATELEGS.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Steel Platelegs", List.of("Steel platelegs favored by seasoned fighters."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.LEGS, "steel_leggings", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 12),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 13),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 11)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 5)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.MITHRIL_PLATELEGS.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.UNCOMMON, "Mithril Platelegs", List.of("Mithril platelegs combining speed and defense."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.LEGS, "mithril_leggings", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 16),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 17),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 15)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 20)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.ADAMANT_PLATELEGS.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.UNCOMMON, "Adamantite Platelegs", List.of("Adamantite platelegs, thick and durable."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.LEGS, "adamant_leggings", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 21),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 22),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 20)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 30)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT_WITH_GEM_SOCKETS, ItemId.RUNE_PLATELEGS.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.RARE, "Runite Platelegs", List.of("Runite platelegs shimmering with magical energy."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new SocketedEquipableMeta(new EquipableTypeMeta(EquipmentSlot.LEGS, "runite_leggings", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 28),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 30),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 26)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 40)),
                                        null), null, 1)
                                , 1)
                ),

                // Armor - Boots
                new ItemEntity(
                        new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.LEATHER_BOOTS.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Leather Boots", List.of("Basic leather footwear."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.FEET, "leather_boots", null, List.of(
                                        new Modifier(ModifierType.DEFENCE_STAB, 1),
                                        new Modifier(ModifierType.DEFENCE_SLASH, 2),
                                        new Modifier(ModifierType.DEFENCE_CRUSH, 2),
                                        new Modifier(ModifierType.DEFENCE_RANGED, 4)
                                ), null, null), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.BRONZE_BOOTS.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Bronze Boots", List.of("Bronze boots, offering basic foot protection."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.FEET, "bronze_boots", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 1),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 2),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 1)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.IRON_BOOTS.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Iron Boots", List.of("Iron boots, sturdy and reliable."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.FEET, "iron_boots", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 2),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 3),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 2)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.STEEL_BOOTS.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Steel Boots", List.of("Steel boots, made for tough terrain."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.FEET, "steel_boots", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 3),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 4),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 3)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 5)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.MITHRIL_BOOTS.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.UNCOMMON, "Mithril Boots", List.of("Mithril boots, light and swift."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.FEET, "mithril_boots", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 5),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 6),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 5)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 20)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT, ItemId.ADAMANT_BOOTS.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.UNCOMMON, "Adamantite Boots", List.of("Adamantite boots, heavy and protective."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableTypeMeta(EquipmentSlot.FEET, "adamant_boots", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 7),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 8),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 7)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 30)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.EQUIPMENT_WITH_GEM_SOCKETS, ItemId.RUNE_BOOTS.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.RARE, "Runite Boots", List.of("Runite boots imbued with arcane power."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new SocketedEquipableMeta(new EquipableTypeMeta(EquipmentSlot.FEET, "runite_boots", null,
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 10),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 11),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 9)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 40)),
                                        null), null, 1)
                                , 1)
                ),


                // Weapons - Swords
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.BRONZE_SWORD.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Bronze Sword", List.of("Bronze sword, a beginner’s trusty blade."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.NORMAL, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 4),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 3),
                                                new Modifier(ModifierType.CLEAVE_DISTANCE, 1)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 1)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.IRON_SWORD.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Iron Sword", List.of("Iron sword, reliable for close combat."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.NORMAL, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 6),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 5),
                                                new Modifier(ModifierType.CLEAVE_DISTANCE, 1)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 1)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.STEEL_SWORD.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Steel Sword", List.of("Steel sword, sharp and durable."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.NORMAL, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 10),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 9),
                                                new Modifier(ModifierType.CLEAVE_DISTANCE, 1)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 5)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.MITHRIL_SWORD.asInt(), Material.WOODEN_SWORD, ItemRarity.UNCOMMON, "Mithril Sword", List.of("Mithril sword, light and deadly."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.NORMAL, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 16),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 14),
                                                new Modifier(ModifierType.CLEAVE_DISTANCE, 1)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 20)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.ADAMANT_SWORD.asInt(), Material.WOODEN_SWORD, ItemRarity.UNCOMMON, "Adamantite Sword", List.of("Adamantite sword, powerful and heavy."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.NORMAL, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 21),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 19),
                                                new Modifier(ModifierType.CLEAVE_DISTANCE, 1)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 30)),
                                        null), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON_WITH_GEM_SOCKETS, ItemId.RUNE_SWORD.asInt(), Material.WOODEN_SWORD, ItemRarity.RARE, "Runite Sword", List.of("Runite sword crackling with magical energy."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new SocketedWeaponEquipableMeta(new EquipableWeaponTypeMeta(WeaponSpeed.NORMAL, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 30),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 29),
                                                new Modifier(ModifierType.CLEAVE_DISTANCE, 1)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 40)),
                                        null), null, 1)
                                , 1)
                ),

                // Weapons - Daggers
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.BRONZE_DAGGER.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Bronze Dagger", List.of("Bronze dagger, quick and light."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.FAST, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_STAB, 3),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 2),
                                                new Modifier(ModifierType.MAX_HIT_PENALTY, -15),
                                                new Modifier(ModifierType.CLEAVE_DISTANCE, -1.5)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 1)),
                                        null), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.IRON_DAGGER.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Iron Dagger", List.of("Iron dagger, sharp and practical."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.FAST, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_STAB, 5),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 3),
                                                new Modifier(ModifierType.MAX_HIT_PENALTY, -15),
                                                new Modifier(ModifierType.CLEAVE_DISTANCE, -1.5)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 1)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.STEEL_DAGGER.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Steel Dagger", List.of("Steel dagger, deadly in skilled hands."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.FAST, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_STAB, 8),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 6),
                                                new Modifier(ModifierType.MAX_HIT_PENALTY, -15),
                                                new Modifier(ModifierType.CLEAVE_DISTANCE, -1.5)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 5)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.MITHRIL_DAGGER.asInt(), Material.WOODEN_SWORD, ItemRarity.UNCOMMON, "Mithril Dagger", List.of("Mithril dagger, swift and precise."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.FAST, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_STAB, 14),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 10),
                                                new Modifier(ModifierType.MAX_HIT_PENALTY, -15),
                                                new Modifier(ModifierType.CLEAVE_DISTANCE, -1.5)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 20)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.ADAMANT_DAGGER.asInt(), Material.WOODEN_SWORD, ItemRarity.UNCOMMON, "Adamantite Dagger", List.of("Adamantite dagger, strong and lethal."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.FAST, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_STAB, 18),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 14),
                                                new Modifier(ModifierType.MAX_HIT_PENALTY, -15),
                                                new Modifier(ModifierType.CLEAVE_DISTANCE, -1.5)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 30)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON_WITH_GEM_SOCKETS, ItemId.RUNE_DAGGER.asInt(), Material.WOODEN_SWORD, ItemRarity.RARE, "Runite Dagger", List.of("Runite dagger glowing with mystical power."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new SocketedWeaponEquipableMeta(new EquipableWeaponTypeMeta(WeaponSpeed.FAST, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_STAB, 24),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 19),
                                                new Modifier(ModifierType.MAX_HIT_PENALTY, -15),
                                                new Modifier(ModifierType.CLEAVE_DISTANCE, -1.5)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 40)),
                                        null), null, 1), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.ARMADILLO_HOOKBLADE.asInt(), Material.WOODEN_SWORD, ItemRarity.EPIC, "Armadillo Hookblade", List.of("A wicked curved blade that trades raw force for blistering speed."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.VERY_FAST, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 10),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 8),
                                                new Modifier(ModifierType.MAX_HIT_PENALTY, -30),
                                                new Modifier(ModifierType.CLEAVE_DISTANCE, -2)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 50)),
                                        null)
                                , 1)
                ),

                // Weapons - Battleaxes
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.BRONZE_BATTLEAXE.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Bronze Battleaxe", List.of("Bronze battleaxe, heavy but slow."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 5),
                                                new Modifier(ModifierType.ATTACK_CRUSH, 6),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 6),
                                                new Modifier(ModifierType.CLEAVE_DISTANCE, 0.5)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 1)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.IRON_BATTLEAXE.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Iron Battleaxe", List.of("Iron battleaxe, powerful cleaving weapon."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 8),
                                                new Modifier(ModifierType.ATTACK_CRUSH, 9),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 9),
                                                new Modifier(ModifierType.CLEAVE_DISTANCE, 0.5)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 1)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.STEEL_BATTLEAXE.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Steel Battleaxe", List.of("Steel battleaxe, favored by warriors."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 12),
                                                new Modifier(ModifierType.ATTACK_CRUSH, 13),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 13),
                                                new Modifier(ModifierType.CLEAVE_DISTANCE, 0.5)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 5)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.MITHRIL_BATTLEAXE.asInt(), Material.WOODEN_SWORD, ItemRarity.UNCOMMON, "Mithril Battleaxe", List.of("Mithril battleaxe, fast and deadly."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 18),
                                                new Modifier(ModifierType.ATTACK_CRUSH, 19),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 20),
                                                new Modifier(ModifierType.CLEAVE_DISTANCE, 0.5)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 20)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON, ItemId.ADAMANT_BATTLEAXE.asInt(), Material.WOODEN_SWORD, ItemRarity.UNCOMMON, "Adamantite Battleaxe", List.of("Adamantite battleaxe, crushing blows guaranteed."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 23),
                                                new Modifier(ModifierType.ATTACK_CRUSH, 24),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 25),
                                                new Modifier(ModifierType.CLEAVE_DISTANCE, 0.5)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 30)),
                                        null)
                                , 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.MELEE_WEAPON_WITH_GEM_SOCKETS, ItemId.RUNE_BATTLEAXE.asInt(), Material.WOODEN_SWORD, ItemRarity.RARE, "Runite Battleaxe", List.of("Runite battleaxe, imbued with arcane fury."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new SocketedWeaponEquipableMeta(new EquipableWeaponTypeMeta(WeaponSpeed.SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 32),
                                                new Modifier(ModifierType.ATTACK_CRUSH, 33),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 34),
                                                new Modifier(ModifierType.CLEAVE_DISTANCE, 0.5)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 40)),
                                        null), null, 1),
                                1)
                ),


                // SHORTBOWS

                new ItemEntity(new AbstractItemMeta(ItemType.RANGED_WEAPON, ItemId.SHORT_BOW.asInt(), Material.BOW, ItemRarity.COMMON, "Shortbow", List.of("A basic shortbow made from oak."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.RANGED_STRENGTH, 5),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 9)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 1)),
                                        null),
                                1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.RANGED_WEAPON, ItemId.BIRCH_SHORT_BOW.asInt(), Material.BOW, ItemRarity.COMMON, "Birch Shortbow", List.of("Light and flexible, but not very strong."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.RANGED_STRENGTH, 7),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 12)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 5)),
                                        null),
                                1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.RANGED_WEAPON, ItemId.MAPLE_SHORT_BOW.asInt(), Material.BOW, ItemRarity.UNCOMMON, "Maple Shortbow", List.of("A well-balanced shortbow made from maple."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.RANGED_STRENGTH, 12),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 18)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 20)),
                                        null),
                                1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.RANGED_WEAPON, ItemId.YEW_SHORT_BOW.asInt(), Material.BOW, ItemRarity.RARE, "Yew Shortbow", List.of("A shortbow made of yew, powerful and efficient."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.RANGED_STRENGTH, 17),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 23)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 30)),
                                        null),
                                1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.RANGED_WEAPON, ItemId.MAGIC_SHORT_BOW.asInt(), Material.BOW, ItemRarity.RARE, "Magic Shortbow", List.of("A magically enhanced shortbow with high accuracy."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.RANGED_STRENGTH, 22),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 31)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 50)),
                                        null),
                                1)
                ),

                // LONGBOWS

                new ItemEntity(new AbstractItemMeta(ItemType.RANGED_WEAPON, ItemId.LONG_BOW.asInt(), Material.BOW, ItemRarity.COMMON, "Longbow", List.of("A standard longbow with increased range."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.VERY_SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.RANGED_STRENGTH, 6),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 11)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 1)),
                                        null),
                                1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.RANGED_WEAPON, ItemId.BIRCH_LONG_BOW.asInt(), Material.BOW, ItemRarity.COMMON, "Birch Longbow", List.of("A birch longbow, decent for beginners."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.VERY_SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.RANGED_STRENGTH, 9),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 15)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 5)),
                                        null),
                                1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.RANGED_WEAPON, ItemId.MAPLE_LONG_BOW.asInt(), Material.BOW, ItemRarity.UNCOMMON, "Maple Longbow", List.of("A longbow crafted from sturdy maple."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.VERY_SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.RANGED_STRENGTH, 14),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 21)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 20)),
                                        null),
                                1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.RANGED_WEAPON, ItemId.YEW_LONG_BOW.asInt(), Material.BOW, ItemRarity.RARE, "Yew Longbow", List.of("A longbow made of yew, strong and accurate."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.VERY_SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.RANGED_STRENGTH, 19),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 27)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 30)),
                                        null),
                                1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.RANGED_WEAPON, ItemId.MAGIC_LONG_BOW.asInt(), Material.BOW, ItemRarity.EPIC, "Magic Longbow", List.of("An enchanted longbow with great range and power."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.VERY_SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.RANGED_STRENGTH, 24),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 35)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 50)),
                                        null),
                                1)
                ),

                // Crossbows

                new ItemEntity(new AbstractItemMeta(ItemType.RANGED_WEAPON, ItemId.IRON_CROSSBOW.asInt(), Material.CROSSBOW, ItemRarity.UNCOMMON, "Iron Crossbow", List.of("A sturdy iron crossbow."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.VERY_SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.RANGED_STRENGTH, 15),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 21)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 20)),
                                        null),
                                1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.RANGED_WEAPON, ItemId.STEEL_CROSSBOW.asInt(), Material.CROSSBOW, ItemRarity.UNCOMMON, "Steel Crossbow", List.of("A reliable steel crossbow."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.VERY_SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.RANGED_STRENGTH, 20),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 25)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 30)),
                                        null),
                                1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.RANGED_WEAPON, ItemId.MITHRIL_CROSSBOW.asInt(), Material.CROSSBOW, ItemRarity.RARE, "Mithril Crossbow", List.of("A lightweight mithril crossbow with enhanced power."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.VERY_SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.RANGED_STRENGTH, 28),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 33)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 40)),
                                        null),
                                1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.RANGED_WEAPON, ItemId.ADAMANT_CROSSBOW.asInt(), Material.CROSSBOW, ItemRarity.RARE, "Adamant Crossbow", List.of("A powerful adamant crossbow with great accuracy."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new EquipableWeaponTypeMeta(WeaponSpeed.VERY_SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.RANGED_STRENGTH, 35),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 41)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 50)),
                                        null),
                                1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.RANGED_WEAPON_WITH_SOCKETS, ItemId.RUNE_CROSSBOW.asInt(), Material.CROSSBOW, ItemRarity.EPIC, "Runite Crossbow", List.of("The ultimate runite crossbow with unmatched precision."), null, true, CRAFTED_ITEM_MULTIPLIER),
                        toVersionedJson(
                                new SocketedWeaponEquipableMeta(new EquipableWeaponTypeMeta(WeaponSpeed.VERY_SLOW, null,
                                        List.of(
                                                new Modifier(ModifierType.RANGED_STRENGTH, 42),
                                                new Modifier(ModifierType.RANGED_ACCURACY, 48)
                                        ),
                                        List.of(new SkillRequirement(SkillType.RANGED, 60)),
                                        null), null, 1),
                                1)
                )


        );
    }

    public void setCache(List<AbstractItemMeta> items) {
        this.cached = items;
    }
}

