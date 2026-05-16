package com.url.shortner.Service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.url.shortner.DTO.UrlMappingDTO;
import com.url.shortner.Model.URLMapping;
import com.url.shortner.Model.User;
import com.url.shortner.Repository.UrlRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UrlService {
    private UrlRepo urlRepo;

    public UrlMappingDTO createShortUrl(String OriginialUrl, User user) {
        String shortUrl = generate();
        URLMapping urlMapping = new URLMapping();
        urlMapping.setUser(user);
        urlMapping.setCreatedDate(LocalDateTime.now());
        urlMapping.setOriginalUrl(OriginialUrl);
        urlMapping.setShortUrl(shortUrl);
        URLMapping savedurl = urlRepo.save(urlMapping);
        return converttDto(savedurl);
    }

    private UrlMappingDTO converttDto(URLMapping urlMapping) {
        UrlMappingDTO dto = new UrlMappingDTO();
        dto.setUrlId(urlMapping.getUrlId());
        dto.setShortUrl(urlMapping.getShortUrl());
        dto.setOriginalUrl(urlMapping.getOriginalUrl());
        dto.setUsername(urlMapping.getUser().getUsername());
        dto.setCreatedDate(urlMapping.getCreatedDate());
        return dto;
    }

    public String generate() {
        String character = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder shortUrl = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            shortUrl.append(character.charAt(random.nextInt(character.length())));
        }
        return shortUrl.toString();

    }
}
