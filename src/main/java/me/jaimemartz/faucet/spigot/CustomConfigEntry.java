package me.jaimemartz.faucet.spigot;

import me.jaimemartz.faucet.ConfigEntry;
import org.bukkit.configuration.ConfigurationSection;

public abstract class CustomConfigEntry extends ConfigEntry<Object> {
    public CustomConfigEntry(int id, String path) {
        super(id, path, null);
    }

    public abstract void load(ConfigurationSection section);
    public abstract void save(ConfigurationSection section, boolean first);

}