package com.urlshortner.service;
import java.util.List;

import com.urlshortner.model.UrlMapping;

public interface UrlShortnerService {
	UrlMapping createshortUrl(String longUrl, String customShortUrl);
	UrlMapping findById(int id);
	UrlMapping findByShortUrl(String shortUrl);
	UrlMapping findByLongUrl(String longUrl);
	List<UrlMapping> listall();
	Boolean deleteUrl(int id);

}