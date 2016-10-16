package me.jaimemartz.faucet;

import org.bukkit.configuration.ConfigurationSection;

public interface ConfigObject {
    void get(ConfigurationSection section);
    void set(ConfigurationSection section, boolean first);
}
