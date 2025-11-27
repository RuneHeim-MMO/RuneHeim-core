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
    private final static DropTable dayFiller = new DropTable(-1, List.of(
            new DropTableEntry(ItemId.COINS.asInt(), 1, 500, 1, DropTableEntry.Type.ITEM)
    ), null);

    @Override
    public List<Class<?>> dependencies() {
        return List.of(HikariPool.class, DropTableHandler.class);
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        List<DropTable> dailyRewards = List.of(
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
                dayFiller,
                dayFiller // GRAND PRIZE
        );


        DailyRewardRepository repository = new DailyRewardRepository(context.get(HikariPool.class));
        DailyRewardHandler dailyRewardHandler = new DailyRewardHandler(repository, dailyRewards);

        context.bind(DailyRewardHandler.class, dailyRewardHandler);
        System.out.println("Bound DailyRewardHandler");
    }

    @Override
    public void stop(Plugin plugin, ModuleContext context) {

    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }
}
