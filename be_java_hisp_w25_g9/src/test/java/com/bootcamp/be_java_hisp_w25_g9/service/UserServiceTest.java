package com.bootcamp.be_java_hisp_w25_g9.service;

import com.bootcamp.be_java_hisp_w25_g9.dto.response.FollowersCountDto;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.MessageDto;
import com.bootcamp.be_java_hisp_w25_g9.exceptions.BadRequestException;
import com.bootcamp.be_java_hisp_w25_g9.exceptions.NotFoundException;
import com.bootcamp.be_java_hisp_w25_g9.model.Client;
import com.bootcamp.be_java_hisp_w25_g9.model.Seller;
import com.bootcamp.be_java_hisp_w25_g9.model.User;
import com.bootcamp.be_java_hisp_w25_g9.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;

    @Test
    void unfollowOK() {
        //ARRANGE
        int idClient = 1;
        int idSeller = 2;
        Client client = new Client(idClient,"TestClient");
        Seller seller = new Seller(idSeller,"TestSeller");
        List<Seller> followedList = client.getFollowed();
        followedList.add(seller);

        MessageDto messageDtoExpected = new MessageDto("El vendedor ha sido quitado de la lista de seguidos del cliente");
        MessageDto messageDtoResult;

        when(userRepository.userExists(anyInt())).thenReturn(true);
        when(userRepository.getUserById(anyInt())).thenReturn(client,seller);

        //ACT
        messageDtoResult = userService.unfollow(idClient,idSeller);

        //ASSERT
        assertEquals(messageDtoExpected,messageDtoResult);
    }
    @Test
    void unfollowMyself() {
        //ARRANGE
        int idClient = 1;
        int idSeller = 1;

        //ACT & ASSERT
        assertThrows(BadRequestException.class,()->userService.unfollow(idClient,idSeller));
    }
    @Test
    void unfollowClientNoExists() {
        //ARRANGE
        int idClient = 1;
        when(userRepository.userExists(idClient)).thenReturn(false);

        //ACT & ASSERT
        assertThrows(BadRequestException.class,()->userService.unfollow(idClient,anyInt()));
    }
    @Test
    void unfollowSellerNoExists() {
        //ARRANGE
        int idSeller = 2;
        when(userRepository.userExists(anyInt())).thenReturn(true,false);

        //ACT & ASSERT
        assertThrows(BadRequestException.class,()->userService.unfollow(anyInt(),idSeller));
    }
    @Test
    void unfollowSellerUnfollowed() {
        //ARRANGE
        int idClient = 1;
        int idSeller = 2;
        Client client = new Client(idClient,"TestClient");
        Seller seller = new Seller(idSeller,"TestSeller");

        when(userRepository.userExists(anyInt())).thenReturn(true);
        when(userRepository.getUserById(anyInt())).thenReturn(client,seller);

        //ACT & ASSERT
        assertThrows(BadRequestException.class,()->userService.unfollow(anyInt(),idSeller));
    }

    @Test
    void getFollowersCount(){
        //ARRANGE
        int idSeller = 4;

        Seller seller = new Seller(idSeller,"TestSeller");
        Client client = new Client(1,"TestClient");
        Client client2 = new Client(2,"TestClient2");
        Client client3 = new Client(3,"TestClient3");

        client.getFollowed().add(seller);
        client2.getFollowed().add(seller);
        client3.getFollowed().add(seller);

        List<User> userList = new ArrayList<>(List.of(seller,client,client2,client3));

        FollowersCountDto followersCountDtoExpected = new FollowersCountDto(idSeller,"TestSeller",3);
        FollowersCountDto followersCountDtoResult;

        when(userRepository.findAll()).thenReturn(userList);

        //ACT
        followersCountDtoResult = userService.getFollowersCount(idSeller);

        //ASSERT
        assertEquals(followersCountDtoExpected,followersCountDtoResult);
    }
    @Test
    void getFollowersCountListIsEmpty(){
        //ARRANGE
        int idSeller = 4;

        Seller seller = new Seller(idSeller,"TestSeller");
        Client client = new Client(1,"TestClient");
        Client client2 = new Client(2,"TestClient2");
        Client client3 = new Client(3,"TestClient3");


        List<User> userList = new ArrayList<>(List.of(seller,client,client2,client3));

        when(userRepository.findAll()).thenReturn(userList);

        //ACT & ASSERT
        assertThrows(NotFoundException.class,()->userService.getFollowersCount(idSeller));
    }
}