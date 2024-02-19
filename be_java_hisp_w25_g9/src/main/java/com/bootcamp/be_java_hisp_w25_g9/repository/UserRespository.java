package com.bootcamp.be_java_hisp_w25_g9.repository;

import com.bootcamp.be_java_hisp_w25_g9.model.User;
import com.bootcamp.be_java_hisp_w25_g9.repository.interfaces.IUserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRespository implements IUserRepository {
    private List<User> userList;
    public String addUser(User user){
        return null;
    }

    public List<User> findAll(){
        return null;
    }
    public boolean userExists(long userId){
        Optional<User> userOpt = userList.stream().filter(u -> u.getUserId()==userId).findFirst();
        return userOpt.isPresent();
    }
    public User getUserById(long userId){
        Optional<User> userOpt = userList.stream().filter(u -> u.getUserId()==userId).findFirst();
        if(userExists(userId))
            return userOpt.get();
        return null;
    }
    public void save(User user){
        userList.set(userList.indexOf(getUserById(user.getUserId())),user);
    }

}
