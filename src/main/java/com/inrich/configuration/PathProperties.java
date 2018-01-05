package com.inrich.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix = "path", ignoreUnknownFields = false)
@PropertySource("classpath:config/path.properties")
@Component
public class PathProperties {
	private String errorTxt;
	private String images;

	public String getErrorTxt() {
		return errorTxt;
	}

	public void setErrorTxt(String errorTxt) {
		this.errorTxt = errorTxt;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

}
