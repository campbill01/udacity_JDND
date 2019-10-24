package com.udacity.course3.reviews.repository;

import java.util.ArrayList;

import com.udacity.course3.reviews.entity.Comment;
//import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.entity.Review;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer>{
    ArrayList<Comment> findByreview(Review review);

}