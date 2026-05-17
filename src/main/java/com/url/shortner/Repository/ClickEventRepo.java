package com.url.shortner.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.url.shortner.Model.Clickevent;
import com.url.shortner.Model.URLMapping;

@Repository
public interface ClickEventRepo extends JpaRepository<Clickevent, Long> {
    List<Clickevent> findByUrlMappingAndClickDateBetween(URLMapping mapping, LocalDateTime start, LocalDateTime end);

    List<Clickevent> findByUrlMappingInAndClickDateBetween(List<URLMapping> mapping, LocalDateTime start,
            LocalDateTime end);

}
