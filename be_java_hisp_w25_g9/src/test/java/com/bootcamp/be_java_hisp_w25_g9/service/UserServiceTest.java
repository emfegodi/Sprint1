package com.bootcamp.be_java_hisp_w25_g9.service;

import com.bootcamp.be_java_hisp_w25_g9.dto.response.FollowersCountDto;
import com.bootcamp.be_java_hisp_w25_g9.exceptions.BadRequestException;
import com.bootcamp.be_java_hisp_w25_g9.exceptions.NotFoundException;
import com.bootcamp.be_java_hisp_w25_g9.model.Client;
import com.bootcamp.be_java_hisp_w25_g9.model.Seller;
import com.bootcamp.be_java_hisp_w25_g9.model.User;
import com.bootcamp.be_java_hisp_w25_g9.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.MessageDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import java.util.ArrayList;
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
    void followOk() {

        //Arrange
        int id = 1;
        int idToFollow = 26;
        Client testUser = new Client(id, "TestUser");
        Seller testSeller = new Seller(id, "TestSeller");
        when(userRepository.userExists(anyInt())).thenReturn(true, true);
        when(userRepository.getUserById(any())).thenReturn(testSeller, testUser, testSeller);
        doNothing().when(userRepository).save(any());
        MessageDto expected = new MessageDto("Vendedor seguido con éxito");
        //Act
        MessageDto result = userService.follow(id, idToFollow);
        //Assert
        assertEquals(expected, result);

    }

    @Test
    void followAlreadyFollowed() {

        //Arrange
        int id = 1;
        int idToFollow = 26;
        Client testUser = new Client(id, "TestUser");
        Seller testSeller = new Seller(idToFollow, "TestSeller");
        testUser.getFollowed().add(testSeller);
        when(userRepository.userExists(anyInt())).thenReturn(true, true);
        when(userRepository.getUserById(any())).thenReturn(testSeller, testUser, testSeller);
        //Act Assert
        assertThrows(BadRequestException.class, () -> userService.follow(id, idToFollow));

    }

    @Test
    void followSellerNotClient() {

        //Arrange
        int id = 1;
        int idToFollow = 26;
        Client testUser = new Client(id, "TestUser");
        when(userRepository.userExists(anyInt())).thenReturn(true, true);
        when(userRepository.getUserById(any())).thenReturn(testUser);
        //Act Assert
        assertThrows(BadRequestException.class, () -> userService.follow(id, idToFollow));

    }

    @Test
    void followSameIdBothParams() {

        //Arrange
        int id = 1;
        int idToFollow = 1;
        //Act Assert
        assertThrows(BadRequestException.class,() -> userService.follow(id, idToFollow));

    }

    @Test
    void followFollowerNotFound() {

        //Arrange
        int id = 1;
        int idToFollow = 26;
        when(userRepository.userExists(anyInt())).thenReturn(false);
        //Act Assert
        assertThrows(BadRequestException.class,() -> userService.follow(id, idToFollow));

    }

    @Test
    void followFollowedNotFound() {

        //Arrange
        int id = 1;
        int idToFollow = 26;
        when(userRepository.userExists(anyInt())).thenReturn(true, false);
        //Act Assert
        assertThrows(BadRequestException.class,() -> userService.follow(id, idToFollow));
    }

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

        Seller seller = new Seller(idSeller, "TestSeller");
        Client client = new Client(1, "TestClient");
        Client client2 = new Client(2, "TestClient2");
        Client client3 = new Client(3, "TestClient3");

        client.getFollowed().add(seller);
        client2.getFollowed().add(seller);
        client3.getFollowed().add(seller);

        List<User> userList = new ArrayList<>(List.of(seller, client, client2, client3));

        FollowersCountDto followersCountDtoExpected = new FollowersCountDto(idSeller, "TestSeller", 3);
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