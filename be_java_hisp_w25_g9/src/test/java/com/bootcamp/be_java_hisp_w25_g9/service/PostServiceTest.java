package com.bootcamp.be_java_hisp_w25_g9.service;

import com.bootcamp.be_java_hisp_w25_g9.dto.ProductDto;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.FollowedPostsDto;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.PostResponseDto;
import com.bootcamp.be_java_hisp_w25_g9.model.Client;
import com.bootcamp.be_java_hisp_w25_g9.model.Post;
import com.bootcamp.be_java_hisp_w25_g9.model.Product;
import com.bootcamp.be_java_hisp_w25_g9.model.Seller;
import com.bootcamp.be_java_hisp_w25_g9.repository.PostRepository;
import com.bootcamp.be_java_hisp_w25_g9.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    PostRepository postRepository;

    @InjectMocks
    PostService postService;

    @Test
    void getPostVerifyOrderingOkDesc() {
        //Arrange
        Integer userId = 12;
        Boolean userExists = true;
        String order = "date_desc";
        Client clientById = new Client(12, "Daryl Miguel");
        Seller sellerFollowed1 = new Seller(29, "Josiah Sanchez");
        Seller sellerFollowed2 = new Seller(30, "Patrick Blanco");
        clientById.setFollowed(List.of(sellerFollowed1, sellerFollowed2));
        ArrayList<Product> productList = new ArrayList<>();
        productList.add(new Product(1, "Camisa", "Ropa", "Marca A", "Azul", "Algodón"));
        productList.add(new Product(2, "Pantalón", "Ropa", "Marca B", "Negro", "Poliéster"));
        productList.add(new Product(3, "Zapatos", "Calzado", "Marca C", "Blanco", "Cuero"));
        List<Post> postList = new ArrayList<>();
        postList.add(new Post(1, 29, 25, LocalDate.of(2024,1,10), productList.get(0), 81.0));
        postList.add(new Post(2, 29, 40, LocalDate.of(2024,2,19), productList.get(1), 82.0));
        postList.add(new Post(3, 29, 43, LocalDate.of(2024,2,27), productList.get(0), 65.0));
        postList.add(new Post(4, 30, 12, LocalDate.of(2024,2,24), productList.get(2), 9.0));
        postList.add(new Post(5, 30, 32, LocalDate.of(2024,2,25), productList.get(1), 65.0));
        ProductDto productDto1 = new ProductDto(1, "Camisa", "Ropa", "Marca A", "Azul", "Algodón");
        ProductDto productDto2 = new ProductDto(2, "Pantalón", "Ropa", "Marca B", "Negro", "Poliéster");
        ProductDto productDto3 = new ProductDto(3, "Zapatos", "Calzado", "Marca C", "Blanco", "Cuero");
        List<PostResponseDto> expectedPostList = new ArrayList<>();
        expectedPostList.add(new PostResponseDto(3, 29,  LocalDate.of(2024,2,27), productDto1, 43, 65.0));
        expectedPostList.add(new PostResponseDto(5, 30, LocalDate.of(2024,2,25), productDto2, 32, 65.0));
        expectedPostList.add(new PostResponseDto(4, 30, LocalDate.of(2024,2,24), productDto3, 12,9.0));
        expectedPostList.add(new PostResponseDto(2, 29, LocalDate.of(2024,2,19), productDto2, 40, 82.0));
        FollowedPostsDto expected = new FollowedPostsDto(userId, expectedPostList);

        when(userRepository.userExists(userId)).thenReturn(userExists);
        when(userRepository.getUserById(userId)).thenReturn(clientById);
        when(postRepository.findAll()).thenReturn(postList);
        //Act
        FollowedPostsDto result = postService.getPost(userId, order);
        //Assert
        assertEquals(expected, result);
    }

    @Test
    void getPostVerifyOrderingOkAsc() {
        //Arrange
        Integer userId = 12;
        Boolean userExists = true;
        String order = "date_asc";
        Client clientById = new Client(12, "Daryl Miguel");
        Seller sellerFollowed1 = new Seller(29, "Josiah Sanchez");
        Seller sellerFollowed2 = new Seller(30, "Patrick Blanco");
        clientById.setFollowed(List.of(sellerFollowed1, sellerFollowed2));
        ArrayList<Product> productList = new ArrayList<>();
        productList.add(new Product(1, "Camisa", "Ropa", "Marca A", "Azul", "Algodón"));
        productList.add(new Product(2, "Pantalón", "Ropa", "Marca B", "Negro", "Poliéster"));
        productList.add(new Product(3, "Zapatos", "Calzado", "Marca C", "Blanco", "Cuero"));
        List<Post> postList = new ArrayList<>();
        postList.add(new Post(1, 29, 25, LocalDate.of(2024,1,10), productList.get(0), 81.0));
        postList.add(new Post(2, 29, 40, LocalDate.of(2024,2,19), productList.get(1), 82.0));
        postList.add(new Post(3, 29, 43, LocalDate.of(2024,2,27), productList.get(0), 65.0));
        postList.add(new Post(4, 30, 12, LocalDate.of(2024,2,24), productList.get(2), 9.0));
        postList.add(new Post(5, 30, 32, LocalDate.of(2024,2,25), productList.get(1), 65.0));
        ProductDto productDto1 = new ProductDto(1, "Camisa", "Ropa", "Marca A", "Azul", "Algodón");
        ProductDto productDto2 = new ProductDto(2, "Pantalón", "Ropa", "Marca B", "Negro", "Poliéster");
        ProductDto productDto3 = new ProductDto(3, "Zapatos", "Calzado", "Marca C", "Blanco", "Cuero");
        List<PostResponseDto> expectedPostList = new ArrayList<>();
        expectedPostList.add(new PostResponseDto(2, 29, LocalDate.of(2024,2,19), productDto2, 40, 82.0));
        expectedPostList.add(new PostResponseDto(4, 30, LocalDate.of(2024,2,24), productDto3, 12,9.0));
        expectedPostList.add(new PostResponseDto(5, 30, LocalDate.of(2024,2,25), productDto2, 32, 65.0));
        expectedPostList.add(new PostResponseDto(3, 29,  LocalDate.of(2024,2,27), productDto1, 43, 65.0));
        FollowedPostsDto expected = new FollowedPostsDto(userId, expectedPostList);

        when(userRepository.userExists(userId)).thenReturn(userExists);
        when(userRepository.getUserById(userId)).thenReturn(clientById);
        when(postRepository.findAll()).thenReturn(postList);
        //Act
        FollowedPostsDto result = postService.getPost(userId, order);
        //Assert
        assertEquals(expected, result);
    }
}