package com.bootcamp.be_java_hisp_w25_g9.service;

import com.bootcamp.be_java_hisp_w25_g9.dto.request.PostRequestDto;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.FollowedPostsDto;
import com.bootcamp.be_java_hisp_w25_g9.dto.response.MessageDto;
import com.bootcamp.be_java_hisp_w25_g9.exceptions.NotFoundException;
import com.bootcamp.be_java_hisp_w25_g9.model.Post;
import com.bootcamp.be_java_hisp_w25_g9.model.Product;
import com.bootcamp.be_java_hisp_w25_g9.model.Seller;
import com.bootcamp.be_java_hisp_w25_g9.model.User;
import com.bootcamp.be_java_hisp_w25_g9.repository.interfaces.IPostRepository;
import com.bootcamp.be_java_hisp_w25_g9.repository.interfaces.IProductRespository;
import com.bootcamp.be_java_hisp_w25_g9.repository.interfaces.IUserRepository;
import com.bootcamp.be_java_hisp_w25_g9.service.interfaces.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService implements IPostService {

    @Autowired
    private IPostRepository postRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IProductRespository productRespository;

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
        if (!productRespository.findAll().contains(product)){
            productRespository.addProduct(product);
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
        return null;
    }

    @Override
    public FollowedPostsDto getPost(int userId, String order) {
        return null;
    }
}
