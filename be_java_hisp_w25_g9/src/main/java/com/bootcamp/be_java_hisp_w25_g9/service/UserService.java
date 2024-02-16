package com.bootcamp.be_java_hisp_w25_g9.service;

import com.bootcamp.be_java_hisp_w25_g9.dto.response.FollowedDto;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.FollowersDto;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.FolowersCountDto;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.MessageDto;
import com.bootcamp.be_java_hisp_w25_g9.repository.interfaces.IUserRepository;
import com.bootcamp.be_java_hisp_w25_g9.service.interfaces.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    private IUserRepository userRepository;
    @Override
    public MessageDto follow(int userId, int userIdToFollow) {
        return null;
    }

    @Override
    public MessageDto unfollow(int uerId, int userIdToUnfollow) {
        return null;
    }

    @Override
    public FolowersCountDto getFollowersCount(int userId) {
        return null;
    }

    @Override
    public FollowersDto getFollowers(int userId) {
        return null;
    }

    @Override
    public FollowersDto getFollowers(int userId, String order) {
        return null;
    }

    @Override
    public FollowedDto getFollowed(int userId) {
        return null;
    }

    @Override
    public FollowedDto getFollowed(int userId, String order) {
        return null;
    }
}
