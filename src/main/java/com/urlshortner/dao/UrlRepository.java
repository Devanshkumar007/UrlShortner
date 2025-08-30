package com.urlshortner.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.urlshortner.model.UrlMapping;

@Repository
public interface UrlRepository extends JpaRepository<UrlMapping, Integer> {
    // Spring Data JPA automatically provides CRUD operations (create, read, update, delete)
    // You can add custom query methods here if needed, e.g.:
    	
	@Query("SELECT u FROM UrlMapping u WHERE u.shortUrl = :shortUrl AND u.status = true")
	Optional<UrlMapping> findByShortUrl(String shortUrl);
     
    @Query("SELECT u FROM UrlMapping u WHERE u.longUrl = :longUrl")
    Optional<UrlMapping> findByLongUrl(String longUrl);
    
    @Query("SELECT um FROM UrlMapping um WHERE um.shortUrl LIKE :prefix%")
    List<UrlMapping> startingWith(@Param("prefix") String prefix);
}