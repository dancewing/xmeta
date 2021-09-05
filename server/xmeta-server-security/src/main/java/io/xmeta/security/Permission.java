package io.xmeta.security;

import java.util.List;

public interface Permission {

    String getAction();

    String getSubject();

    List<String> getFields();

    List<String> getConditions();
}
