package me.jaimemartz.faucet;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

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
        sender.sendMessage(components);
    }

    public static String colorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String decolorize(String text) {
        return ChatColor.stripColor(text);
    }
}
