package org.evenmorefish.fishstew;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;
import org.evenmorefish.fishstew.metrics.Metrics;
import org.jetbrains.annotations.NotNull;
import org.evenmorefish.fishstew.command.MainCommand;
import org.evenmorefish.fishstew.config.MessageConfig;
import org.evenmorefish.fishstew.item.FishStewManager;

public final class FishStewPlugin extends JavaPlugin {

    private static FishStewPlugin INSTANCE;

    private Metrics metrics;

    public FishStewPlugin() {
        if (INSTANCE != null) {
            throw new UnsupportedOperationException(getClass().getSimpleName() + " has already been assigned!");
        }
        INSTANCE = this;
    }

    public static @NotNull FishStewPlugin getInstance() {
        if (INSTANCE == null) {
            throw new UnsupportedOperationException(FishStewPlugin.class.getSimpleName() + " has not been assigned!");
        }
        return INSTANCE;
    }

    @Override
    public void onLoad() {
        new MessageConfig().init();
        registerCommands();
    }

    @Override
    public void onEnable() {
        this.metrics = new Metrics(this, 28265);

        getServer().getPluginManager().registerEvents(new FishStewListener(), this);
        FishStewManager.getInstance().load();
    }

    @Override
    public void onDisable() {
        FishStewManager.getInstance().unload();
    }

    @SuppressWarnings("UnstableApiUsage")
    private void registerCommands() {
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(MainCommand.get());
        });
    }

    public void reload() {
        FishStewManager.getInstance().reload();
    }

}
