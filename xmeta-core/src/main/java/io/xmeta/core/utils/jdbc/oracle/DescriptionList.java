package io.xmeta.core.utils.jdbc.oracle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.xmeta.core.utils.jdbc.oracle.ParserUtils.compare;


public class DescriptionList implements DatabaseSpec {
    public static final String DESCRIPTION_LIST = "description_list";

    private final List<Description> descriptionList = new ArrayList<>();

    public DescriptionList(KeyValue<?> keyValue) {
        if (keyValue instanceof KeyValue.TerminalKeyValue) {
            if (!compare(DESCRIPTION_LIST, keyValue)) {
                throw new OracleConnectionStringException(DESCRIPTION_LIST + " node not found");
            }
        }
        if (keyValue instanceof KeyValue.KeyValueList) {
            KeyValue.KeyValueList kvList = (KeyValue.KeyValueList) keyValue;
            for (KeyValue<?> desc : kvList.getValue()) {
                if (compare(Description.DESCRIPTION, desc)) {
                    Description description = new Description(desc);
                    this.descriptionList.add(description);
                }
            }
        }
    }

    public List<Description> getDescriptionList() {
        return descriptionList;
    }

    @Override
    public String getDatabaseId() {
        Set<String> databaseIds = new HashSet<>();
        for (Description description : descriptionList) {
            databaseIds.add(description.getDatabaseId());
        }
        if (databaseIds.size() == 1) {
            return databaseIds.iterator().next();
        }
        return databaseIds.toString();
    }

    @Override
    public List<String> getJdbcHost() {
        List<String> jdbcHost = new ArrayList<>();
        for (Description description : descriptionList) {
            jdbcHost.addAll(description.getJdbcHost());
        }
        return jdbcHost;
    }

}
