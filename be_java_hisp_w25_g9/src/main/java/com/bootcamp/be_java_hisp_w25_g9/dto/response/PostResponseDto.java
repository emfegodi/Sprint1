package com.bootcamp.be_java_hisp_w25_g9.dto.response;

import com.bootcamp.be_java_hisp_w25_g9.dto.ProductDto;

import java.time.LocalDate;

public record PostResponseDto(
        int user_id,
        int post_id,
        LocalDate date,
        ProductDto product,
        int category,
        double price
) {
}
