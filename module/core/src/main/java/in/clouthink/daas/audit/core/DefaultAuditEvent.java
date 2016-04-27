package in.clouthink.daas.audit.core;

import java.util.Date;

/**
 * The default impl for MutableAuditEvent
 */
public class DefaultAuditEvent implements MutableAuditEvent {

	Object requestedBy;

	Date requestedAt;

	String userAgent;

	String httpMethod;

	String requestedUrl;

	String clientAddress;

	String xForwardedFor;

	String serviceName;

	String methodName;

	String category;

	String description;

	long timeCost;

	boolean error;

	String errorMessage;

	String errorDetail;

	@Override
	public Object getRequestedBy() {
		return requestedBy;
	}

	@Override
	public void setRequestedBy(Object requestedBy) {
		this.requestedBy = requestedBy;
	}

	@Override
	public Date getRequestedAt() {
		return requestedAt;
	}

	@Override
	public void setRequestedAt(Date requestedAt) {
		this.requestedAt = requestedAt;
	}

	@Override
	public String getUserAgent() {
		return userAgent;
	}

	@Override
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	@Override
	public String getHttpMethod() {
		return httpMethod;
	}

	@Override
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	@Override
	public String getRequestedUrl() {
		return requestedUrl;
	}

	@Override
	public void setRequestedUrl(String requestedUrl) {
		this.requestedUrl = requestedUrl;
	}

	@Override
	public String getClientAddress() {
		return clientAddress;
	}

	@Override
	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	@Override
	public String getForwardedFor() {
		return xForwardedFor;
	}

	@Override
	public void setForwardedFor(String xForwardedFor) {
		this.xForwardedFor = xForwardedFor;
	}

	@Override
	public String getServiceName() {
		return serviceName;
	}

	@Override
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	@Override
	public String getMethodName() {
		return methodName;
	}

	@Override
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	@Override
	public String getCategory() {
		return category;
	}

	@Override
	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public long getTimeCost() {
		return timeCost;
	}

	@Override
	public void setTimeCost(long timeCost) {
		this.timeCost = timeCost;
	}

	@Override
	public boolean hasError() {
		return error;
	}

	public boolean isError() {
		return error;
	}

	@Override
	public void setError(boolean error) {
		this.error = error;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String getErrorDetail() {
		return errorDetail;
	}

	@Override
	public void setErrorDetail(String errorDetail) {
		this.errorDetail = errorDetail;
	}

	@Override
	public String toString() {
		return "DefaultAuditEvent{" +
			   "requestedBy='" + requestedBy + '\'' +
			   ", requestedAt=" + requestedAt +
			   ", userAgent='" + userAgent + '\'' +
			   ", httpMethod='" + httpMethod + '\'' +
			   ", requestedUrl='" + requestedUrl + '\'' +
			   ", clientAddress='" + clientAddress + '\'' +
			   ", xForwardedFor='" + xForwardedFor + '\'' +
			   ", controllerName='" + serviceName + '\'' +
			   ", methodName='" + methodName + '\'' +
			   ", category='" + category + '\'' +
			   ", description='" + description + '\'' +
			   ", timeCost=" + timeCost +
			   ", error=" + error +
			   ", errorMessage='" + errorMessage + '\'' +
			   ", errorDetail='" + errorDetail + '\'' +
			   '}';
	}
}
