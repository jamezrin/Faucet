package me.jaimemartz.faucet;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public final class ConfigUtil {
    public static Configuration loadConfig(String name, Plugin plugin) {
        File file = new File(plugin.getDataFolder(), name);
        if (!plugin.getDataFolder().exists())
            plugin.getDataFolder().mkdir();
        if (!file.exists()) {
            try {
                Files.copy(plugin.getResourceAsStream(file.getName()), file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean saveConfig(Configuration config, String name, Plugin plugin) {
        File file = new File(plugin.getDataFolder(), name);
        if (!plugin.getDataFolder().exists())
            plugin.getDataFolder().mkdir();
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
