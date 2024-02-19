package com.bootcamp.be_java_hisp_w25_g9.controller;

import com.bootcamp.be_java_hisp_w25_g9.dto.request.PostRequestDto;
import com.bootcamp.be_java_hisp_w25_g9.service.interfaces.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class PostController{

    @Autowired
    IPostService postService;

    @PostMapping("/post")
    public ResponseEntity<?> insertNewPost(@RequestBody PostRequestDto newPost){
        return new ResponseEntity<>(postService.createPost(newPost), HttpStatus.OK);
    }

    @GetMapping("/followed/{userId}/list")
    public ResponseEntity<?> getFollowedPostOrderByDate(@PathVariable int userId, @RequestParam("order") String order){
        return new ResponseEntity<>(postService.getPost(userId, order),HttpStatus.OK);
    }


}
