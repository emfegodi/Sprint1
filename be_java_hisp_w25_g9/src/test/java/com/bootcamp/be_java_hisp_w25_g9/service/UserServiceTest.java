package com.bootcamp.be_java_hisp_w25_g9.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyByte;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import com.bootcamp.be_java_hisp_w25_g9.dto.UserDto;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.FollowedDto;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.FollowersDto;
import com.bootcamp.be_java_hisp_w25_g9.exceptions.BadRequestException;
import com.bootcamp.be_java_hisp_w25_g9.exceptions.NoUsersFoundException;
import com.bootcamp.be_java_hisp_w25_g9.exceptions.NotFoundException;
import com.bootcamp.be_java_hisp_w25_g9.model.Client;
import com.bootcamp.be_java_hisp_w25_g9.model.Seller;
import com.bootcamp.be_java_hisp_w25_g9.model.User;
import com.bootcamp.be_java_hisp_w25_g9.repository.UserRepository;
import com.bootcamp.be_java_hisp_w25_g9.repository.interfaces.IUserRepository;
import com.bootcamp.be_java_hisp_w25_g9.service.interfaces.IUserService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  UserRepository userRepository;

  @InjectMocks
  UserService userService;

  Client client1;
  Client client2;
  Client client3;
  Client client4;
  Client client5;
  Client client6;

  Seller seller1;
  Seller seller2;
  Seller seller3;

  @BeforeEach
  void setUp() {
    client1 = new Client(1, "Quynn Nunez");
    client2 = new Client(2, "Zena Pastor");
    client3 = new Client(3, "Sylvia Catalina");
    client4 = new Client(4, "Macon Vera");
    client5 = new Client(5, "Lucia Arvizu");
    client6 = new Client(6, "Alexander Lozano");

    seller1 = new Seller(26, "Chase Sanchez");
    seller2 = new Seller(27, "Darren Mendoza");
    seller3 = new Seller(28, "Kamal Alvarado");

    client1.setFollowed(List.of(seller1, seller2));
    client2.setFollowed(List.of(seller1, seller2));
    client3.setFollowed(List.of(seller1, seller2));
    client4.setFollowed(List.of(seller1, seller2));

  }

  @Test
  void getFollowersInAscendingOrderOk() {
    // ARRANGE
    int sellerId = seller1.getUserId();
    FollowersDto expectedAscFollowersDto = new FollowersDto(
        seller1.getUserId(),
        seller1.getUserName(),
        new ArrayList<UserDto>(List.of(
            new UserDto(client4.getUserId(), client4.getUserName()), // M Q S Z -> 4 1 3 2
            new UserDto(client1.getUserId(), client1.getUserName()),
            new UserDto(client3.getUserId(), client3.getUserName()),
            new UserDto(client2.getUserId(), client2.getUserName())
        ))
    );

    when(userRepository.getUserById(sellerId)).thenReturn(seller1);
    when(userRepository.findAll()).thenReturn(List.of(client1, client2, client3, client4, client5, client6, seller1, seller2, seller3));

    // ACT
    FollowersDto resultAscFollowersDto = userService.getFollowers(sellerId, "name_asc");

    // ASSERT
    assertEquals(expectedAscFollowersDto, resultAscFollowersDto);
  }

  @Test
  void getFollowersInDescendingOrderOk() {
    // ARRANGE
    FollowersDto expectedDescFollowersDto = new FollowersDto(
        seller2.getUserId(),
        seller2.getUserName(),
        new ArrayList<UserDto>(List.of(
            new UserDto(client2.getUserId(), client2.getUserName()), // Z S Q M -> 2 3 1 4
            new UserDto(client3.getUserId(), client3.getUserName()),
            new UserDto(client1.getUserId(), client1.getUserName()),
            new UserDto(client4.getUserId(), client4.getUserName())
        ))
    );

    when(userRepository.getUserById((int) seller2.getUserId())).thenReturn(seller2);
    when(userRepository.findAll()).thenReturn(List.of(client1, client2, client3, client4, client5, client6, seller1, seller2, seller3));

    // ACT
    FollowersDto resultDescFollowersDto = userService.getFollowers((int) seller2.getUserId(), "name_desc");

    // ASSERT
    assertEquals(expectedDescFollowersDto, resultDescFollowersDto);
  }

  @Test
  void ThrowSellerNotFoundWhenFindFollowers() {
    // ARRANGE
    int userId = 26;
    when(userRepository.getUserById(userId)).thenReturn(null);
    // ACT
    // ASSERT
    assertThrows(NoUsersFoundException.class, () -> userService.getFollowers(userId, "name_asc"));
  }

  @Test
  void ThrowUserIsNotSellerWhenFindFollowers() {
    // ARRANGE
    int clientId = client1.getUserId();
    when(userRepository.getUserById(clientId)).thenReturn(client1);
    // ACT
    // ASSERT
    assertThrows(BadRequestException.class, () -> userService.getFollowers(clientId, "name_asc"));
  }

  @Test
  void ThrowSellerDontHaveFollowersWhenFindFollowers() {
    int userId = seller1.getUserId();

    // ARRANGE
    when(userRepository.getUserById(userId)).thenReturn(seller1);
    when(userRepository.findAll()).thenReturn(List.of());
    // ACT
    // ASSERT
    assertThrows(NoUsersFoundException.class, () -> userService.getFollowers(userId, "name_asc"));
  }

  // Followed
  @Test
  void getFollowedInAscendingOrderOk() {
    // ARRANGE
    int clientId = client1.getUserId();
    FollowedDto expectedAscFollowedDto = new FollowedDto(
        client1.getUserId(),
        client1.getUserName(),
        new ArrayList<UserDto>(List.of(
            new UserDto(seller1.getUserId(), seller1.getUserName()),
            new UserDto(seller2.getUserId(), seller2.getUserName())
        ))
    );

    when(userRepository.getUserById(clientId)).thenReturn(client1);

    // ACT
    FollowedDto resultAscFollowedDto = userService.getFollowed(clientId, "name_asc");

    // ASSERT
    assertEquals(expectedAscFollowedDto, resultAscFollowedDto);
  }

  @Test
  void getFollowedInDescendingOrderOk() {
    // ARRANGE
    int clientId = client1.getUserId();
    FollowedDto expectedDescFollowedDto = new FollowedDto(
        client1.getUserId(),
        client1.getUserName(),
        new ArrayList<UserDto>(List.of(
            new UserDto(seller2.getUserId(), seller2.getUserName()),
            new UserDto(seller1.getUserId(), seller1.getUserName())
        ))
    );

    when(userRepository.getUserById(clientId)).thenReturn(client1);

    // ACT
    FollowedDto resultDescFollowedDto = userService.getFollowed(clientId, "name_desc");

    // ASSERT
    assertEquals(expectedDescFollowedDto, resultDescFollowedDto);
  }

  @Test
  void ThrowUserNotFoundWhenFindFollowed() {
    // ARRANGE
    when(userRepository.getUserById(anyInt())).thenReturn(null);
    // ACT
    // ASSERT
    assertThrows(NotFoundException.class, () -> userService.getFollowed(anyInt(), "name_asc"));
  }

  @Test
  void ThrowUserDontHaveFollowed() {
    int clientId = client5.getUserId();
    // ARRANGE
    when(userRepository.getUserById(anyInt())).thenReturn(client5);
    // ACT
    // ASSERT
    assertThrows(NoUsersFoundException.class, () -> userService.getFollowed(clientId, "name_asc"));
  }
}