package com.udacity.course3.reviews.repository;

import java.util.ArrayList;
import java.util.Optional;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Integer>{
    Optional<Review> findByreviewId(Integer reviewId);
    ArrayList<Review> findByproduct(Product product);

}