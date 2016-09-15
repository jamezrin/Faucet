package me.jaimemartz.faucet;

import java.util.LinkedHashSet;
import java.util.Set;

public class ConfigEntry<T> {
    private final int id;
    private final String path;
    private T value;

    public ConfigEntry(int id, String path, T value) {
        this.id = id;
        this.path = path;
        this.value = value;

        entries.add(this);
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    private static final Set<ConfigEntry> entries = new LinkedHashSet<>();
    public static ConfigEntry[] values() {
        return entries.toArray(new ConfigEntry[entries.size()]);
    }

    public static String path(String... parts) {
        final StringBuilder builder = new StringBuilder();
        int index = 0;
        while (index < (parts.length - 1)) {
            builder.append(parts [index++]);
            builder.append(".");
        }
        builder.append(parts [index]);
        return builder.toString();
    }
}