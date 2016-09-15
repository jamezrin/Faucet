package me.jaimemartz.faucet.spigot;

import me.jaimemartz.faucet.ConfigEntry;
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

    public ConfigFile(int id, JavaPlugin owner, String name) {
        this.id = id;
        this.owner = owner;
        entries = new LinkedHashSet<>();
        file = new File(owner.getDataFolder(), name);
    }

    @SuppressWarnings("unchecked")
    public void load(boolean update) throws IOException {
        config = YamlConfiguration.loadConfiguration(file);
        if (update) {
            for (ConfigEntry entry : entries) {
                entry.set(config.get(entry.getPath(), entry.get()));
                if (entry instanceof CustomConfigEntry) {
                    ((CustomConfigEntry) entry).load(config.getConfigurationSection(entry.getPath()));
                }
            }
        }
    }

    public void save() throws IOException {
        for (ConfigEntry entry : entries) {
            boolean first = config.get(entry.getPath()) == null;
            config.set(entry.getPath(), entry.get());
            if (entry instanceof CustomConfigEntry) {
                ((CustomConfigEntry) entry).save(config.getConfigurationSection(entry.getPath()), first);
            }
        }
        config.save(file);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String path) {
        return (T) config.get(path);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String path, T def) {
        return (T) config.get(path, def);
    }

    public void set(String path, Object object) {
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
        return config != null;
    }
}
