package com.bootcamp.be_java_hisp_w25_g9.repository;

import com.bootcamp.be_java_hisp_w25_g9.model.Post;
import com.bootcamp.be_java_hisp_w25_g9.repository.interfaces.IPostRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepository implements IPostRepository {

    public String addPost(Post post){
        return null;
    }

    public List<Post> findAll(){
        return null;
    }

}
