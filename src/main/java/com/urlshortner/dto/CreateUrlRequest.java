package com.urlshortner.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class CreateUrlRequest {

    @NotBlank(message = "Long URL cannot be empty")
    @URL(message = "Invalid URL format")
    private String longUrl;
    
    private String customShortUrl;

    public String getCustomShortUrl() {
		return customShortUrl;
	}

	public void setCustomShortUrl(String customShortUrl) {
		this.customShortUrl = customShortUrl;
	}

	public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }
}