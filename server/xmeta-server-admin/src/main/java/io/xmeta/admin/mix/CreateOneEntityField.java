package io.xmeta.admin.mix;

import io.xmeta.graphql.model.EntityFieldCreateInput;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOneEntityField {
    EntityFieldCreateInput data;
    String relatedFieldName;
    String relatedFieldDisplayName;
}
