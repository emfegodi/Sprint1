package com.bootcamp.be_java_hisp_w25_g9.service;

import com.bootcamp.be_java_hisp_w25_g9.dto.ProductDtoMixIn;
import com.bootcamp.be_java_hisp_w25_g9.dto.request.PostRequestDto;
import com.bootcamp.be_java_hisp_w25_g9.dto.request.PostRequestDtoMixin;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.FollowedPostsDto;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.MessageDto;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.PostResponseDto;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.PostResponseDtoMixin;
import com.bootcamp.be_java_hisp_w25_g9.exceptions.BadRequestException;
import com.bootcamp.be_java_hisp_w25_g9.exceptions.NotFoundException;
import com.bootcamp.be_java_hisp_w25_g9.model.Post;
import com.bootcamp.be_java_hisp_w25_g9.model.Product;
import com.bootcamp.be_java_hisp_w25_g9.model.Seller;
import com.bootcamp.be_java_hisp_w25_g9.model.User;
import com.bootcamp.be_java_hisp_w25_g9.repository.interfaces.IPostRepository;
import com.bootcamp.be_java_hisp_w25_g9.repository.interfaces.IProductRespository;
import com.bootcamp.be_java_hisp_w25_g9.service.interfaces.IPostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class PostService implements IPostService {
    private final IPostRepository postRepository;
    private final IUserRepository userRepository;
    private final IProductRespository productRepository;
    ObjectMapper mapper = new ObjectMapper();

    public PostService(IPostRepository postRepository, IUserRepository userRepository, IProductRespository productRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        mapper.addMixIn(Product.class, ProductDtoMixIn.class);
        mapper.addMixIn(Post.class, PostResponseDtoMixin.class);
        mapper.addMixIn(Post.class, PostRequestDtoMixin.class);
    }

    @Override
    public MessageDto createPost(PostRequestDto postRequestDto) {
        User seller = userRepository.getUserById(postRequestDto.user_id());
        if (seller == null || seller.getClass().equals(Seller.class)){
            throw new NotFoundException("El usuario no se encuentra o no es vendedor");
        }
        Product product = new Product(
                postRequestDto.product().product_id(),
                postRequestDto.product().product_name(),
                postRequestDto.product().type(),
                postRequestDto.product().brand(),
                postRequestDto.product().color(),
                postRequestDto.product().notes()
        );
        if (!productRepository.findAll().contains(product)){
            productRepository.addProduct(product);
        }
        Post post = new Post(
                postRepository.findAll().size(),
                postRequestDto.user_id(),
                postRequestDto.category(),
                postRequestDto.date(),
                product,
                postRequestDto.price()
        );
        postRepository.addPost(post);
        return new MessageDto("Publicación creada con éxito");
    }

    @Override
    public FollowedPostsDto getPost(int userId) {
        return new FollowedPostsDto(userId, getPostsByuserId(userId));
    }

    public List<PostResponseDto> getPostsByuserId(int userId){
        if(!userRepository.userExists(userId)) throw new NotFoundException(MessageFormat.format("El usuario con id {0} no existe",userId));
        List<Seller> followedList = userRepository.getUserById(userId).getFollowed();
        if(followedList.isEmpty()){
            throw new NotFoundException(MessageFormat.format("El usuario {0} no tiene vendedores seguidos", userId));
        }
        List<Post> postsLlist = postRepository.findAll();
        List<Post> lastestPosts = new ArrayList<>();
        for (Seller seller : followedList) {
            lastestPosts.addAll(
                    postsLlist.stream()
                            .sorted(Comparator.comparing(Post::getDate).reversed())
                            .filter(post -> {
                                        return post.getUserId() == seller.getUserId() &&
                                                compareDates(post.getDate(), LocalDate.now());
                                    }
                            ).toList());
        }
        if(lastestPosts.isEmpty()){
            throw new NotFoundException(MessageFormat.format("No se encontraron post de los vendedores seguidos del usuario {0}",userId));
        }
        return lastestPosts.stream().map(post -> mapper.convertValue(post, PostResponseDto.class)).toList();
    }

    public boolean compareDates(LocalDate date1, LocalDate date2){
        long compDate = ChronoUnit.DAYS.between(date1, date2);
        double compDateWeeks = compDate/7.0;
        return compDateWeeks >= 0  && compDateWeeks <= 2.0;
    }

    @Override
    public FollowedPostsDto getPost(int userId, String order) {
        FollowedPostsDto followedPost;
        switch (order){
            case "date_asc" -> {
                return new FollowedPostsDto(
                        userId,
                        getPostsByuserId(userId).stream()
                                .sorted(Comparator.comparing(PostResponseDto::date)).toList());
            }
            case "date_desc" -> {
                return followedPost = getPost(userId);
            }
            default -> throw new BadRequestException(MessageFormat.format("{0} no es valido, recuerde que debe ingresar 'date_asc' o 'date_desc'", order));
        }
    }
}
