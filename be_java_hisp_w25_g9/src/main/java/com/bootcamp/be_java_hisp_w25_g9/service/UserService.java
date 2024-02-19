package com.bootcamp.be_java_hisp_w25_g9.service;

import com.bootcamp.be_java_hisp_w25_g9.dto.UserDto;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.FollowedDto;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.FollowersDto;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.FolowersCountDto;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.MessageDto;
import com.bootcamp.be_java_hisp_w25_g9.exceptions.NoUsersFoundException;
import com.bootcamp.be_java_hisp_w25_g9.model.Seller;
import com.bootcamp.be_java_hisp_w25_g9.model.User;
import com.bootcamp.be_java_hisp_w25_g9.exceptions.BadRequestException;
import com.bootcamp.be_java_hisp_w25_g9.exceptions.*;
import com.bootcamp.be_java_hisp_w25_g9.model.Seller;
import com.bootcamp.be_java_hisp_w25_g9.repository.interfaces.IUserRepository;
import com.bootcamp.be_java_hisp_w25_g9.service.interfaces.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import com.bootcamp.be_java_hisp_w25_g9.model.Client;
import com.bootcamp.be_java_hisp_w25_g9.dto.UserDtoMixIn;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    ObjectMapper mapper = new ObjectMapper();

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
        mapper.addMixIn(Client.class, UserDtoMixIn.class);
        mapper.addMixIn(Seller.class, UserDtoMixIn.class);
    }

    @Override
    public MessageDto follow(int userId, int userIdToFollow) {
        if(userId == userIdToFollow)
            throw new BadRequestException("El usuario no puede seguirse a sí mismo");

        if(!userRepository.userExists(userId))
            throw new BadRequestException("El cliente no existe");
        if(!userRepository.userExists(userIdToFollow))
            throw new BadRequestException("El vendedor no existe");

        Client client = (Client) userRepository.getUserById(userId);
        Seller seller = (Seller) userRepository.getUserById(userIdToFollow);

        List<Seller> followedList = client.getFollowed();
        Optional<Seller> sellerFollowed = followedList.stream().filter(u -> u.getUserId()==userIdToFollow).findFirst();
        if(sellerFollowed.isPresent())
            throw new BadRequestException("No se puede seguir a un vendedor que ya se esta siguiendo");
        followedList.add(seller);

        userRepository.save(client);

        return new MessageDto("Vendedor seguido con exito");
    }

    @Override
    public MessageDto unfollow(int userId, int userIdToUnfollow) {
        if (userId == userIdToUnfollow)
            throw new BadRequestException("El usuario no puede dejar de seguirse a sí mismo");
        if(!userRepository.userExists(userId))
            throw new BadRequestException("El cliente no existe");
        if(!userRepository.userExists(userIdToUnfollow))
            throw new BadRequestException("El vendedor no existe");

        Client client = (Client) userRepository.getUserById(userId);
        Seller seller = (Seller) userRepository.getUserById(userIdToUnfollow);

        List<Seller> followedList = client.getFollowed();
        Optional<Seller> sellerFollowed = followedList.stream().filter(u -> u.getUserId() == userIdToUnfollow).findFirst();

        if(!sellerFollowed.isPresent())
            throw new BadRequestException("El vendedor no estaba en la lista de seguidos del cliente");

        followedList.remove(seller);

        return new MessageDto("El vendedor ha sido quitado de la lista de seguidos del cliente");
    }

    @Override
    public FolowersCountDto getFollowersCount(int userId) {

        List<Seller> sellerList = userRepository.findAll().stream()
                .flatMap(u -> u.getFollowed().stream()
                        .filter(s -> s.getUserId() == userId)
                )
                .toList();

        if (sellerList.isEmpty()) throw new NotFoundException("Vendedor no encontrado");
        int count = sellerList.size();

        return new FolowersCountDto(userId, sellerList.get(0).getUserName(), count);
    }

    @Override
    public FollowersDto getFollowers(int userId) {

        List<User> users = userRepository.findAll();
        if (users == null || users.isEmpty()) throw new NoUsersFoundException("There are no users in the repository");

        List<User> sellerReceived = users.stream().filter(user -> user.getUserId() == userId).toList();
        if (sellerReceived.isEmpty()) throw new NoUsersFoundException("The seller was not found");

        List<UserDto> followers = users.stream()
                .filter(user -> user.getFollowed().stream().anyMatch(seller -> seller.getUserId() == userId))
                .map(user -> new UserDto(user.getUserId(), user.getUserName()))
                .toList();

        if (followers.isEmpty()) throw new NoUsersFoundException("The seller does not have followers");

        return new FollowersDto(
                userId,
                sellerReceived.get(0).getUserName(),
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
