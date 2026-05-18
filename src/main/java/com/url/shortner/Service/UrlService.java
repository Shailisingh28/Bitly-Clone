package com.url.shortner.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.url.shortner.DTO.ClickEventDTO;
import com.url.shortner.DTO.UrlMappingDTO;
import com.url.shortner.Model.Clickevent;
import com.url.shortner.Model.URLMapping;
import com.url.shortner.Model.User;
import com.url.shortner.Repository.ClickEventRepo;
import com.url.shortner.Repository.UrlRepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UrlService {
    private UrlRepo urlRepo;
    private ClickEventRepo eventrepo;

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

    public List<UrlMappingDTO> getUrlByUser(User user) {
        return urlRepo.findByUser(user).stream().map(this::converttDto).toList();
    }

    public List<ClickEventDTO> getEventByDate(String shortUrl, LocalDateTime start, LocalDateTime end) {
        URLMapping urlMapping = urlRepo.findByShortUrl(shortUrl);
        if (urlMapping != null) {
            return eventrepo.findByUrlMappingAndClickDateBetween(urlMapping, start, end).stream()
                    .collect(Collectors.groupingBy(click -> click.getClickDate().toLocalDate(), Collectors.counting()))
                    .entrySet().stream().map(entry -> {
                        ClickEventDTO clickEventDTO = new ClickEventDTO();
                        clickEventDTO.setClickDate(entry.getKey());
                        clickEventDTO.setCount(entry.getValue());
                        return clickEventDTO;
                    })
                    .collect(Collectors.toList());
        }
        return null;
    }

    public Map<LocalDate, Long> getTotalClicksByUserAndDate(User user, LocalDate start, LocalDate end) {
        List<URLMapping> urlMappings = urlRepo.findByUser(user);
        List<Clickevent> clickevents = eventrepo.findByUrlMappingInAndClickDateBetween(urlMappings,
                start.atStartOfDay(), end.plusDays(1).atStartOfDay());
        return clickevents.stream()
                .collect(Collectors.groupingBy(click -> click.getClickDate().toLocalDate(), Collectors.counting()));
    }

    public URLMapping getOriginalUrl(String shortUrl) {
        URLMapping urlMapping = urlRepo.findByShortUrl(shortUrl);
        if (urlMapping != null) {
            urlMapping.setClick_count(urlMapping.getClick_count() + 1);
            urlRepo.save(urlMapping);

            Clickevent event = new Clickevent();
            event.setClickDate(LocalDateTime.now());
            event.setUrlMapping(urlMapping);
            eventrepo.save(event);
        }

        return urlMapping;
    }

}
