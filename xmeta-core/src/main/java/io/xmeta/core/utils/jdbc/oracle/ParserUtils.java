package io.xmeta.core.utils.jdbc.oracle;

public final class ParserUtils {
    private ParserUtils() {
    }

    public static boolean compare(String value, KeyValue<?> kv) {
        if (kv == null) {
            return false;
        }
        return value.equals(kv.getKey());
    }

}
