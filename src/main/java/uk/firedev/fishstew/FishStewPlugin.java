package uk.firedev.fishstew;

import com.oheers.fish.api.FileUtil;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.fishstew.command.MainCommand;
import uk.firedev.fishstew.item.FishStewItem;
import uk.firedev.fishstew.item.FishStewManager;
import uk.firedev.fishstew.item.FishStewRegistry;

import java.io.File;
import java.util.List;
import java.util.logging.Level;

public final class FishStewPlugin extends JavaPlugin {

    private static FishStewPlugin INSTANCE;

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
        registerCommands();
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new InteractListener(), this);
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
