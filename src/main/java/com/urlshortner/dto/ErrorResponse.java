package com.urlshortner.dto;

import java.time.LocalDateTime;

public class ErrorResponse {
	private int status;
	private String message;
	private LocalDateTime timestamp;

	public ErrorResponse() {
		super();
	}

	public ErrorResponse(int status, String message) {
		this.status = status;
		this.message = message;
		this.timestamp = LocalDateTime.now();
	}

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}
