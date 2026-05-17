package com.url.shortner.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClickEventDTO {
    private LocalDate clickDate;
    private Long count;
}
