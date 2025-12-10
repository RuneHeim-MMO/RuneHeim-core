package org.zuma.examplePlugin.core.module.game;


import org.bukkit.plugin.Plugin;
import org.zuma.examplePlugin.core.database.HikariPool;
import org.zuma.examplePlugin.core.module.ModuleContext;
import org.zuma.examplePlugin.core.module.PluginModule;
import org.zuma.examplePlugin.domain.daily.DailyRewardHandler;
import org.zuma.examplePlugin.domain.daily.persistence.DailyRewardRepository;
import org.zuma.examplePlugin.domain.droptable.DropTable;
import org.zuma.examplePlugin.domain.droptable.DropTableHandler;
import org.zuma.examplePlugin.domain.droptable.DropTableId;
import org.zuma.examplePlugin.domain.item.ItemRegistry;

import java.util.List;

public class DailyRewardModule implements PluginModule {

    @Override
    public List<Class<?>> dependencies() {
        return List.of(HikariPool.class, DropTableHandler.class);
    }

    @Override
    public void start(Plugin plugin, ModuleContext context) {
        List<DropTable> dailyRewards = List.of(
                context.get(DropTableHandler.class).getByID(DropTableId.DAILY_REWARD_FIRST_DAY.asInt()),
                context.get(DropTableHandler.class).getByID(DropTableId.DAILY_REWARD_DAY_FILLER.asInt()),
                context.get(DropTableHandler.class).getByID(DropTableId.DAILY_REWARD_DAY_FILLER.asInt()),
                context.get(DropTableHandler.class).getByID(DropTableId.DAILY_REWARD_DAY_FILLER.asInt()),
                context.get(DropTableHandler.class).getByID(DropTableId.DAILY_REWARD_DAY_FILLER.asInt()),
                context.get(DropTableHandler.class).getByID(DropTableId.DAILY_REWARD_DAY_FILLER.asInt()),
                context.get(DropTableHandler.class).getByID(DropTableId.DAILY_REWARD_DAY_FILLER.asInt()),
                context.get(DropTableHandler.class).getByID(DropTableId.DAILY_REWARD_DAY_FILLER.asInt()),
                context.get(DropTableHandler.class).getByID(DropTableId.DAILY_REWARD_DAY_FILLER.asInt()),
                context.get(DropTableHandler.class).getByID(DropTableId.DAILY_REWARD_DAY_FILLER.asInt()),
                context.get(DropTableHandler.class).getByID(DropTableId.DAILY_REWARD_DAY_FILLER.asInt()),
                context.get(DropTableHandler.class).getByID(DropTableId.DAILY_REWARD_DAY_FILLER.asInt()),
                context.get(DropTableHandler.class).getByID(DropTableId.DAILY_REWARD_DAY_FILLER.asInt()),
                context.get(DropTableHandler.class).getByID(DropTableId.DAILY_REWARD_DAY_FILLER.asInt()),
                context.get(DropTableHandler.class).getByID(DropTableId.DAILY_REWARD_GRAND_PRIZE.asInt()) // GRAND PRIZE
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
