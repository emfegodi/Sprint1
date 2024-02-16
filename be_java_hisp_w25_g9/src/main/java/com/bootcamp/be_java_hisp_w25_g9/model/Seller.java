package com.bootcamp.be_java_hisp_w25_g9.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level= AccessLevel.PRIVATE)
public class Seller extends User {
    public Seller(int userId, String userName){
        super(userId, userName);
    }
}
