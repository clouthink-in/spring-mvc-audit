package in.clouthink.daas.audit.core;

import java.util.Date;

/**
 */
public interface MutableAuditEvent extends AuditEvent {

	void setRequestedBy(String requestedBy);

	void setRequestedAt(Date requestedAt);

	void setTimeCost(long timeCost);

	void setUserAgent(String userAgent);

	void setHttpMethod(String httpMethod);

	void setRequestedUrl(String requestedUrl);

	void setClientAddress(String clientAddress);

	void setForwardedFor(String xForwardedFor);

	void setServiceName(String serviceName);

	void setMethodName(String methodName);

	void setCategory(String category);

	void setDescription(String description);

	void setError(boolean error);

	void setErrorMessage(String errorMessage);

	void setErrorDetail(String errorDetail);

}
