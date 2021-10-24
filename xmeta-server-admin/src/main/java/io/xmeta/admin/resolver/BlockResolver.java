package io.xmeta.admin.resolver;

import io.xmeta.admin.model.Block;
import io.xmeta.admin.model.BlockVersion;
import io.xmeta.admin.model.BlockVersionOrderByInput;
import io.xmeta.admin.model.BlockVersionWhereInput;

/**
 * Resolver for Block
 */
public interface BlockResolver extends graphql.kickstart.tools.GraphQLResolver<Block> {

    @javax.validation.constraints.NotNull
    java.util.List<BlockVersion> versions(Block block, BlockVersionWhereInput where, BlockVersionOrderByInput orderBy, Integer skip, Integer take);

}
