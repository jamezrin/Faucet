package me.jaimemartz.faucet;

import java.util.Collection;

public final class StringCombiner {
    private StringCombiner() {}

    public static String combine(String[] array, int startIndex, int stopIndex, String delimiter) {
        StringBuilder builder = new StringBuilder();
        int index = startIndex;
        while (index < stopIndex) {
            builder.append(array [index++]);
            builder.append(delimiter);
        }
        builder.append(array [index]);
        return builder.toString();
    }

    public static String combine(String[] array, int startIndex, String delimiter) {
        return combine(array, startIndex, array.length - 1, delimiter);
    }

    public static String combine(String[] array, String delimiter) {
        return combine(array, 0, delimiter);
    }

    public static String combine(Collection<String> coll, int startIndex, int stopIndex, String delimiter) {
        return combine(coll.stream().toArray(String[]::new), startIndex, stopIndex, delimiter);
    }

    public static String combine(Collection<String> coll, int startIndex, String delimiter) {
        return combine(coll, startIndex, coll.size() - 1, delimiter);
    }

    public static String combine(Collection<String> coll, String delimiter) {
        return combine(coll, 0, delimiter);
    }
}
