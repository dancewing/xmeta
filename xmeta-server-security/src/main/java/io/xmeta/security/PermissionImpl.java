package io.xmeta.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class PermissionImpl implements Permission, Serializable {

    private String action;
    private String subject;
    private List<String> fields;
    private List<String> conditions;
}
