package me.jaimemartz.faucet;

import org.apache.commons.lang3.Validate;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ConfigFactory {
    private final Map<Integer, ConfigFile> configs;
    private final JavaPlugin owner;
    public ConfigFactory(JavaPlugin owner) {
        Validate.notNull(owner);
        this.configs = new LinkedHashMap<>();
        this.owner = owner;

        ConfigFactory instance = instances.putIfAbsent(owner, this);
        Validate.isTrue(instance == null, "There is an factory already registered to that plugin");
    }

    public ConfigFile register(int id, String name) {
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
                    try (InputStream in = file.getOwner().getResource(file.getFile().getName())) {
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
                    for (ConfigEntry entry : file.getEntries()) {
                        boolean first = file.get(entry.getPath()) == null;
                        Object object = entry.get();
                        if (object instanceof ConfigObject) {
                            file.getHandle().addDefault(entry.getPath(), null);
                            ((ConfigObject) object).save(file.getHandle().getConfigurationSection(entry.getPath()), first);
                        } else {
                            file.getHandle().addDefault(entry.getPath(), object);
                        }
                    }
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

    public JavaPlugin getOwner() {
        return owner;
    }

    public static final Map<JavaPlugin, ConfigFactory> instances = new LinkedHashMap<>();

    public static ConfigFactory getFactory(JavaPlugin plugin) {
        Validate.notNull(plugin);
        return instances.get(plugin);
    }
}