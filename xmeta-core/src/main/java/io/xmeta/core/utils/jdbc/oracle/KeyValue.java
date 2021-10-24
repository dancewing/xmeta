package io.xmeta.core.utils.jdbc.oracle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface KeyValue<V> {

    String getKey();

    V getValue();

    class TerminalKeyValue implements KeyValue<String> {

        public final String key;
        public final String value;

        public TerminalKeyValue(String key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "TerminalKeyValue{" +
                    "key='" + key + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }

    class KeyValueList implements KeyValue<List<KeyValue<?>>> {

        public final String key;
        public final List<KeyValue<?>> value;

        public KeyValueList(String key, List<KeyValue<?>> value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String getKey() {
            return key;
        }

        public List<KeyValue<?>> getValue() {
            if (value == null) {
                return Collections.emptyList();
            }
            return value;
        }

        @Override
        public String toString() {
            return "KeyValueList{" +
                    "key='" + key + '\'' +
                    ", value=" + value +
                    '}';
        }
    }


    class Builder {
        public final String key;
        public String value;
        public List<KeyValue<?>> keyValueList;

        public Builder(String key) {
            this.key = key;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public void addKeyValueList(KeyValue<?> keyValue) {
            if (keyValueList == null) {
                this.keyValueList = new ArrayList<>();
            }
            this.keyValueList.add(keyValue);
        }

        public KeyValue build() {
            if (value != null) {
                return new TerminalKeyValue(key, value);
            }
            return new KeyValueList(key, keyValueList);
        }
    }

}
