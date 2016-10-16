package me.jaimemartz.faucet;

import net.md_5.bungee.config.Configuration;

public interface ConfigObject {
    void get(Configuration section);
    void set(Configuration section, boolean first);
}
