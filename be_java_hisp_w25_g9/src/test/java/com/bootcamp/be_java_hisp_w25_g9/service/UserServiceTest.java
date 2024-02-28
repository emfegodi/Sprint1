package com.bootcamp.be_java_hisp_w25_g9.service;

import com.bootcamp.be_java_hisp_w25_g9.dto.UserDto;
import com.bootcamp.be_java_hisp_w25_g9.dto.UserDtoMixIn;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.FollowedDto;
import com.bootcamp.be_java_hisp_w25_g9.exceptions.BadRequestException;
import com.bootcamp.be_java_hisp_w25_g9.model.Client;
import com.bootcamp.be_java_hisp_w25_g9.model.Seller;
import com.bootcamp.be_java_hisp_w25_g9.model.User;
import com.bootcamp.be_java_hisp_w25_g9.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;

    private static Client client;
    private static List<User> userList;
    private static Seller seller;

    @BeforeAll
    public static void setUp() {

        seller = new Seller(1, "a");

        client = new Client(2, "p");
        client.setFollowed(List.of(seller,
                new Seller(3, "b"),
                new Seller(4, "c"),
                new Seller(5, "d"),
                new Seller(6, "e")
        ));

        Client client2 = new Client(3, "p");
        client2.setFollowed(List.of(seller));
        Client client3 = new Client(4, "p");
        client3.setFollowed(List.of(seller));
        Client client4 = new Client(5, "p");
        client4.setFollowed(List.of(seller));

        userList = List.of(client, client2, client3, client4);
    }

    @Test
    void getFollowedAsc() {

        //Arrange
        int clientId = 1;
        String order = "name_asc";

        //Act Assert
        when(userRepository.getUserById(anyInt())).thenReturn(client);
        Assertions.assertNotNull(userService.getFollowed(clientId, order));
        Assertions.assertNotEquals(0, userService.getFollowed(clientId, order).followed().size());
    }

    @Test
    void getFollowedDesc() {

        //Arrange
        int clientId = 1;
        String order = "name_desc";

        //Act Assert
        when(userRepository.getUserById(anyInt())).thenReturn(client);
        Assertions.assertNotNull(userService.getFollowed(clientId, order));
        Assertions.assertNotEquals(0, userService.getFollowed(clientId, order).followed().size());
    }

    @Test
    void getFollowersAsc() {

        //Arrange
        int sellerId = 1;
        String order = "name_asc";
        when(userRepository.getUserById(sellerId)).thenReturn(seller);
        when(userRepository.findAll()).thenReturn(userList);

        //Act Assert
        Assertions.assertNotNull(userService.getFollowers(sellerId, order));
        Assertions.assertNotEquals(0, userService.getFollowers(sellerId, order).followed().size());
        Assertions.assertEquals(4, userService.getFollowers(sellerId, order).followed().size());
    }

    @Test
    void getFollowersDesc() {

        //Arrange
        int sellerId = 1;
        String order = "name_desc";
        when(userRepository.getUserById(sellerId)).thenReturn(seller);
        when(userRepository.findAll()).thenReturn(userList);

        //Act Assert
        Assertions.assertNotNull(userService.getFollowers(sellerId, order));
        Assertions.assertNotEquals(0, userService.getFollowers(sellerId, order).followed().size());
        Assertions.assertEquals(4, userService.getFollowers(sellerId, order).followed().size());
    }

    @Test
    void getErrorOrderName(){

        //Arrange
        int clientId = 1;
        String order = "name";

        //Act Assert
        Assertions.assertThrows(BadRequestException.class, ()->userService.getFollowed(clientId, order));
    }
}