package uk.firedev.fishstew.reward;

import com.oheers.fish.api.reward.RewardType;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import uk.firedev.fishstew.FishStewPlugin;

public class FishStewRewardType extends RewardType {

    @Override
    public void doReward(@NotNull Player player, @NotNull String s, @NotNull String s1, Location location) {

    }

    @Override
    public @NotNull String getIdentifier() {
        return "";
    }

    @Override
    public @NotNull String getAuthor() {
        return "";
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return FishStewPlugin.getInstance();
    }

}
