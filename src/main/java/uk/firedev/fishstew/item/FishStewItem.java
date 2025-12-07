package uk.firedev.fishstew.item;

import com.oheers.fish.EvenMoreFish;
import com.oheers.fish.FishUtils;
import com.oheers.fish.api.config.ConfigBase;
import com.oheers.fish.api.registry.RegistryItem;
import com.oheers.fish.competition.configs.CompetitionFile;
import com.oheers.fish.items.ItemFactory;
import dev.dejvokep.boostedyaml.YamlDocument;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.fishstew.FishStewPlugin;
import uk.firedev.fishstew.utils.Keys;

import java.io.File;
import java.util.UUID;

@SuppressWarnings("UnstableApiUsage")
public class FishStewItem extends ConfigBase implements RegistryItem {

    private final @NotNull String id;
    private final @NotNull ItemFactory factory;
    private final @NotNull CompetitionFile compFile;

    public FishStewItem(@NotNull File file) throws InvalidConfigurationException {
        super(file, FishStewPlugin.getInstance(), false);
        String key = getConfig().getString("id");
        if (key == null) {
            throw new InvalidConfigurationException("ID does not exist for " + file.getName());
        }
        this.id = key;
        CompetitionFile compFile = EvenMoreFish.getInstance().getCompetitionQueue().getItem(getConfig().getString("competition-id"));
        if (compFile == null) {
            throw new InvalidConfigurationException("Competition ID does not exist for " + file.getName());
        }
        this.compFile = compFile;
        this.factory = ItemFactory.itemFactory(getConfig());
    }

    @Override
    public @NotNull String getKey() {
        return this.id;
    }

    private @NotNull ItemStack getItem() {
        return getItem(null);
    }

    public boolean isDisabled() {
        return getConfig().getBoolean("disabled", false);
    }

    public boolean shouldRespectMinimumPlayers() {
        return getConfig().getBoolean("respect-minimum-players", false);
    }

    public @NotNull ItemStack getItem(@Nullable UUID uuid) {
        ItemStack item;
        if (uuid == null) {
            item = this.factory.createItem();
        } else {
            item = this.factory.createItem(uuid);
        }
        // Apply the custom data so we can correctly identify this item.
        item.editMeta(meta -> {
            meta.getPersistentDataContainer().set(Keys.STEW_ID, PersistentDataType.STRING, this.id);
        });
        return item;
    }

    public void give(@NotNull Player player) {
        FishUtils.giveItem(getItem(player.getUniqueId()), player);
    }

    public @NotNull CompetitionFile getCompFile() {
        return this.compFile;
    }

}
