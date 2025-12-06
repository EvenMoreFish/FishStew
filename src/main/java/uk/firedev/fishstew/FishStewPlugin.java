package uk.firedev.fishstew;

import com.oheers.fish.api.FileUtil;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.fishstew.command.MainCommand;
import uk.firedev.fishstew.item.FishStewItem;
import uk.firedev.fishstew.item.FishStewRegistry;

import java.io.File;
import java.util.List;
import java.util.logging.Level;

public final class FishStewPlugin extends JavaPlugin {

    private static FishStewPlugin INSTANCE;

    private final File itemDirectory = new File(getDataFolder(), "items");

    public FishStewPlugin() {
        if (INSTANCE != null) {
            throw new UnsupportedOperationException(getClass().getName() + " has already been assigned!");
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
        if (!itemDirectory.exists()) {
            itemDirectory.mkdirs();
        }
        registerCommands();
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new InteractListener(), this);
        reload();
    }

    @Override
    public void onDisable() {}

    @SuppressWarnings("UnstableApiUsage")
    private void registerCommands() {
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(MainCommand.get());
        });
    }

    public void reload() {
        FishStewRegistry.getInstance().clear();
        List<File> stewFiles = FileUtil.getFilesInDirectory(itemDirectory, true, true);

        for (File file : stewFiles) {
            try {
                FishStewItem item = new FishStewItem(file);
                FishStewRegistry.getInstance().register(item);
            } catch (InvalidConfigurationException exception) {
                getLogger().warning(exception.getMessage());
            }
        }
    }

}
