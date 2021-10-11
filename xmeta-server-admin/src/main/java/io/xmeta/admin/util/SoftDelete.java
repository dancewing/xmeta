package io.xmeta.admin.util;

import org.apache.commons.lang.StringUtils;

public final class SoftDelete {
    public static String prepareDeletedItemName(String value, String id) {
        return "__" + id + "_" + value;
    }

    public static String revertDeletedItemName(String value, String id) {
        return StringUtils.replace(value, "__" + id + "_", "");
    }
}
