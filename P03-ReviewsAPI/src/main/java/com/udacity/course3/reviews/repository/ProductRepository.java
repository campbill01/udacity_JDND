package com.udacity.course3.reviews.repository;

import java.util.Optional;

import com.udacity.course3.reviews.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer>{
    Optional<Product> findByProductId(Integer productId);

}