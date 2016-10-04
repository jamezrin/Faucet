package me.jaimemartz.faucet;

import net.md_5.bungee.api.plugin.Plugin;
import org.apache.commons.lang3.Validate;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ConfigFactory {
    public ConfigFile register(int id, String name, Plugin owner) {
        Validate.isTrue(!configs.containsKey(id), "A configuration with this id is already registered");
        owner.getDataFolder().mkdir();
        ConfigFile file = new ConfigFile(id, owner, name);
        configs.put(id, file);
        return file;
    }

    public void submit(ConfigEntry... entries) {
        Validate.notEmpty(entries);
        for (ConfigEntry entry : entries) {
            ConfigFile file = get(entry.getId());
            file.getEntries().add(entry);
        }
    }

    public ConfigFile get(int id) {
        ConfigFile file = configs.get(id);
        Validate.notNull(file, "No such configuration registered with that id");
        return file;
    }

    public ConfigFile get(String name) {
        ConfigFile file = configs.values().stream().filter(name::equals).findAny().orElse(null);
        Validate.notNull(file, "No such configuration found registered with that name");
        return file;
    }

    public void load(int id, boolean resource) {
        ConfigFile file = get(id);
        if (resource) {
            try {
                if (!file.getFile().exists()) {
                    try (InputStream in = file.getOwner().getResourceAsStream(file.getFile().getName())) {
                        Files.copy(in, file.getFile().toPath());
                    }
                }
                file.load(true);
            } catch (IOException | NullPointerException e) {
                new IOException("Could not access the resource", e).printStackTrace();
            }
        } else {
            try {
                if (file.getFile().createNewFile()) {
                    file.load(false);
                    file.save();
                } else {
                    file.load(true);
                }
            } catch (IOException e) {
                new IOException("Could not load the file", e).printStackTrace();
            }
        }
    }

    public void save(int id) {
        ConfigFile file = get(id);
        try {
            file.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final Map<Integer, ConfigFile> configs = new LinkedHashMap<>();
}