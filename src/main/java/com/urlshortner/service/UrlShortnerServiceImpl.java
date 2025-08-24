
package com.urlshortner.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.urlshortner.dao.UrlRepository;
import com.urlshortner.model.UrlMapping;
import urlshortner.exceptions.CustomUrlTakenException;


@Service
public class UrlShortnerServiceImpl implements UrlShortnerService{

	@Autowired
	UrlRepository urlRepo;
	
	public String shorturl(String longUrl) {
		int first = longUrl.indexOf(".");
		int second = longUrl.lastIndexOf(".");
		String small = longUrl.substring(first+1, second);
		return "choti." + small;
	}
	
	@Override
	@Transactional
	public UrlMapping createshortUrl(String longUrl, String customShortUrl) {

		if (customShortUrl != null && !customShortUrl.isBlank()) {
			String fullCustomUrl = "choti." + customShortUrl;
			// Check the database to see if this specific URL is taken.
			if (urlRepo.findByShortUrl(fullCustomUrl).isPresent()) {
				throw new CustomUrlTakenException("The custom short URL '" + customShortUrl + "' is already in use. Please choose another one.");
			} else {
				UrlMapping url = new UrlMapping();
				url.setLongUrl(longUrl);
				url.setshortUrl(fullCustomUrl);
				urlRepo.save(url);
				return url;
			}
		}

		UrlMapping check = urlRepo.findByLongUrl(longUrl).orElse(null);
		if(check!=null) return check;
		
		String small = shorturl(longUrl);
		List<UrlMapping> ls = urlRepo.startingWith(small);
		String finalShortUrl = ls.size() == 0 ? small : small + ls.size();
		
		UrlMapping url = new UrlMapping();
		url.setLongUrl(longUrl);
		url.setshortUrl(finalShortUrl);
		urlRepo.save(url);
		return url;
	}

	@Override
	public UrlMapping findById(int id) {
		UrlMapping obj = urlRepo.findById(id).orElse(null);
		return obj;
	}

	@Override
	public UrlMapping findByShortUrl(String shortUrl) {
		UrlMapping url = urlRepo.findByShortUrl(shortUrl).orElse(null);
		return url;
	}

	@Override
	public UrlMapping findByLongUrl(String longUrl) {
		UrlMapping url = urlRepo.findByLongUrl(longUrl).orElse(null);
		return url;
	}

	@Override
	public List<UrlMapping> listall() {
		return urlRepo.findAll();
	}

	@Override
	@Transactional
	public Boolean deleteUrl(int id) {
		UrlMapping curr = urlRepo.findById(id).orElse(null);
		if(curr==null) return false;
		urlRepo.delete(curr);
		return true;
	}
	
	@Override
	@Transactional
	public UrlMapping getAndIncrementClicks(String shortUrl) {
	    UrlMapping mapping = urlRepo.findByShortUrl(shortUrl).orElse(null);
	    if (mapping != null) {
	        mapping.setClicks(mapping.getClicks() + 1);
	        return mapping;
	    }
	    return null;
	}
}
