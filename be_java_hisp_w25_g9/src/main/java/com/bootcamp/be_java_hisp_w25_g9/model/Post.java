package com.bootcamp.be_java_hisp_w25_g9.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
public class Post {
    int id;
    int userId;
    int category;
    LocalDate date;
    Product product;
    double price;




}
