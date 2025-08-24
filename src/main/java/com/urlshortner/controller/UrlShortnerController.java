package com.urlshortner.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.urlshortner.dto.CreateUrlRequest;
import com.urlshortner.dto.ErrorResponse;
import com.urlshortner.model.UrlMapping;
import com.urlshortner.service.UrlShortnerService;

import jakarta.validation.Valid;
import urlshortner.exceptions.CustomUrlTakenException;

@RestController
@RequestMapping("/api/url")
public class UrlShortnerController {

	@Autowired
	UrlShortnerService urlService;
	
	private static final Logger log = LoggerFactory.getLogger(UrlShortnerController.class);
	
	/**
	 * Creates a new short URL for a given long URL.
	 *
	 * @param request A DTO containing the long URL and optional custom short URL.
	 * @return ResponseEntity with the created UrlMapping, or an error response.
	 */
	@PostMapping("/create")
	public ResponseEntity<?> createshortUrl(@Valid @RequestBody CreateUrlRequest request){
		String longUrl = request.getLongUrl();
		String customShortUrl = request.getCustomShortUrl();
		
		log.debug("Attempting to create new short URL for: {}", longUrl);
		
		try {
			// Change: Call the service with both the longUrl and customShortUrl.
			UrlMapping url = urlService.createshortUrl(longUrl, customShortUrl);
			if (url != null) {
				log.info("Successfully created/retrieved short URL: {}", url.getshortUrl());
				return new ResponseEntity<>(url, HttpStatus.CREATED);
			}
			log.warn("Service returned null for long URL: {}", longUrl);
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Could not create short URL."), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.error("Error creating short URL for {}: {}", longUrl, e.getMessage(), e);
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred."), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
    
    // =================================================================
    // NEW: Exception handler for a custom URL that is already taken.
    // This method is automatically called by Spring if the service throws
    // the CustomUrlTakenException.
    // =================================================================
    @ExceptionHandler(CustomUrlTakenException.class)
    public ResponseEntity<ErrorResponse> handleCustomUrlTakenException(CustomUrlTakenException ex) {
        log.warn("Custom URL already taken: {}", ex.getMessage());
        return new ResponseEntity<>(
            new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage()),
            HttpStatus.CONFLICT
        );
    }
    
	@PostMapping("/long")
	public ResponseEntity<?> findbyLongUrl(@Valid @RequestBody CreateUrlRequest request){
		String longUrl = request.getLongUrl();
		log.debug("Fetching URL mapping by long URL: {}", longUrl);
		UrlMapping url = urlService.findByLongUrl(longUrl);
		if (url != null) {
            return new ResponseEntity<>(url, HttpStatus.OK);
        }
		log.info("No mapping found for long URL: {}", longUrl);
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Mapping not found for the given long URL."), HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/short/{shortUrl}")
	public ResponseEntity<?> findbyShortUrl(@PathVariable String shortUrl){
		log.debug("Fetching URL mapping by short URL: {}", shortUrl);
		UrlMapping url = urlService.findByShortUrl(shortUrl);
		if (url != null) {
            return new ResponseEntity<>(url, HttpStatus.OK);
        }
		log.info("No mapping found for short URL: {}", shortUrl);
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Mapping not found for the given short URL."), HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findbyId(@PathVariable int id){
		log.debug("Fetching URL mapping by ID: {}", id);
		UrlMapping url = urlService.findById(id);
		if (url != null) {
            return new ResponseEntity<>(url, HttpStatus.OK);
        }
		log.info("No mapping found for ID: {}", id);
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Mapping not found for the given ID."), HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/all")
    public ResponseEntity<List<UrlMapping>> getAllUrls(){
    	log.debug("Fetching all URL mappings.");
    	List<UrlMapping> UrlMappings = urlService.listall();
    	if (!UrlMappings.isEmpty()) {
            return new ResponseEntity<>(UrlMappings, HttpStatus.OK);
        }
    	log.info("No URL mappings found.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
