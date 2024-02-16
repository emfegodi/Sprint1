package com.bootcamp.be_java_hisp_w25_g9.repository;

import com.bootcamp.be_java_hisp_w25_g9.model.Product;
import com.bootcamp.be_java_hisp_w25_g9.repository.interfaces.IProductRespository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository implements IProductRespository {

    public String addProduct(Product product){
        return null;
    }

    public List<Product> findAll(){
        return null;
    }
}
