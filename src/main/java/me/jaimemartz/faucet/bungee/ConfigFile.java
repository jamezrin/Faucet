package me.jaimemartz.faucet.bungee;

import me.jaimemartz.faucet.ConfigEntry;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

public class ConfigFile {
    private final int id;
    private final Plugin owner;
    private final File file;
    private final Set<ConfigEntry> entries;
    private Configuration config;

    public ConfigFile(int id, Plugin owner, String name) {
        this.id = id;
        this.owner = owner;
        entries = new LinkedHashSet<>();
        file = new File(owner.getDataFolder(), name);
    }

    @SuppressWarnings("unchecked")
    public void load(boolean update) throws IOException {
        config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        if (update) {
            for (ConfigEntry entry : entries) {
                entry.set(config.get(entry.getPath(), entry.get()));
                if (entry instanceof CustomConfigEntry) {
                    ((CustomConfigEntry) entry).load(config.getSection(entry.getPath()));
                }
            }
        }
    }

    public void save() throws IOException {
        for (ConfigEntry entry : entries) {
            boolean first = config.get(entry.getPath()) == null;
            config.set(entry.getPath(), entry.get());
            if (entry instanceof CustomConfigEntry) {
                ((CustomConfigEntry) entry).save(config.getSection(entry.getPath()), first);
            }
        }
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String path) {
        return get(path, (T) config.getDefault(path));
    }

    public <T> T get(String path, T def) {
        return config.get(path, def);
    }

    public void set(String path, Object object) {
        config.set(path, object);
    }

    public int getId() {
        return id;
    }

    public Plugin getOwner() {
        return owner;
    }

    public File getFile() {
        return file;
    }

    public Set<ConfigEntry> getEntries() {
        return entries;
    }

    public Configuration getHandle() {
        return config;
    }

    public boolean isLoaded() {
        return config != null;
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
