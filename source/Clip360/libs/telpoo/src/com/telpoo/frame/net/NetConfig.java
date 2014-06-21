package com.telpoo.frame.net;

public class NetConfig {

	private final String contentType;
	private final String userAgent;
	private final int connectTimeout;
	private final int soTimeout;
	private final String authorization;

	private NetConfig(Builder builder) {
		contentType = builder.contentType;
		userAgent = builder.userAgent;
		connectTimeout = builder.connectTimeout;
		soTimeout = builder.soTimeout;
		authorization = builder.authorization;
	}

	public String getContentType() {
		return contentType;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public int getSoTimeout() {
		return soTimeout;
	}

	public String getAuthorization() {
		return authorization;
	}

	public NetConfig createDefault() {
		return new Builder().build();
	}

	public static class Builder {
		private int connectTimeout = 15000;
		private int soTimeout = 10000;
		private String authorization = null;
		private String contentType = "application/json";
		private String userAgent = "Mozilla/5.0 (Linux; Android 4.1.1; Nexus 7 Build/JRO03D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Safari/535.19";

		public Builder() {

		}

		public Builder connectTimeout(int connectTimeout) {
			this.connectTimeout = connectTimeout;
			return this;
		}

		public Builder soTimeout(int soTimeout) {
			this.soTimeout = soTimeout;
			return this;
		}

		public Builder authorization(String authorization) {
			this.authorization = authorization;
			return this;
		}

		public Builder contentType(String contentType) {
			this.contentType = contentType;
			return this;
		}

		public Builder userAgent(String userAgent) {
			this.userAgent = userAgent;
			return this;
		}

		public Builder cloneFrom(NetConfig option) {
			connectTimeout = option.getConnectTimeout();
			soTimeout = option.soTimeout;
			authorization = option.authorization;
			contentType = option.contentType;
			userAgent = option.userAgent;
			return this;

		}

		public NetConfig build() {
			return new NetConfig(this);
		}

	}

}
