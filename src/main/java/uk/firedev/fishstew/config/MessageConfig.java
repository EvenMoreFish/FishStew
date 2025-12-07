package uk.firedev.fishstew.config;

import com.oheers.fish.api.config.ConfigBase;
import com.oheers.fish.messages.EMFConfigLoader;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import uk.firedev.fishstew.FishStewPlugin;
import uk.firedev.messagelib.message.ComponentMessage;
import uk.firedev.messagelib.message.ComponentSingleMessage;
import uk.firedev.messagelib.replacer.Replacer;

@SuppressWarnings("UnstableApiUsage")
public class MessageConfig extends ConfigBase {

    private static MessageConfig INSTANCE = null;

    private final FishStewConfigLoader loader;

    public MessageConfig() {
        super("messages.yml", "messages.yml", FishStewPlugin.getInstance(), true);
        this.loader = new FishStewConfigLoader(getConfig());
    }

    public void init() {
        if (INSTANCE != null) {
            throw new UnsupportedOperationException(getClass().getSimpleName() + " has already been assigned!");
        }
        INSTANCE = this;
    }

    public static @NotNull MessageConfig getInstance() {
        if (INSTANCE == null) {
            throw new UnsupportedOperationException(MessageConfig.class.getSimpleName() + " has not been assigned!");
        }
        return INSTANCE;
    }

    public @NotNull ComponentSingleMessage getPrefix() {
        return ComponentMessage.componentMessage(loader, "prefix", "<gray>[FishStew]</gray> ").toSingleMessage();
    }

    public @NotNull Replacer getPrefixReplacer() {
        return Replacer.replacer().addReplacement("{prefix}", getPrefix());
    }

    public @NotNull ComponentMessage getReloaded() {
        return getMessage("reloaded", "{prefix}<white>Successfully reloaded the plugin.");
    }

    public @NotNull ComponentMessage getStewReceived() {
        return getMessage("stew-received", "{prefix}<white>You have been given a fish stew.");
    }

    public @NotNull ComponentMessage getStewGiven(@NotNull Player target) {
        return getMessage("stew-given", "{prefix}<white>You have given {target} a fish stew.")
            .replace("{target}", target.name());
    }

    public @NotNull ComponentMessage getNotEnoughPlayers() {
        return getMessage("not-enough-players", "{prefix}<white>There are not enough players online to start the competition.");
    }

    public @NotNull ComponentMessage getCompetitionActive() {
        return getMessage("competition-active", "{prefix}<white>A competition is already active! Please wait until it is over.");
    }

    public @NotNull ComponentMessage getStewInvalid() {
        return getMessage("stew-invalid", "{prefix}<white>This fish stew is no longer valid.");
    }

    private @NotNull ComponentMessage getMessage(@NotNull String path, @NotNull String def) {
        return ComponentMessage.componentMessage(loader, path, def).replace(getPrefixReplacer());
    }

}
