package com.urlshortner.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller; // Use @Controller for view/redirect logic
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;

import com.urlshortner.model.UrlMapping;
import com.urlshortner.service.UrlShortnerService;

@Controller
public class RedirectController {

    @Autowired
    private UrlShortnerService urlService;

    private static final Logger log = LoggerFactory.getLogger(RedirectController.class);

    /**
     * Handles requests to the short URL and redirects to the original long URL.
     * This is the core redirection functionality of the URL shortener.
     *
     * @param shortCode The short URL code provided in the path (e.g., /abc123).
     * @return A RedirectView to the original long URL, or redirects to a 404 page if not found.
     */
    @GetMapping("/{shortCode}")
    public RedirectView redirectToLongUrl(@PathVariable String shortCode) {
        log.debug("Attempting to redirect for short code: {}", shortCode);
        String longUrl = null;
        try {
            // Retrieve the UrlMapping object from the service based on the short code.
            UrlMapping mapping = urlService.findByShortUrl(shortCode);
            if (mapping != null) {
                longUrl = mapping.getLongUrl();
            }
        } catch (Exception e) {
            log.error("Error retrieving long URL for short code {}: {}", shortCode, e.getMessage(), e);
        }

        if (longUrl != null) {
            log.info("Redirecting short code {} to long URL: {}", shortCode, longUrl);
            RedirectView redirectView = new RedirectView(longUrl);
            // Use 301 Moved Permanently for better SEO, as the mapping is permanent.
            redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
            return redirectView;
        } else {
            log.warn("Short URL not found: {}", shortCode);
            // If the short code is not found, redirect to a 404 Not Found page.
            // Spring Boot will handle mapping "/error" to a default 404 page.
            RedirectView redirectView = new RedirectView("/error");
            redirectView.setStatusCode(HttpStatus.NOT_FOUND);
            return redirectView;
        }
    }
}