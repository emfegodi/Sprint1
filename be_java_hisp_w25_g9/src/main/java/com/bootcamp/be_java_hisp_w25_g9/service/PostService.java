package com.bootcamp.be_java_hisp_w25_g9.service;

import com.bootcamp.be_java_hisp_w25_g9.dto.request.PostRequestDto;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.FollowedPostsDto;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.MessageDto;
import com.bootcamp.be_java_hisp_w25_g9.repository.interfaces.IPostRepository;
import com.bootcamp.be_java_hisp_w25_g9.service.interfaces.IPostService;
import org.springframework.stereotype.Service;

@Service
public class PostService implements IPostService {
    private IPostRepository postRepository;

    @Override
    public MessageDto createPost(PostRequestDto postRequestDto) {
        return null;
    }

    @Override
    public FollowedPostsDto getPost(int userId) {
        return null;
    }

    @Override
    public FollowedPostsDto getPost(int userId, String order) {
        return null;
    }
}
