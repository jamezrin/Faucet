package me.jaimemartz.faucet;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class Messager {
    private final CommandSender sender;

    public Messager(CommandSender sender) {
        this.sender = sender;
    }

    public void send(String string) {
        if (string != null) {
            send(TextComponent.fromLegacyText(colorize(string)));
        }
    }

    public void send(String... strings) {
        for (String string : strings) {
            send(string);
        }
    }

    public void send(String string, Replacement... replacements) {
        if (string != null) {
            for (Replacement replacement : replacements) {
                string = replacement.replace(string);
            }
            send(string);
        }
    }

    public void send(BaseComponent... components) {
        if (sender instanceof Player) {
            ((Player) sender).spigot().sendMessage(components);
        } else {
            sender.sendMessage(TextComponent.toLegacyText(components));
        }
    }

    public static String colorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String decolorize(String text) {
        return ChatColor.stripColor(text);
    }
}
