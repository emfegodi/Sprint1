package com.bootcamp.be_java_hisp_w25_g9.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@AllArgsConstructor
public class Product {

    int productId;
    String productName;
    String type;
    String brand;
    String color;
    String notes;
}
