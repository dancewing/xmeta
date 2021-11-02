package io.xmeta.core.utils;

import java.util.HashMap;
import java.util.Map;

public final class Maps {
    private Maps() {
    }

    public static Map<String, Object> empty() {
        return new HashMap<>();
    }

    public static Maps.MapBuilder<String, Object> of(String key, Object value) {
        return new Maps.HashMapBuilder().and(key, value);
    }

    public interface MapBuilder<String, Object> {
        Maps.MapBuilder<String, Object> and(String var1, Object var2);

        Map<String, Object> build();
    }

    private static class HashMapBuilder<String, Object> implements Maps.MapBuilder<String, Object> {
        private final Map<String, Object> data;

        private HashMapBuilder() {
            this.data = new HashMap<>();
        }

        public Maps.MapBuilder<String, Object> and(String key, Object value) {
            this.data.put(key, value);
            return this;
        }

        public Map<String, Object> build() {
            return this.data;
        }
    }
}
