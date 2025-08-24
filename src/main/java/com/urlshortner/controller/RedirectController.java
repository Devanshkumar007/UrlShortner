package com.urlshortner.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller; // Use @Controller for view/redirect logic
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import com.urlshortner.model.UrlMapping;
import com.urlshortner.service.UrlShortnerService;

import jakarta.transaction.Transactional;

@Controller
@RequestMapping("/s") // This is the key: All short URLs will start with this prefix.
public class RedirectController {

    @Autowired
    private UrlShortnerService urlService;
    

    private static final Logger log = LoggerFactory.getLogger(RedirectController.class);

    /**
     * Handles requests for short URLs and redirects to the original long URL.
     * Example URL: http://localhost:9191/s/{shortCode}
     *
     * @param shortCode The short code (e.g., "choti.GitHubDevansh").
     * @return a RedirectView to the original long URL.
     */
    @GetMapping("/{shortCode}")
    public RedirectView redirectToLongUrl(@PathVariable String shortCode) {
        log.debug("Attempting to redirect for short code: {}", shortCode);
        
        // The service needs the full short URL to find it in the database.
        String fullShortUrl = shortCode; 
        
        String longUrl = null;
        try {
            // Find the URL mapping in the database.
            UrlMapping mapping = urlService.getAndIncrementClicks(fullShortUrl);
            if (mapping != null) {
                longUrl = mapping.getLongUrl();
            }
        } catch (Exception e) {
            log.error("Error retrieving long URL for short code {}: {}", shortCode, e.getMessage(), e);
        }

        if (longUrl != null) {
            log.info("Redirecting short code {} to long URL: {}", shortCode, longUrl);
            RedirectView redirectView = new RedirectView(longUrl);
            redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
            return redirectView;
        } else {
            log.warn("Short URL not found: {}", shortCode);
            // Redirect to a 404 page if the URL is not found.
            RedirectView redirectView = new RedirectView("/error");
            redirectView.setStatusCode(HttpStatus.NOT_FOUND);
            return redirectView;
        }
    }
}
