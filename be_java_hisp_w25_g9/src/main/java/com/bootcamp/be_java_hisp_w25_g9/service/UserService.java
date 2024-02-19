package com.bootcamp.be_java_hisp_w25_g9.service;

import com.bootcamp.be_java_hisp_w25_g9.dto.response.FollowedDto;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.FollowersDto;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.FolowersCountDto;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.MessageDto;
import com.bootcamp.be_java_hisp_w25_g9.exceptions.*;
import com.bootcamp.be_java_hisp_w25_g9.model.*;
import com.bootcamp.be_java_hisp_w25_g9.repository.interfaces.IUserRepository;
import com.bootcamp.be_java_hisp_w25_g9.service.interfaces.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    private IUserRepository userRepository;
    @Override
    public MessageDto follow(int userId, int userIdToFollow) {
        if(userId == userIdToFollow)
            throw new BadRequestException("El usuario no puede seguirse asi mismo");

        if(userRepository.userExists(userId))
            throw new BadRequestException("El cliente no existe");
        if(userRepository.userExists(userIdToFollow))
            throw new BadRequestException("El vendedor no existe");

        Client client = (Client) userRepository.getUserById(userId);
        Seller seller = (Seller) userRepository.getUserById(userIdToFollow);

        List<Seller> followedList = client.getFollowed();
        Optional<Seller> sellerFollowed = followedList.stream().filter(u -> u.getUserId()==userIdToFollow).findFirst();
        if(sellerFollowed.isEmpty())
            throw new BadRequestException("No se puede seguir a un vendedor que ya se esta siguiendo");
        followedList.add(seller);

        userRepository.save(client);

        return new MessageDto("Vendedor seguido con exito");
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
