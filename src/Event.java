import com.fasterxml.jackson.annotation.JsonProperty;

public class Event {
	@JsonProperty("Ip")
	String ip;

	@JsonProperty("CreatedAt")
	long createdAt;

	@JsonProperty("Agent")
	String agent;

	@JsonProperty("Uuid")
	String uuid;

	@JsonProperty("Referrer")
	String referrer;

	@JsonProperty("Url")
	String url;
	@JsonProperty("Metric")
	String metric;

	@JsonProperty("ProductId")
	String productId;

	@JsonProperty("VideoId")
	String videoId;

	@JsonProperty("OrderId")
	long orderId;

	@JsonProperty("CustomerId")
	long customerId;

	public String getIp() {
		return ip;
	}

	public String getSqlIp() {
		return "'" + ip + "'";
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

	public String getAgent() {
		return agent;
	}

	public String getSqlAgent() {
		return "'" + agent + "'";
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getUuid() {
		return uuid;
	}

	public String getSqlUuid() {
		return "'" + uuid + "'";
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getReferrer() {
		return referrer;
	}

	public String getSqlReferrer() {
		return "'" + referrer + "'";
	}

	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}

	public String getUrl() {
		return url;
	}

	public String getSqlUrl() {
		return "'" + url + "'";
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMetric() {
		return metric;
	}

	public String getSqlMetric() {
		return "'" + metric + "'";
	}

	public void setMetric(String metric) {
		this.metric = metric;
	}

	public String getProductId() {
		return productId;
	}

	public String getSqlProductId() {
		return "'" + productId + "'";
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getVideoId() {
		return videoId;
	}

	public String getSqlVideoId() {
		return "'" + videoId + "'";
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

}
