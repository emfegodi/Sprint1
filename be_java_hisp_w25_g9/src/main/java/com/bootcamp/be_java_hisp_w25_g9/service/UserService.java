package com.bootcamp.be_java_hisp_w25_g9.service;

import com.bootcamp.be_java_hisp_w25_g9.dto.UserDto;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.FollowedDto;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.FollowersDto;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.FolowersCountDto;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.MessageDto;
import com.bootcamp.be_java_hisp_w25_g9.exceptions.NoUsersFoundException;
import com.bootcamp.be_java_hisp_w25_g9.model.Seller;
import com.bootcamp.be_java_hisp_w25_g9.model.User;
import com.bootcamp.be_java_hisp_w25_g9.repository.interfaces.IUserRepository;
import com.bootcamp.be_java_hisp_w25_g9.service.interfaces.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

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

        List<User> users = userRepository.findAll();
        if (users == null || users.isEmpty()) throw new NoUsersFoundException("There are no users in the repository");

        User sellerReceived = users.stream().filter(user -> user.getUserId() == userId).toList().get(0);
        if (sellerReceived == null) throw new NoUsersFoundException("The seller was not found");

        List<UserDto> followers = users.stream()
                .filter(user -> user.getFollowed().stream().anyMatch(seller -> seller.getUserId() == userId))
                .map(user -> new UserDto(user.getUserId(), user.getUserName()))
                .toList();

        if (followers.isEmpty()) throw new NoUsersFoundException("The seller does not have followers");

        return new FollowersDto(
                userId,
                sellerReceived.getUserName(),
                followers
        );
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
