package com.bootcamp.be_java_hisp_w25_g9.dto.response;

import com.bootcamp.be_java_hisp_w25_g9.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PostResponseDto(
        int post_id,
        int user_id,
        LocalDate date,
        ProductDto product,
        int category,
        double price
) {
}
