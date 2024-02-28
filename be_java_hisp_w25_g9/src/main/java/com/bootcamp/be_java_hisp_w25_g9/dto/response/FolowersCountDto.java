package com.bootcamp.be_java_hisp_w25_g9.dto.response;

public record FolowersCountDto(
    Integer user_id,
    String user_name,
    Integer followers_count
) { }
