package io.xmeta.graphql.resolver.impl;

import io.xmeta.graphql.model.Auth;
import io.xmeta.graphql.model.SignupInput;
import io.xmeta.graphql.resolver.SignupMutationResolver;
import org.springframework.stereotype.Component;

@Component
public class SignupMutationResolverImpl implements SignupMutationResolver {
    @Override
    public Auth signup(SignupInput data) throws Exception {
        return null;
    }
}
