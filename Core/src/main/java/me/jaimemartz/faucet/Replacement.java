package me.jaimemartz.faucet;

public class Replacement {
    private final String target;
    private final String replacement;

    public Replacement(String target, String replacement) {
        this.target = target;
        this.replacement = replacement;
    }

    public String getTarget() {
        return target;
    }

    public String getReplacement() {
        return replacement;
    }

    public String replace(String string) {
        return string.replace(target, replacement);
    }
}
