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

    public Messager send(String text) {
        send(transform(text));
        return this;
    }

    public Messager send(String... texts) {
        for (String text : texts) {
            send(text);
        }
        return this;
    }

    public Messager send(BaseComponent... components) {
        sender.sendMessage(components);
        return this;
    }

    public static Messager send(CommandSender sender, String text) {
        return new Messager(sender).send(text);
    }

    public static Messager send(CommandSender sender, BaseComponent... components) {
        return new Messager(sender).send(components);
    }

    public static String colorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String decolorize(String text) {
        return ChatColor.stripColor(text);
    }

    public static BaseComponent[] transform(String text) {
        return TextComponent.fromLegacyText(colorize(text));
    }
}
