package com.bootcamp.be_java_hisp_w25_g9.dto.response;

public record PromoProductsCountDto(
        int user_id,
        String user_name,
        int promo_products_account
) {}