package uk.firedev.fishstew.item;

import com.oheers.fish.api.FileUtil;
import org.bukkit.configuration.InvalidConfigurationException;
import org.jetbrains.annotations.NotNull;
import uk.firedev.fishstew.FishStewPlugin;

import java.io.File;
import java.util.List;

public class FishStewManager {

    private static final FishStewManager instance = new FishStewManager();

    private final File itemDirectory = new File(FishStewPlugin.getInstance().getDataFolder(), "items");

    private FishStewManager() {}

    public static @NotNull FishStewManager getInstance() {
        return instance;
    }

    public void load() {
        loadExampleFile();
        loadFiles();
        logLoadedItems();
    }

    private void loadExampleFile() {
        if (!itemDirectory.exists()) {
            itemDirectory.mkdirs();
        }
        FishStewPlugin.getInstance().saveResource("items/_example.yml", true);
    }

    private void loadFiles() {
        List<File> stewFiles = FileUtil.getFilesInDirectory(itemDirectory, true, true);
        stewFiles.forEach(this::loadFile);
    }

    private void loadFile(@NotNull File file) {
        try {
            FishStewItem item = new FishStewItem(file);
            if (item.isDisabled()) {
                return;
            }
            FishStewRegistry.getInstance().register(item);
        } catch (InvalidConfigurationException exception) {
            FishStewPlugin.getInstance().getLogger().warning(exception.getMessage());
        }
    }

    private void logLoadedItems() {
        FishStewPlugin.getInstance().getLogger().info(
            "Loaded FishStewManager with " + FishStewRegistry.getInstance().getSize() + " Items."
        );
    }

    public void reload() {
        unload();
        load();
    }

    public void unload() {
        FishStewRegistry.getInstance().clear();
    }

}
