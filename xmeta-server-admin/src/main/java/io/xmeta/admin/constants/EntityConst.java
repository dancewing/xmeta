package io.xmeta.admin.constants;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.xmeta.admin.model.EntityCreateInput;
import io.xmeta.admin.model.EntityFieldCreateInput;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public abstract class EntityConst {

    public static final String USER_ENTITY_NAME = "User";

    public static List<EntityTemplate> getDefaultEntities() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            EntityTemplate[] entityEntities = objectMapper.readValue(EntityConst.class.getResourceAsStream(
                    "/default_entities.json"), EntityTemplate[].class);
            if (entityEntities != null) {
                return Arrays.asList(entityEntities);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Getter
    @Setter
    public static class EntityTemplate extends EntityCreateInput {
        private List<EntityFieldCreateInput> fields;
    }
}
