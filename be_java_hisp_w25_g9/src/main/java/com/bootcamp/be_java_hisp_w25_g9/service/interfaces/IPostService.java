package com.bootcamp.be_java_hisp_w25_g9.service.interfaces;

import com.bootcamp.be_java_hisp_w25_g9.dto.request.PostRequestDto;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.FollowedPostsDto;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.MessageDto;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.PromoProductsCountDto;

import java.util.List;

public interface IPostService {
    MessageDto createPost(PostRequestDto postRequestDto);
    FollowedPostsDto getPost(int userId);
    FollowedPostsDto getPost(int userId, String order);
    MessageDto createPromoPost(PostRequestDto postRequestDto);
    PromoProductsCountDto getPromoPostCount(int userId);
    List<PostRequestDto> getPromoPostList(int userId);
}
