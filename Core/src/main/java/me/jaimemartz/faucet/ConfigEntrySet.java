package me.jaimemartz.faucet;

import java.util.LinkedHashSet;
import java.util.Set;

public class ConfigEntrySet {
    private static final Set<ConfigEntry> entries = new LinkedHashSet<>();

    public static <T> ConfigEntry<T> entry(int id, String path, T value) {
        ConfigEntry<T> entry = new ConfigEntry<>(id, path, value);
        entries.add(entry);
        return entry;
    }

    public static ConfigEntry[] values() {
        return entries.toArray(new ConfigEntry[entries.size()]);
    }
}
