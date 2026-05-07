package com.url.shortner.Model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class URLMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UrlId;
    private int click_count = 0;
    private String original_URL;
    private String short_URL;
    @CreatedDate
    private LocalDateTime createdDate;
    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;
    @OneToMany(mappedBy = "urlMapping")
    private List<Clickevent> clickevents;
}
