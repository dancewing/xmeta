package io.xmeta.security.auth;


import org.springframework.data.domain.AuditorAware;
import io.xmeta.security.SecurityUtils;

import java.util.Optional;

/**
 * Implementation of {@link AuditorAware} based on Spring Security.
 */
public class SpringSecurityAuditorAware implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		return Optional.of(SecurityUtils.getCurrentUserLogin().orElse("system"));
	}
}
