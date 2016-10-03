package me.jaimemartz.faucet;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public final class Messager {
    private final CommandSender sender;

    public Messager(CommandSender sender) {
        this.sender = sender;
    }

    public Messager send(String text) {
        send(colorize(text));
        return this;
    }

    public Messager send(String... texts) {
        for (String text : texts) {
            send(colorize(text));
        }
        return this;
    }

    public static Messager send(CommandSender sender, String text) {
        return new Messager(sender).send(text);
    }

    public static String colorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String decolorize(String text) {
        return ChatColor.stripColor(text);
    }
}
