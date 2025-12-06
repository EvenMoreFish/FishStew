package uk.firedev.fishstew.item;

import com.oheers.fish.api.config.ConfigBase;
import com.oheers.fish.api.registry.RegistryItem;
import org.bukkit.configuration.InvalidConfigurationException;
import org.jetbrains.annotations.NotNull;
import uk.firedev.fishstew.FishStewPlugin;

import java.io.File;

@SuppressWarnings("UnstableApiUsage")
public class FishStewItem implements RegistryItem {

    private final @NotNull String key;

    public FishStewItem(@NotNull File file) throws InvalidConfigurationException {
        ConfigBase config = new ConfigBase(file, FishStewPlugin.getInstance(), false);
        String key = config.getConfig().getString("id");
        if (key == null) {
            throw new InvalidConfigurationException("ID does not exist for " + file.getName());
        }
        this.key = key;
    }

    @Override
    public @NotNull String getKey() {
        return this.key;
    }

}
