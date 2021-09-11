package io.xmeta.graphql.mix;

import graphql.kickstart.tools.TypeDefinitionFactory;
import graphql.language.Definition;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CustomTypeDefinitionFactory implements TypeDefinitionFactory {
    @NotNull
    @Override
    public List<Definition<?>> create(@NotNull List<Definition<?>> list) {
        // return ObjectTypeDefinition.newObjectTypeDefinition().name("").fieldDefinition(new FieldDefinition("",
        //         new TypeName(""))).build();
        return list;
    }
}
