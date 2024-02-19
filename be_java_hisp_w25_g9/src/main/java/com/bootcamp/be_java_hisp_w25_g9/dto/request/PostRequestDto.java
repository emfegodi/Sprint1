package com.bootcamp.be_java_hisp_w25_g9.dto.request;

import com.bootcamp.be_java_hisp_w25_g9.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record PostRequestDto(
        int user_id,
        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate date,
        ProductDto product,
        int category,
        double price
) {
}
