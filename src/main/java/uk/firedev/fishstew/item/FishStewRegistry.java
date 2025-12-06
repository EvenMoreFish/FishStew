package uk.firedev.fishstew.item;

import com.oheers.fish.api.registry.EMFRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.TreeMap;

public class FishStewRegistry implements EMFRegistry<FishStewItem> {

    private static final FishStewRegistry instance = new FishStewRegistry();

    private final Map<String, FishStewItem> registry = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    private FishStewRegistry() {}

    public static @NotNull FishStewRegistry getInstance() {
        return instance;
    }

    @Override
    public @NotNull Map<String, FishStewItem> getRegistry() {
        return Map.copyOf(registry);
    }

    @Override
    public @Nullable FishStewItem get(@NotNull String s) {
        return registry.get(s);
    }

    @Override
    public @NotNull FishStewItem getOrDefault(@NotNull String s, @NotNull FishStewItem fishStewItem) {
        return registry.getOrDefault(s, fishStewItem);
    }

    @Override
    public boolean unregister(@NotNull String s) {
        return registry.remove(s) != null;
    }

    @Override
    public boolean register(@NotNull FishStewItem value, boolean force) {
        if (!force && registry.containsKey(value.getKey())) {
            return false;
        }
        registry.put(value.getKey(), value);
        return true;
    }

}
