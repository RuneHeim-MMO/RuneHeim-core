package org.zuma.examplePlugin.domain.item.persistence;

import com.google.gson.Gson;
import org.bukkit.Color;
import org.bukkit.Material;
import org.zuma.examplePlugin.core.meta.JsonMeta;
import org.zuma.examplePlugin.domain.heads.HeadTexture;
import org.zuma.examplePlugin.domain.item.constants.CustomModelId;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.item.constants.ItemRarity;
import org.zuma.examplePlugin.domain.item.constants.ItemType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.meta.v1.EquipableMeta;
import org.zuma.examplePlugin.domain.item.meta.v1.FoodMeta;
import org.zuma.examplePlugin.domain.item.meta.v1.PotionMeta;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.ArrayList;
import java.util.List;

public class ItemRepository {
    private List<AbstractItemMeta> cached = new ArrayList<>();

    /* public void saveItems(List<AbstractItemMeta> items, HikariDataSource dataSource) {
        String sql = """
                    INSERT INTO items (
                        id, type, material, rarity, display_name, lore, head_texture, leather_color,
                        json_meta, json_meta_version
                    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Gson gson = new Gson();

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
        Gson gson = new Gson();

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
            RuneQuestPlugin.getRuneLogger().log(LogLevel.CRITICAL, "Could not load the items! " + e.getMessage());
        }

        return items;
    }
    */
    public List<ItemEntity> getAll() {
        JsonMeta empty = new JsonMeta("", 1);

        return List.of(
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.SMITHING_HAMMER.asInt(), Material.WOODEN_AXE, ItemRarity.COMMON, "Hammer", List.of("A sturdy hammer used for refining and forging equipment."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_CRUSH, 4),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 2)
                                        ), null)
                        ), 1)),


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

                // Misc
                new ItemEntity(new AbstractItemMeta(ItemType.MISC, ItemId.BURNT_FOOD.asInt(), Material.COAL, ItemRarity.NOT_APPLICABLE, "Charred Remains", List.of("All that's left after fire claims its meal."), null, null), null),
                new ItemEntity(new AbstractItemMeta(ItemType.MISC, ItemId.EMPTY_POTION_BOTTLE.asInt(), Material.GLASS_BOTTLE, ItemRarity.NOT_APPLICABLE, "Empty Potion Bottle",
                        List.of("It smells... unpleasant."), null, null), null),

                // Food
                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_HERRING.asInt(), Material.COOKED_SALMON, ItemRarity.COMMON, "Cooked Herring",
                        List.of("Lightly roasted river fish, a quick meal."), null, null), new JsonMeta(
                        new Gson().toJson(new FoodMeta(3)), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_MINNOW.asInt(), Material.COOKED_SALMON, ItemRarity.COMMON, "Cooked Minnow",
                        List.of("Tiny fish, crisped over a fire."), null, null), new JsonMeta(
                        new Gson().toJson(new FoodMeta(1)), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_TROUT.asInt(), Material.COOKED_SALMON, ItemRarity.COMMON, "Cooked Trout",
                        List.of("Flaky and rich, a river-dweller's feast."), null, null), new JsonMeta(
                        new Gson().toJson(new FoodMeta(7)), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_SALMON.asInt(), Material.COOKED_SALMON, ItemRarity.COMMON, "Cooked Salmon",
                        List.of("Bold flavor, pink flesh, campfire ready."), null, null), new JsonMeta(
                        new Gson().toJson(new FoodMeta(9)), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_TUNA.asInt(), Material.COOKED_SALMON, ItemRarity.COMMON, "Cooked Tuna",
                        List.of("Dense meat, satisfying and nutritious."), null, null), new JsonMeta(
                        new Gson().toJson(new FoodMeta(10)), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_CRAWLER.asInt(), Material.COOKED_SALMON, ItemRarity.COMMON, "Cooked Crawler",
                        List.of("Charred crustacean, crunchy and filling."), null, null), new JsonMeta(
                        new Gson().toJson(new FoodMeta(12)), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_PIKE.asInt(), Material.COOKED_SALMON, ItemRarity.COMMON, "Cooked Pike",
                        List.of("A sharp-finned delicacy from cold waters."), null, null), new JsonMeta(
                        new Gson().toJson(new FoodMeta(14)), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_EEL.asInt(), Material.COOKED_SALMON, ItemRarity.UNCOMMON, "Cooked Eel",
                        List.of("Grilled serpent of the deep marshes."), null, null), new JsonMeta(
                        new Gson().toJson(new FoodMeta(16)), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_SHARK.asInt(), Material.COOKED_SALMON, ItemRarity.UNCOMMON, "Cooked Shark",
                        List.of("Meaty apex predator, expertly seared."), null, null), new JsonMeta(
                        new Gson().toJson(new FoodMeta(20)), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_TURTLE.asInt(), Material.COOKED_SALMON, ItemRarity.RARE, "Cooked Turtle",
                        List.of("Tender shell beast meat, slow-cooked to perfection."), null, null), new JsonMeta(
                        new Gson().toJson(new FoodMeta(21)), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.FOOD, ItemId.COOKED_RAY.asInt(), Material.COOKED_SALMON, ItemRarity.RARE, "Cooked Ray",
                        List.of("Wings of the deep skyfish, rich in nutrients."), null, null), new JsonMeta(
                        new Gson().toJson(new FoodMeta(22)), 1)),

                // Potions
                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.DEFENCE_POTION_I.asInt(), Material.MILK_BUCKET, ItemRarity.RARE, "Defence Potion I",
                        List.of("Increases your defense temporarily.", "Brewed by skilled alchemists."), null, null), new JsonMeta(
                        new Gson().toJson(new PotionMeta(3, 3, "Potion effects...", CustomModelId.GREEN_POTION)), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.ATTACK_POTION_I.asInt(), Material.MILK_BUCKET, ItemRarity.RARE, "Attack Potion I",
                        List.of("Boosts your attack power.", "Unleash your fury in battle."), null, null), new JsonMeta(
                        new Gson().toJson(new PotionMeta(3, 3, "Potion effects...", CustomModelId.BLUE_POTION)), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.MAGIC_POTION_I.asInt(), Material.MILK_BUCKET, ItemRarity.RARE, "Magic Potion I",
                        List.of("Enhances your magical abilities.", "A favorite among mages."), null, null), new JsonMeta(
                        new Gson().toJson(new PotionMeta(3, 3, "Potion effects...", CustomModelId.CYAN_POTION)), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.HEALTH_POTION_I.asInt(), Material.MILK_BUCKET, ItemRarity.RARE, "Health Potion I",
                        List.of("Restores a small amount of health.", "Always good to have a few."), null, null), new JsonMeta(
                        new Gson().toJson(new PotionMeta(3, 3, "Potion effects...", CustomModelId.RED_POTION)), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.POTION, ItemId.STRENGTH_POTION_I.asInt(), Material.MILK_BUCKET, ItemRarity.RARE, "Strength Potion I",
                        List.of("Temporarily increases strength.", "Tear through enemies with ease."), null, null), new JsonMeta(
                        new Gson().toJson(new PotionMeta(3, 3, "Potion effects...", CustomModelId.YELLOW_POTION)), 1)),


                // Tools
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.BRONZE_PICKAXE.asInt(), Material.WOODEN_PICKAXE, ItemRarity.COMMON, "Bronze Pickaxe", List.of("Bronze pickaxe, basic mining tool."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.MINING_BOOST, 1)),
                                        List.of(new SkillRequirement(SkillType.MINING, 1))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.IRON_PICKAXE.asInt(), Material.WOODEN_PICKAXE, ItemRarity.COMMON, "Iron Pickaxe", List.of("Iron pickaxe, mines ores with ease."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.MINING_BOOST, 2)),
                                        List.of(new SkillRequirement(SkillType.MINING, 1))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.STEEL_PICKAXE.asInt(), Material.WOODEN_PICKAXE, ItemRarity.COMMON, "Steel Pickaxe", List.of("Steel pickaxe, efficient and strong."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.MINING_BOOST, 3)),
                                        List.of(new SkillRequirement(SkillType.MINING, 6))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.MITHRIL_PICKAXE.asInt(), Material.WOODEN_PICKAXE, ItemRarity.UNCOMMON, "Mithril Pickaxe", List.of("Mithril pickaxe, fast and durable."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.MINING_BOOST, 4)),
                                        List.of(new SkillRequirement(SkillType.MINING, 21))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.ADAMANTITE_PICKAXE.asInt(), Material.WOODEN_PICKAXE, ItemRarity.UNCOMMON, "Adamantite Pickaxe", List.of("Adamantite pickaxe, mines the hardest ores."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.MINING_BOOST, 5)),
                                        List.of(new SkillRequirement(SkillType.MINING, 31))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.RUNITE_PICKAXE.asInt(), Material.WOODEN_PICKAXE, ItemRarity.RARE, "Runite Pickaxe", List.of("Runite pickaxe, enchanted for maximum yield."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.MINING_BOOST, 6)),
                                        List.of(new SkillRequirement(SkillType.MINING, 41))
                                )
                        ), 1)
                ),

                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.BRONZE_AXE.asInt(), Material.WOODEN_AXE, ItemRarity.COMMON, "Bronze axe", List.of("Bronze axe, useful for combat and chopping."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.WOODCUTTING_BOOST, 1)),
                                        List.of(new SkillRequirement(SkillType.WOODCUTTING, 1))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.IRON_AXE.asInt(), Material.WOODEN_AXE, ItemRarity.COMMON, "Iron axe", List.of("Iron axe, sturdy and reliable."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.WOODCUTTING_BOOST, 2)),
                                        List.of(new SkillRequirement(SkillType.WOODCUTTING, 1))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.STEEL_AXE.asInt(), Material.WOODEN_AXE, ItemRarity.COMMON, "Steel axe", List.of("Steel axe, stronger and more durable."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.WOODCUTTING_BOOST, 3)),
                                        List.of(new SkillRequirement(SkillType.WOODCUTTING, 6))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.MITHRIL_AXE.asInt(), Material.WOODEN_AXE, ItemRarity.UNCOMMON, "Mithril axe", List.of("Mithril axe, light and efficient."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.WOODCUTTING_BOOST, 4)),
                                        List.of(new SkillRequirement(SkillType.WOODCUTTING, 21))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.ADAMANTITE_AXE.asInt(), Material.WOODEN_AXE, ItemRarity.UNCOMMON, "Adamantite axe", List.of("Adamantite axe, built to last."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.WOODCUTTING_BOOST, 5)),
                                        List.of(new SkillRequirement(SkillType.WOODCUTTING, 31))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.RUNITE_AXE.asInt(), Material.WOODEN_AXE, ItemRarity.RARE, "Runite axe", List.of("Runite axe glowing with magical energy."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.WOODCUTTING_BOOST, 6)),
                                        List.of(new SkillRequirement(SkillType.WOODCUTTING, 41))
                                )
                        ), 1)
                ),

                // Fishing
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.FISHING_NET.asInt(), Material.COAL, ItemRarity.COMMON, "Fishing Net",
                        List.of("A durable net for catching small fish like herring and minnows."), null, null), new JsonMeta(new Gson().toJson(
                        new EquipableMeta(
                                List.of(
                                        new Modifier(ModifierType.BASIC_FISHING, 1)
                                ),
                                List.of(
                                        new SkillRequirement(SkillType.FISHING, 1)
                                ))
                ), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.FISHING_ROD.asInt(), Material.COAL, ItemRarity.COMMON, "Fishing Rod",
                        List.of("A reliable fishing rod suited for catching medium-sized fish such as trout and salmon."), null, null), new JsonMeta(new Gson().toJson(
                        new EquipableMeta(
                                List.of(
                                        new Modifier(ModifierType.REGULAR_FISHING, 1)
                                ),
                                List.of(
                                        new SkillRequirement(SkillType.FISHING, 15)
                                ))
                ), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.REINFORCED_FISHING_ROD.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Reinforced Fishing Rod",
                        List.of("A sturdy rod designed for challenging catches like sharks and other deepwater fish."), null, null), new JsonMeta(new Gson().toJson(
                        new EquipableMeta(
                                List.of(
                                        new Modifier(ModifierType.ADVANCED_FISHING, 1)
                                ),
                                List.of(
                                        new SkillRequirement(SkillType.FISHING, 75)
                                ))
                ), 1)),

                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.FISHING_TRAP.asInt(), Material.COAL, ItemRarity.UNCOMMON, "Fishing Trap",
                        List.of("A baited trap used to catch slow-moving aquatic creatures like turtles and rays."), null, null), new JsonMeta(new Gson().toJson(
                        new EquipableMeta(
                                List.of(
                                        new Modifier(ModifierType.TRAP_FISHING, 1)
                                ),
                                List.of(
                                        new SkillRequirement(SkillType.FISHING, 85)
                                ))
                ), 1)),


                // Armor - Helmets
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.BRONZE_HELMET.asInt(), Material.PLAYER_HEAD, ItemRarity.COMMON, "Bronze Helmet", List.of("Simple bronze helmet offering minimal head protection."), HeadTexture.BRONZE_HELM.asBase64(), Color.fromRGB(210, 170, 120)),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.DEFENCE_STAB, 1), new Modifier(ModifierType.DEFENCE_SLASH, 1), new Modifier(ModifierType.DEFENCE_CRUSH, 1)),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.IRON_HELMET.asInt(), Material.PLAYER_HEAD, ItemRarity.COMMON, "Iron Helmet", List.of("Sturdy iron helmet to guard against blows."), HeadTexture.IRON_HELM.asBase64(), null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.DEFENCE_STAB, 3), new Modifier(ModifierType.DEFENCE_SLASH, 4), new Modifier(ModifierType.DEFENCE_CRUSH, 2)),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.STEEL_HELMET.asInt(), Material.PLAYER_HEAD, ItemRarity.COMMON, "Steel Helmet", List.of("Well-crafted steel helmet for enhanced defense."), HeadTexture.STEEL_HELM.asBase64(), null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.DEFENCE_STAB, 5), new Modifier(ModifierType.DEFENCE_SLASH, 6), new Modifier(ModifierType.DEFENCE_CRUSH, 5)),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 5))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.MITHRIL_HELMET.asInt(), Material.PLAYER_HEAD, ItemRarity.UNCOMMON, "Mithril Helmet", List.of("Lightweight mithril helmet, combining agility and defense."), HeadTexture.MITHRIL_HELM.asBase64(), null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.DEFENCE_STAB, 8), new Modifier(ModifierType.DEFENCE_SLASH, 9), new Modifier(ModifierType.DEFENCE_CRUSH, 7)),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 20))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.ADAMANTITE_HELMET.asInt(), Material.PLAYER_HEAD, ItemRarity.UNCOMMON, "Adamantite Helmet", List.of("Heavy adamantite helmet, favored by elite warriors."), HeadTexture.ADAMANT_HELM.asBase64(), null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.DEFENCE_STAB, 12), new Modifier(ModifierType.DEFENCE_SLASH, 13), new Modifier(ModifierType.DEFENCE_CRUSH, 11)),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 30))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.RUNITE_HELMET.asInt(), Material.PLAYER_HEAD, ItemRarity.RARE, "Runite Helmet", List.of("Runite helmet radiating magical protection."), HeadTexture.RUNE_HELM.asBase64(), null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(new Modifier(ModifierType.DEFENCE_STAB, 18), new Modifier(ModifierType.DEFENCE_SLASH, 19), new Modifier(ModifierType.DEFENCE_CRUSH, 17)),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 40))
                                )
                        ), 1)
                ),

                // Armor - Chestplates
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.BRONZE_BODY.asInt(), Material.LEATHER_CHESTPLATE, ItemRarity.COMMON, "Bronze Body", List.of("Basic bronze chestplate, shields vital organs."), null, Color.fromRGB(210, 170, 120)),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 6),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 8),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 7)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.IRON_BODY.asInt(), Material.CHAINMAIL_CHESTPLATE, ItemRarity.COMMON, "Iron Body", List.of("Iron chestplate providing solid body armor."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 10),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 13),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 11)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.STEEL_BODY.asInt(), Material.IRON_CHESTPLATE, ItemRarity.COMMON, "Steel Body", List.of("Tough steel chestplate for frontline combat."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 18),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 19),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 17)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 5))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.MITHRIL_BODY.asInt(), Material.GOLDEN_CHESTPLATE, ItemRarity.UNCOMMON, "Mithril Body", List.of("Mithril chestplate offering balance of speed and defense."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 24),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 25),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 23)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 20))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.ADAMANTITE_BODY.asInt(), Material.DIAMOND_CHESTPLATE, ItemRarity.UNCOMMON, "Adamantite Body", List.of("Heavy adamantite chestplate for supreme protection."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 30),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 31),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 29)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 30))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.RUNITE_BODY.asInt(), Material.NETHERITE_CHESTPLATE, ItemRarity.RARE, "Runite Body", List.of("Runite chestplate enchanted for extra resilience."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 40),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 42),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 38)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 40))
                                )
                        ), 1)
                ),

                // Armor - platelegs
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.BRONZE_PLATELEGS.asInt(), Material.LEATHER_LEGGINGS, ItemRarity.COMMON, "Bronze Platelegs", List.of("Bronze platelegs, basic leg armor."), null, Color.fromRGB(210, 170, 120)),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 4),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 5),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 3)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.IRON_PLATELEGS.asInt(), Material.CHAINMAIL_LEGGINGS, ItemRarity.COMMON, "Iron Platelegs", List.of("Iron platelegs for reliable leg protection."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 7),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 8),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 6)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.STEEL_PLATELEGS.asInt(), Material.IRON_LEGGINGS, ItemRarity.COMMON, "Steel Platelegs", List.of("Steel platelegs favored by seasoned fighters."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 12),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 13),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 11)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 5))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.MITHRIL_PLATELEGS.asInt(), Material.GOLDEN_LEGGINGS, ItemRarity.UNCOMMON, "Mithril Platelegs", List.of("Mithril platelegs combining speed and defense."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 16),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 17),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 15)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 20))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.ADAMANTITE_PLATELEGS.asInt(), Material.DIAMOND_LEGGINGS, ItemRarity.UNCOMMON, "Adamantite Platelegs", List.of("Adamantite platelegs, thick and durable."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 21),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 22),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 20)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 30))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.RUNITE_PLATELEGS.asInt(), Material.NETHERITE_LEGGINGS, ItemRarity.RARE, "Runite Platelegs", List.of("Runite platelegs shimmering with magical energy."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 28),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 30),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 26)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 40))
                                )
                        ), 1)
                ),

                // Armor - Boots
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.BRONZE_BOOTS.asInt(), Material.LEATHER_BOOTS, ItemRarity.COMMON, "Bronze Boots", List.of("Bronze boots, offering basic foot protection."), null, Color.fromRGB(210, 170, 120)),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 1),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 2),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 1)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.IRON_BOOTS.asInt(), Material.CHAINMAIL_BOOTS, ItemRarity.COMMON, "Iron Boots", List.of("Iron boots, sturdy and reliable."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 2),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 3),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 2)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 1))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.STEEL_BOOTS.asInt(), Material.IRON_BOOTS, ItemRarity.COMMON, "Steel Boots", List.of("Steel boots, made for tough terrain."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 3),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 4),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 3)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 5))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.MITHRIL_BOOTS.asInt(), Material.GOLDEN_BOOTS, ItemRarity.UNCOMMON, "Mithril Boots", List.of("Mithril boots, light and swift."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 5),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 6),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 5)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 20))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.ADAMANTITE_BOOTS.asInt(), Material.DIAMOND_BOOTS, ItemRarity.UNCOMMON, "Adamantite Boots", List.of("Adamantite boots, heavy and protective."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 7),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 8),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 7)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 30))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.RUNITE_BOOTS.asInt(), Material.NETHERITE_BOOTS, ItemRarity.RARE, "Runite Boots", List.of("Runite boots imbued with arcane power."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.DEFENCE_STAB, 10),
                                                new Modifier(ModifierType.DEFENCE_SLASH, 11),
                                                new Modifier(ModifierType.DEFENCE_CRUSH, 9)
                                        ),
                                        List.of(new SkillRequirement(SkillType.DEFENCE, 40))
                                )
                        ), 1)
                ),


                // Weapons - Swords
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.BRONZE_SWORD.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Bronze Sword", List.of("Bronze sword, a beginner’s trusty blade."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 4),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 3)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 1))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.IRON_SWORD.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Iron Sword", List.of("Iron sword, reliable for close combat."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 6),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 5)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 1))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.STEEL_SWORD.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Steel Sword", List.of("Steel sword, sharp and durable."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 10),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 9)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 5))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.MITHRIL_SWORD.asInt(), Material.WOODEN_SWORD, ItemRarity.UNCOMMON, "Mithril Sword", List.of("Mithril sword, light and deadly."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 16),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 14)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 20))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.ADAMANTITE_SWORD.asInt(), Material.WOODEN_SWORD, ItemRarity.UNCOMMON, "Adamantite Sword", List.of("Adamantite sword, powerful and heavy."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 21),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 19)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 30))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.RUNITE_SWORD.asInt(), Material.WOODEN_SWORD, ItemRarity.RARE, "Runite Sword", List.of("Runite sword crackling with magical energy."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 30),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 29)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 40))
                                )
                        ), 1)
                ),

                // Weapons - Daggers
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.BRONZE_DAGGER.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Bronze Dagger", List.of("Bronze dagger, quick and light."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_STAB, 3),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 2)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 1))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.IRON_DAGGER.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Iron Dagger", List.of("Iron dagger, sharp and practical."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_STAB, 5),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 3)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 1))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.STEEL_DAGGER.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Steel Dagger", List.of("Steel dagger, deadly in skilled hands."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_STAB, 8),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 6)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 5))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.MITHRIL_DAGGER.asInt(), Material.WOODEN_SWORD, ItemRarity.UNCOMMON, "Mithril Dagger", List.of("Mithril dagger, swift and precise."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_STAB, 14),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 10)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 20))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.ADAMANTITE_DAGGER.asInt(), Material.WOODEN_SWORD, ItemRarity.UNCOMMON, "Adamantite Dagger", List.of("Adamantite dagger, strong and lethal."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_STAB, 18),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 14)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 30))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.RUNITE_DAGGER.asInt(), Material.WOODEN_SWORD, ItemRarity.RARE, "Runite Dagger", List.of("Runite dagger glowing with mystical power."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_STAB, 24),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 19)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 40))
                                )
                        ), 1)
                ),

                // Weapons - Battleaxes
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.BRONZE_BATTLEAXE.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Bronze Battleaxe", List.of("Bronze battleaxe, heavy but slow."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 5),
                                                new Modifier(ModifierType.ATTACK_CRUSH, 6),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 6)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 1))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.IRON_BATTLEAXE.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Iron Battleaxe", List.of("Iron battleaxe, powerful cleaving weapon."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 8),
                                                new Modifier(ModifierType.ATTACK_CRUSH, 9),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 9)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 1))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.STEEL_BATTLEAXE.asInt(), Material.WOODEN_SWORD, ItemRarity.COMMON, "Steel Battleaxe", List.of("Steel battleaxe, favored by warriors."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 12),
                                                new Modifier(ModifierType.ATTACK_CRUSH, 13),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 13)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 5))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.MITHRIL_BATTLEAXE.asInt(), Material.WOODEN_SWORD, ItemRarity.UNCOMMON, "Mithril Battleaxe", List.of("Mithril battleaxe, fast and deadly."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 18),
                                                new Modifier(ModifierType.ATTACK_CRUSH, 19),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 20)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 20))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.ADAMANTITE_BATTLEAXE.asInt(), Material.WOODEN_SWORD, ItemRarity.UNCOMMON, "Adamantite Battleaxe", List.of("Adamantite battleaxe, crushing blows guaranteed."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 23),
                                                new Modifier(ModifierType.ATTACK_CRUSH, 24),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 25)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 30))
                                )
                        ), 1)
                ),
                new ItemEntity(new AbstractItemMeta(ItemType.GEAR, ItemId.RUNITE_BATTLEAXE.asInt(), Material.WOODEN_SWORD, ItemRarity.RARE, "Runite Battleaxe", List.of("Runite battleaxe, imbued with arcane fury."), null, null),
                        new JsonMeta(new Gson().toJson(
                                new EquipableMeta(
                                        List.of(
                                                new Modifier(ModifierType.ATTACK_SLASH, 32),
                                                new Modifier(ModifierType.ATTACK_CRUSH, 33),
                                                new Modifier(ModifierType.STRENGTH_BONUS, 34)
                                        ),
                                        List.of(new SkillRequirement(SkillType.ATTACK, 40))
                                )
                        ), 1)
                )


        );
    }

    public void setCache(List<AbstractItemMeta> items) {
        this.cached = items;
    }
}
