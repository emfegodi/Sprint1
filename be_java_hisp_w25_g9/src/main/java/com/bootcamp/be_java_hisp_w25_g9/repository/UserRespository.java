package com.bootcamp.be_java_hisp_w25_g9.repository;

import com.bootcamp.be_java_hisp_w25_g9.model.User;
import com.bootcamp.be_java_hisp_w25_g9.repository.interfaces.IUserRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRespository implements IUserRepository {

    List<User> userList = new ArrayList<>();

    public String addUser(User user){
        return null;
    }

    public List<User> findAll(){
        return null;
    }

    @Override
    public User getUserById(Integer id) {
        return userList.stream().filter(x -> x.getUserId() == id).findFirst().orElse(null);
    }

}
