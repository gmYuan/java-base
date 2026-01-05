package com.ygm.hotel;


public class ServletConfiguration {
	private String urlPattern;
	private String servletClass;
	private Integer loadOnStartup;

	public ServletConfiguration(String urlPattern, String servletClass, Integer loadOnStartup) {
		this.urlPattern = urlPattern;
		this.servletClass = servletClass;
		this.loadOnStartup = loadOnStartup;
	}

	// getters
	public String getUrlPattern() {
		return urlPattern;
	}

	public String getServletClass() {
		return servletClass;
	}

	public Integer getLoadOnStartup() {
		return loadOnStartup;
	}

	// setters
	public void setUrlPattern(String urlPattern) {
		this.urlPattern = urlPattern;
	}

	public void setServletClass(String servletClass) {
		this.servletClass = servletClass;
	}

	public void setLoadOnStartup(Integer loadOnStartup) {
		this.loadOnStartup = loadOnStartup;
	}


}