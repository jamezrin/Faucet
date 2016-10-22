package me.jaimemartz.faucet;

import org.apache.commons.lang3.Validate;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

public class ConfigFile {
    private final int id;
    private final JavaPlugin owner;
    private final File file;
    private final Set<ConfigEntry> entries;
    private FileConfiguration config;
    private boolean loaded = false;

    public ConfigFile(int id, JavaPlugin owner, String name) {
        this.id = id;
        this.owner = owner;
        entries = new LinkedHashSet<>();
        file = new File(owner.getDataFolder(), name);
    }

    public void load(boolean update) throws IOException {
        config = YamlConfiguration.loadConfiguration(file);
        if (update) {
            for (ConfigEntry entry : entries) {
                Object value = entry.get();
                if (value instanceof ConfigObject) {
                    ((ConfigObject) value).get(config.getConfigurationSection(entry.getPath()));
                    entry.set(value);
                } else {
                    entry.set(config.get(entry.getPath(), entry.get()));
                }
            }
        }
        loaded = true;
    }

    public void save() throws IOException {
        Validate.isTrue(loaded, "The configuration has not been loaded yet");
        for (ConfigEntry entry : entries) {
            boolean first = config.get(entry.getPath()) == null;
            Object object = entry.get();
            if (object instanceof ConfigObject) {
                ((ConfigObject) object).set(config.createSection(entry.getPath()), first);
            } else {
                config.set(entry.getPath(), entry.get());
            }
        }
        config.save(file);
    }

    public <T> T get(String path) {
        Validate.isTrue(loaded, "The configuration has not been loaded yet");
        return (T) config.get(path);
    }

    public <T> T get(String path, T def) {
        Validate.isTrue(loaded, "The configuration has not been loaded yet");
        return (T) config.get(path, def);
    }

    public void set(String path, Object object) {
        Validate.isTrue(loaded, "The configuration has not been loaded yet");
        config.set(path, object);
    }

    public int getId() {
        return id;
    }

    public JavaPlugin getOwner() {
        return owner;
    }

    public File getFile() {
        return file;
    }

    public Set<ConfigEntry> getEntries() {
        return entries;
    }

    public FileConfiguration getHandle() {
        return config;
    }

    public boolean isLoaded() {
        return loaded;
    }
}