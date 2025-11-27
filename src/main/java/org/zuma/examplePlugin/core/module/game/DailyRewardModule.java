package org.zuma.examplePlugin.core.module.game;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.domain.daily.DailyRewardHandler;
import org.zuma.examplePlugin.domain.daily.persistence.DailyRewardRepository;
import org.zuma.examplePlugin.domain.droptable.DropTable;
import org.zuma.examplePlugin.domain.droptable.DropTableEntry;
import org.zuma.examplePlugin.domain.droptable.DropTableHandler;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.quest.QuestHandler;
import org.zuma.examplePlugin.domain.quest.QuestRegistry;
import org.zuma.examplePlugin.domain.quest.persistence.QuestProgressRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DailyRewardModule implements PluginModule {
    private final static DropTable firstDay = new DropTable(-1, List.of(
            new DropTableEntry(ItemId.COINS.asInt(), 1, 500, 1, DropTableEntry.Type.ITEM)
    ), null);

    private final static DropTable dayFiller = new DropTable(-1, List.of(
            new DropTableEntry(ItemId.IRON_BAR.asInt(), 4, 8, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.STEEL_BAR.asInt(), 4, 8, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.MITHRIL_BAR.asInt(), 3, 8, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.RUNE_BAR.asInt(), 2, 8, 50, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.COOKED_TUNA.asInt(), 1, 32, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.ATTACK_POTION_I.asInt(), 1, 3, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.STRENGTH_POTION_I.asInt(), 1, 3, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.DEFENCE_POTION_I.asInt(), 1, 3, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.RANGED_POTION_I.asInt(), 1, 3, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.YEW_LOG.asInt(), 1, 32, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.MAPLE_LOG.asInt(), 1, 32, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.MAHOGANY_LOG.asInt(), 1, 32, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.MAGIC_LOG.asInt(), 1, 16, 30, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.REGULAR_FAIRY_RATION.asInt(), 1, 16, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.MAPLE_SHORT_BOW.asInt(), 1, 1, 10, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.STEEL_CROSSBOW.asInt(), 1, 1, 10, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.MITHRIL_CROSSBOW.asInt(), 1, 1, 40, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.BIG_BONES.asInt(), 1, 32, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.LARGE_BONES.asInt(), 1, 32, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.GENIE_XP_LAMP.asInt(), 1, 4, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.SAPPHIRE.asInt(), 1, 4, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.RUBY.asInt(), 1, 4, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.EMERALD.asInt(), 1, 4, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.DIAMOND.asInt(), 1, 4, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.SCRATCH_CARD.asInt(), 1, 3, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.MITHRIL_RING.asInt(), 1, 2, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.GRAY_MULE_MOUNT.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.AMULET_OF_THE_PROTECTOR.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.AMULET_OF_MIGHT.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.AMULET_OF_PRECISION.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.FUSION_SCROLL.asInt(), 1, 1, 80, DropTableEntry.Type.ITEM),

            new DropTableEntry(ItemId.STEEL_MEDHELM.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.STEEL_FULLHELM.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.STEEL_CHAINBODY.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.STEEL_BODY.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.STEEL_BOOTS.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.STEEL_DAGGER.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.STEEL_BATTLEAXE.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.STEEL_SWORD.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),

            new DropTableEntry(ItemId.COWL.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.STUDDED_LEATHER_BODY.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.STUDDED_LEATHER_LEGGINGS.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.HARD_LEATHER_LEGGINGS.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.HARD_LEATHER_BODY.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),

            new DropTableEntry(ItemId.COINS.asInt(), 1, 500, 5, DropTableEntry.Type.ITEM)
    ), null);

    private final static DropTable grandPrize = new DropTable(-1, List.of(
            new DropTableEntry(ItemId.STEEL_BAR.asInt(), 32, 64, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.MITHRIL_BAR.asInt(), 16, 32, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.RUNE_BAR.asInt(), 8, 16, 50, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.COOKED_SHARK.asInt(), 32, 64, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.COOKED_EEL.asInt(), 32, 64, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.ATTACK_POTION_II.asInt(), 1, 5, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.STRENGTH_POTION_II.asInt(), 1, 5, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.DEFENCE_POTION_II.asInt(), 1, 5, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.RANGED_POTION_II.asInt(), 1, 5, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.YEW_LOG.asInt(), 32, 64, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.MAPLE_LOG.asInt(), 32, 64, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.MAHOGANY_LOG.asInt(), 32, 64, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.MAGIC_LOG.asInt(), 16, 32, 30, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.REGULAR_FAIRY_RATION.asInt(), 16, 32, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.FINE_FAIRY_RATION.asInt(), 8, 16, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.MAGIC_SHORT_BOW.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.MITHRIL_CROSSBOW.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.ADAMANT_CROSSBOW.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.BIG_BONES.asInt(), 32, 64, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.LARGE_BONES.asInt(), 16, 64, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.GENIE_XP_LAMP.asInt(), 3, 6, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.SCRATCH_CARD.asInt(), 2, 6, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.ADAMANT_RING.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.RUNE_RING.asInt(), 1, 1, 40, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.GRAY_MULE_MOUNT.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),

            new DropTableEntry(ItemId.MITHRIL_MEDHELM.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.MITHRIL_FULLHELM.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.MITHRIL_CHAINBODY.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.MITHRIL_BODY.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.MITHRIL_BOOTS.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.MITHRIL_DAGGER.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.MITHRIL_BATTLEAXE.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.MITHRIL_SWORD.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),

            new DropTableEntry(ItemId.ADAMANT_MEDHELM.asInt(), 1, 1, 40, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.ADAMANT_FULLHELM.asInt(), 1, 1, 40, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.ADAMANT_CHAINBODY.asInt(), 1, 1, 40, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.ADAMANT_BODY.asInt(), 1, 1, 40, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.ADAMANT_BOOTS.asInt(), 1, 1, 40, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.ADAMANT_DAGGER.asInt(), 1, 1, 40, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.ADAMANT_BATTLEAXE.asInt(), 1, 1, 40, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.ADAMANT_SWORD.asInt(), 1, 1, 40, DropTableEntry.Type.ITEM),

            new DropTableEntry(ItemId.RUNE_MEDHELM.asInt(), 1, 1, 60, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.RUNE_FULLHELM.asInt(), 1, 1, 60, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.RUNE_CHAINBODY.asInt(), 1, 1, 60, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.RUNE_BODY.asInt(), 1, 1, 60, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.RUNE_BOOTS.asInt(), 1, 1, 60, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.RUNE_DAGGER.asInt(), 1, 1, 60, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.RUNE_BATTLEAXE.asInt(), 1, 1, 60, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.RUNE_SWORD.asInt(), 1, 1, 60, DropTableEntry.Type.ITEM),

            new DropTableEntry(ItemId.AMULET_OF_FORTITUDE.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.AMULET_OF_THE_DUELIST.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.AMULET_OF_THE_HUNTER.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),

            new DropTableEntry(ItemId.CREAMY_STEED_MOUNT.asInt(), 1, 1, 50, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.SNOW_WHITE_HORSE_MOUNT.asInt(), 1, 1, 50, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.BLACK_STALLION_MOUNT.asInt(), 1, 1, 50, DropTableEntry.Type.ITEM),

            new DropTableEntry(ItemId.DARK_BROWN_WARHORSE_MOUNT.asInt(), 1, 1, 250, DropTableEntry.Type.ITEM),

            new DropTableEntry(ItemId.FAIRY_OF_HOPE.asInt(), 1, 1, 100, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.FAIRY_OF_LIGHT.asInt(), 1, 1, 100, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.FAIRY_OF_SILENCE.asInt(), 1, 1, 100, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.FAIRY_OF_DARKNESS.asInt(), 1, 1, 100, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.FAIRY_OF_FROST.asInt(), 1, 1, 100, DropTableEntry.Type.ITEM),

            new DropTableEntry(ItemId.FAIRY_ATTACK_UPGRADE.asInt(), 1, 1, 100, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.FAIRY_DEFENCE_UPGRADE.asInt(), 1, 1, 100, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.FAIRY_STRENGTH_UPGRADE.asInt(), 1, 1, 100, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.FAIRY_HITPOINTS_UPGRADE.asInt(), 1, 1, 100, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.FAIRY_RANGE_UPGRADE.asInt(), 1, 1, 100, DropTableEntry.Type.ITEM),

            new DropTableEntry(ItemId.BRUTALITE_CORE.asInt(), 1, 1, 100, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.OBSIDIAN_BULWARK.asInt(), 1, 1, 100, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.BLADESTONE.asInt(), 1, 1, 100, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.ARCHERS_EMBER.asInt(), 1, 1, 100, DropTableEntry.Type.ITEM),

            new DropTableEntry(ItemId.GREEN_DHIDE_BODY.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),
            new DropTableEntry(ItemId.GREEN_DHIDE_LEGGINGS.asInt(), 1, 1, 20, DropTableEntry.Type.ITEM),

            new DropTableEntry(ItemId.COINS.asInt(), 1, 5000, 5, DropTableEntry.Type.ITEM)
    ), null);

    @Override
    public List<Class<?>> dependencies() {
        return List.of(HikariPool.class, DropTableHandler.class);
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        List<DropTable> dailyRewards = List.of(
                firstDay,
                dayFiller,
                dayFiller,
                dayFiller,
                dayFiller,
                dayFiller,
                dayFiller,
                dayFiller,
                dayFiller,
                dayFiller,
                dayFiller,
                dayFiller,
                dayFiller,
                dayFiller,
                grandPrize // GRAND PRIZE
        );


        DailyRewardRepository repository = new DailyRewardRepository(context.get(HikariPool.class));
        DailyRewardHandler dailyRewardHandler = new DailyRewardHandler(repository, dailyRewards);

        context.bind(DailyRewardHandler.class, dailyRewardHandler);
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {

    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
