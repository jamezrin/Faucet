package me.jaimemartz.faucet.bungee;

import me.jaimemartz.faucet.ConfigEntry;
import net.md_5.bungee.config.Configuration;

public abstract class CustomConfigEntry extends ConfigEntry<Object> {
    public CustomConfigEntry(int id, String path) {
        super(id, path, null);
    }

    public abstract void load(Configuration section);
    public abstract void save(Configuration section, boolean first);

}