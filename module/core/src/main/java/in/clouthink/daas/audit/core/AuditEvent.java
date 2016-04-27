package in.clouthink.daas.audit.core;

import java.io.Serializable;
import java.util.Date;

/**
 */
public interface AuditEvent<T> extends Serializable {

	/**
	 * @return who send the request
	 */
	T getRequestedBy();

	/**
	 * The time to start the request
	 *
	 * @return
	 */
	Date getRequestedAt();

	/**
	 * @return how many time cost by the request
	 */
	long getTimeCost();

	/**
	 * The user agent part of http request
	 *
	 * @return
	 */
	String getUserAgent();

	/**
	 * The method part of http request
	 *
	 * @return GET, POST, PUT, DELETE etc.
	 */
	String getHttpMethod();

	/**
	 * The address part of the http request
	 *
	 * @return
	 */
	String getRequestedUrl();

	String getClientAddress();

	String getForwardedFor();

	/**
	 * @return the spring mvc rest service (controller) name(with package name)
	 */
	String getServiceName();

	/**
	 * @return the method annotated with RequestMapping
	 */
	String getMethodName();

	/**
	 * @return the category of the request
	 */
	String getCategory();

	/**
	 * @return the description of the request
	 */
	String getDescription();

	/**
	 * @return true if any error thrown while processing the request
	 */
	boolean hasError();

	/**
	 * @return the message of the error
	 */
	String getErrorMessage();

	/**
	 * @return the error in detail
	 */
	String getErrorDetail();

}
