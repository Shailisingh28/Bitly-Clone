package com.url.shortner.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.url.shortner.Model.URLMapping;
import com.url.shortner.Model.User;

@Repository
public interface UrlRepo extends JpaRepository<URLMapping, Long> {
    URLMapping findByShortUrl(String shortUrl);

    List<URLMapping> findByUser(User user);
}
