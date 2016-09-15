package me.jaimemartz.faucet;

import java.util.HashSet;
import java.util.Set;

public class ConfigEntry<T> {
    private final int id;
    private final String path;
    private T value;

    public ConfigEntry(int id, String path, T value) {
        this.id = id;
        this.path = path;
        this.value = value;

        values.add(this);
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

    private static final Set<ConfigEntry> values = new HashSet<>();
    public static ConfigEntry[] values() {
        return values.toArray(new ConfigEntry[values.size()]);
    }
}