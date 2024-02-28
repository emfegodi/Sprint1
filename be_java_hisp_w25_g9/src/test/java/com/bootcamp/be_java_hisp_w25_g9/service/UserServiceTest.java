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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository repository;

    @InjectMocks
    UserService service;

    @Test
    void followOk() {

        //Arrange
        int id = 1;
        int idToFollow = 26;
        Client testUser = new Client(id, "TestUser");
        Seller testSeller = new Seller(id, "TestSeller");
        when(repository.userExists(anyInt())).thenReturn(true, true);
        when(repository.getUserById(any())).thenReturn(testSeller, testUser, testSeller);
        doNothing().when(repository).save(any());
        MessageDto expected = new MessageDto("Vendedor seguido con éxito");
        //Act
        MessageDto result = service.follow(id, idToFollow);
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
        when(repository.userExists(anyInt())).thenReturn(true, true);
        when(repository.getUserById(any())).thenReturn(testSeller, testUser, testSeller);
        //Act Assert
        assertThrows(BadRequestException.class, () -> service.follow(id, idToFollow));

    }

    @Test
    void followSellerNotClient() {

        //Arrange
        int id = 1;
        int idToFollow = 26;
        Client testUser = new Client(id, "TestUser");
        when(repository.userExists(anyInt())).thenReturn(true, true);
        when(repository.getUserById(any())).thenReturn(testUser);
        //Act Assert
        assertThrows(BadRequestException.class, () -> service.follow(id, idToFollow));

    }

    @Test
    void followSameIdBothParams() {

        //Arrange
        int id = 1;
        int idToFollow = 1;
        //Act Assert
        assertThrows(BadRequestException.class,() -> service.follow(id, idToFollow));

    }

    @Test
    void followFollowerNotFound() {

        //Arrange
        int id = 1;
        int idToFollow = 26;
        when(repository.userExists(anyInt())).thenReturn(false);
        //Act Assert
        assertThrows(BadRequestException.class,() -> service.follow(id, idToFollow));

    }

    @Test
    void followFollowedNotFound() {

        //Arrange
        int id = 1;
        int idToFollow = 26;
        when(repository.userExists(anyInt())).thenReturn(true, false);
        //Act Assert
        assertThrows(BadRequestException.class,() -> service.follow(id, idToFollow));

    }

}