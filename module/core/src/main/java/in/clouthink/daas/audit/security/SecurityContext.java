package in.clouthink.daas.audit.security;


/**
 */
public interface SecurityContext {

	/**
	 * Get current principal which as the username of audit entry
	 *
	 * @return
	 */
	String getPrincipal();

}
