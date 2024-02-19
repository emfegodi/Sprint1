package com.bootcamp.be_java_hisp_w25_g9.repository;

import com.bootcamp.be_java_hisp_w25_g9.model.Post;
import com.bootcamp.be_java_hisp_w25_g9.model.Product;
import com.bootcamp.be_java_hisp_w25_g9.repository.interfaces.IPostRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepository implements IPostRepository {

    List<Post> postList;

    public PostRepository(List<Post> postList) {
        this.postList = new ArrayList<>();
        populate();
    }

    public String addPost(Post post){
        return null;
    }

    public List<Post> findAll(){
        return postList;
    }

    public void populate(){
//        postList.add(new Post(
//                1, 2, 1, LocalDate.now(), new Product(
//                        1,"Hola","Tipo","Marca","rojo","Notes"
//        ), 90.89
//        ));
//        postList.add(new Post(
//                2, 2, 1, LocalDate.of(2024,1,1), new Product(
//                2,"Hola","Tipo","Marca","rojo","Notes"
//        ), 90.89
//        ));
//        postList.add(new Post(
//                3, 2, 1, LocalDate.of(2024,2,5), new Product(
//                3,"Hola","Tipo","Marca","rojo","Notes"
//        ), 90.89
//        ));postList.add(new Post(
//                4, 2, 1, LocalDate.of(2025,2,5), new Product(
//                3,"Hola","Tipo","Marca","rojo","Notes"
//        ), 90.89
//        ));
    }

}
