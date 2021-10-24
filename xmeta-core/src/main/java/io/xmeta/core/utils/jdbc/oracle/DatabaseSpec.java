package io.xmeta.core.utils.jdbc.oracle;

import java.util.List;

public interface DatabaseSpec {
    String getDatabaseId();

    List<String> getJdbcHost();
}
