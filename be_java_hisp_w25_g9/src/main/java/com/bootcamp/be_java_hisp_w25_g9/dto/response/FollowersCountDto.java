package com.bootcamp.be_java_hisp_w25_g9.dto.response;

public record FollowersCountDto(
    Integer user_id,
    String user_name,
    Integer followers_count
) { }
