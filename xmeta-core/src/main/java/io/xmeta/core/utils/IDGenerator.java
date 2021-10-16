package io.xmeta.core.utils;

public final class IDGenerator {
    private static final Snowflake SNOWFLAKE = new Snowflake(10, 10);

    public static String nextId() {
        return SNOWFLAKE.nextIdStr();
    }
}
