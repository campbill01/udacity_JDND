package com.udacity.course3.reviews.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

/**
 * Spring REST controller for working with comment entity.
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {
    private static Logger logger = LoggerFactory.getLogger(CommentsController.class);
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    /**
     * Creates a comment for a review.
     *
     * 1. Add argument for comment entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, save comment.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.POST)
    public ResponseEntity<?> createCommentForReview(@PathVariable("reviewId") Integer reviewId, @RequestBody Comment comment) {
        Optional<Review> lookupReview = reviewRepository.findById(reviewId);
        logger.info("Looking up review " + reviewId);
        if(lookupReview.isPresent()){
            logger.info("Found review " + reviewId);
            //need to assign review to comment before saving
            Review review = lookupReview.get();
            comment.setReview(review);
            logger.info("Saving Comment " + comment.getCommentText() + " for review " + reviewId);
            commentRepository.save(comment);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Saved", "CommentController");
            return ResponseEntity.accepted().headers(headers).body(comment);
        }else{     
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * List comments for a review.
     *
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, return list of comments.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.GET)
    public List<?> listCommentsForReview(@PathVariable("reviewId") Integer reviewId) {
        logger.info("Running Comments controller get method of id");
        Optional<Review> lookupReview = reviewRepository.findByreviewId(reviewId);
        if(lookupReview.isPresent()){
            logger.info("Found review, looking up comments");
            Review review = lookupReview.get();
            // get all comments for review and return
            ArrayList<Comment> comments = commentRepository.findByreview(review);
            if(comments.equals(null)){
                logger.info("No comments for review: " + reviewId);
                throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
            }else{
                logger.info("Building list of comments...");
                ArrayList<Comment> results = new ArrayList<Comment>();
                for(Comment item: comments){
                    results.add(item);
                }
                return results;
    
            }
        }else{
            logger.info("Review not found " + reviewId);
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
        }

    }
}