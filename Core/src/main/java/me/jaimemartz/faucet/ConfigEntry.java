package me.jaimemartz.faucet;

public class ConfigEntry <T> {
    private final int id;
    private final String path;
    private T value;

    protected ConfigEntry(int id, String path, T value) {
        this.id = id;
        this.path = path;
        this.value = value;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public static String path(String... parts) {
        return StringCombiner.combine(parts, ".");
    }
}