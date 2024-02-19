package com.bootcamp.be_java_hisp_w25_g9.repository;

import com.bootcamp.be_java_hisp_w25_g9.exceptions.NotFoundException;
import com.bootcamp.be_java_hisp_w25_g9.model.Client;
import com.bootcamp.be_java_hisp_w25_g9.model.Seller;
import com.bootcamp.be_java_hisp_w25_g9.model.User;
import com.bootcamp.be_java_hisp_w25_g9.repository.interfaces.IUserRepository;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRespository implements IUserRepository {

    List<User> userList;

    public UserRespository() {
        this.userList = new ArrayList<>();
        populate();
    }

    public String addUser(User user){
        return null;
    }

    public User findUser(int userId){
        User user = userList.stream().filter(u -> u.getUserId() == userId).findFirst().orElse(null);
        if(user == null){
            throw new NotFoundException(MessageFormat.format("No se encontró el usuario con userId {0}", userId));
        }
        return user;

    }

    public List<User> findAll(){
        return userList;
    }

    public void populate() {
        Client pepito = new Client(1, "Pepito");
        Seller armando = new Seller(2, "Armando");
        Seller juan = new Seller(3, "Juan");
        pepito.setFollowed(List.of(armando, juan));
        userList.add(pepito);
    }

}
