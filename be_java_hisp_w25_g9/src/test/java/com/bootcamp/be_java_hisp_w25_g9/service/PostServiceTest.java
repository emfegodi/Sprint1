package com.bootcamp.be_java_hisp_w25_g9.service;

import com.bootcamp.be_java_hisp_w25_g9.dto.ProductDtoMixIn;
import com.bootcamp.be_java_hisp_w25_g9.dto.request.PostRequestDtoMixin;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.FollowedPostsDto;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.PostResponseDto;
import com.bootcamp.be_java_hisp_w25_g9.model.Client;
import com.bootcamp.be_java_hisp_w25_g9.model.Post;
import com.bootcamp.be_java_hisp_w25_g9.model.Product;
import com.bootcamp.be_java_hisp_w25_g9.model.Seller;
import com.bootcamp.be_java_hisp_w25_g9.repository.PostRepository;
import com.bootcamp.be_java_hisp_w25_g9.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    UserRepository userRepository;
    @Mock
    PostRepository postRepository;

    @InjectMocks
    PostService postService;
    static ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    public static void before() {
        mapper.registerModule(new JavaTimeModule());
        mapper.addMixIn(Product.class, ProductDtoMixIn.class);
        mapper.addMixIn(Post.class, PostRequestDtoMixin.class);
    }

    @DisplayName("getPostByOrder Case ASC")
    @Test
    void getPostByOrderAsc() {
        //ARRANGE
        int userId = 1;
        List<Product> productList = List.of(
                new Product(1, "Camisa", "Ropa", "Marca A", "Azul", "Algodón"),
                new Product(2, "Pantalón", "Ropa", "Marca B", "Negro", "Poliéster"),
                new Product(3, "Zapatos", "Calzado", "Marca C", "Blanco", "Cuero")
        );

        List<Seller> clients = List.of(
                new Seller(35, "Deacon Marquez"),
                new Seller(36, "Molly Martina"),
                new Seller(37, "Chase Tapia"),
                new Seller(38, "Leigh Gabriela")
        );

        Client client = new Client(1, "Quynn Nunez");
        client.setFollowed(clients);

        List<Post> postList = new ArrayList<>();
        postList.add(new Post(1, 35, 25, LocalDate.now().minusDays(3), productList.get(1), 78.0));
        postList.add(new Post(2, 36, 25, LocalDate.now(), productList.get(1), 78.0));
        postList.add(new Post(3, 35, 25, LocalDate.now().minusDays(20), productList.get(1), 78.0));
        postList.add(new Post(4, 35, 25, LocalDate.now().minusDays(10), productList.get(1), 78.0));

        List<PostResponseDto> postResponseDtosExpected = new ArrayList<>();
        postResponseDtosExpected.add(mapper.convertValue(postList.get(1), PostResponseDto.class));
        postResponseDtosExpected.add(mapper.convertValue(postList.get(0), PostResponseDto.class));
        postResponseDtosExpected.add(mapper.convertValue(postList.get(3), PostResponseDto.class));

        FollowedPostsDto expected = new FollowedPostsDto(1, postResponseDtosExpected);
        //ACT
        when(userRepository.userExists(userId)).thenReturn(true);
        when(userRepository.getUserById(userId)).thenReturn(client);
        when(postRepository.findAll()).thenReturn(postList);
        //ASSERT
        assertEquals(postService.getPost(userId), expected);
    }

}