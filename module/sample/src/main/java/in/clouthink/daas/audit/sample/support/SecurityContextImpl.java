package in.clouthink.daas.audit.sample.support;

import in.clouthink.daas.audit.security.SecurityContext;

/**
 */
public class SecurityContextImpl implements SecurityContext {

	@Override
	public String getPrincipal() {
		return "audit-sample-user";
	}

}
