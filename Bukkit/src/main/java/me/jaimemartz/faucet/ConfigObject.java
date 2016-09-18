package me.jaimemartz.faucet;

import org.bukkit.configuration.ConfigurationSection;

public interface ConfigObject {
    void load(ConfigurationSection section);
    void save(ConfigurationSection section, boolean first);
}
