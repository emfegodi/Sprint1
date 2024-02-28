package com.bootcamp.be_java_hisp_w25_g9.service;

import com.bootcamp.be_java_hisp_w25_g9.dto.response.MessageDto;
import com.bootcamp.be_java_hisp_w25_g9.exceptions.BadRequestException;
import com.bootcamp.be_java_hisp_w25_g9.model.Client;
import com.bootcamp.be_java_hisp_w25_g9.model.Seller;
import com.bootcamp.be_java_hisp_w25_g9.repository.UserRepository;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

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

}