package me.jaimemartz.faucet;

import net.md_5.bungee.config.Configuration;

public interface ConfigObject {
    void load(Configuration section);
    void save(Configuration section, boolean first);
}
