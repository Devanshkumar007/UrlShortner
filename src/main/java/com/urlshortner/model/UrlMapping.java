package com.urlshortner.model;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class UrlMapping {

	@SequenceGenerator(name ="gen" , initialValue = 1 , allocationSize = 1 )
	@GeneratedValue(generator = "gen" , strategy = GenerationType.SEQUENCE)
	@Id
	int id;
	String longUrl;
	String shortUrl;
	Date creationDate;
	Date expirationDate;
	int clicks;
	
	public UrlMapping() {
		super();
		// TODO Auto-generated constructor stub
		this.creationDate = Date.valueOf(LocalDate.now());
		this.expirationDate = Date.valueOf(LocalDate.now().plusDays(10));
		this.clicks = 0;
	}
	
	public UrlMapping(int id, String longUrl, String shortUrl) {
		super();
		this.id = id;
		this.longUrl = longUrl;
		this.shortUrl = shortUrl;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLongUrl() {
		return longUrl;
	}
	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}
	public String getshortUrl() {
		return shortUrl;
	}
	public void setshortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public int getClicks() {
		return clicks;
	}
	public void setClicks(int clicks) {
		this.clicks = clicks;
	}
	@Override
	public String toString() {
		return "UrlMapping [id=" + id + ", longUrl=" + longUrl + ", shortUrl=" + shortUrl + ", creationDate="
				+ creationDate + ", expirationDate=" + expirationDate + ", clicks=" + clicks + "]";
	}
	
	
	
	
}
