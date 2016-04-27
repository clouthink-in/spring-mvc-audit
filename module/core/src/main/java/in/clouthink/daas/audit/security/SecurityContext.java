package in.clouthink.daas.audit.security;


/**
 */
public interface SecurityContext<T> {

	/**
	 * Get current principal which may be the user of audit event
	 *
	 * @return
	 */
	T getPrincipal();

}
