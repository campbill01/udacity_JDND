package com.udacity.course3.reviews.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.MongoComment;
import com.udacity.course3.reviews.entity.MongoReview;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.MongoReviewRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    @Autowired
    private MongoReviewRepository mongoReviewRepository;

    /**
     * Creates a comment for a review.
     *
     * 1. Add argument for comment entity. Use {@link RequestBody} annotation. 2.
     * Check for existence of review. 3. If review not found, return NOT_FOUND. 4.
     * If found, save comment.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.POST)
    public ResponseEntity<?> createCommentForReview(@PathVariable("reviewId") Integer reviewId,
            @RequestBody ObjectNode objectNode) {
        Optional<Review> lookupReview = reviewRepository.findById(reviewId);
        logger.info("Looking up review " + reviewId);
        if (lookupReview.isPresent()) {
            logger.info("Found review " + reviewId);

            // need to assign review to comment and save to mysql
            Review review = lookupReview.get();
            // Object mapper is used to map json to genric object
            ObjectMapper mapper = new ObjectMapper();
            Comment comment = mapper.convertValue(objectNode, Comment.class);
            comment.setReview(review);
            logger.info("Saving Comment " + comment.getCommentText() + " for review " + reviewId);
            commentRepository.save(comment);
            // Save to mongo Document as new comment
            // get Document
            Optional<MongoReview> mongoLookupReview = mongoReviewRepository.findByreviewId(reviewId);
            if (lookupReview.isPresent()) {
                logger.info("Mongo review found...");
                MongoReview mongoReview = mongoLookupReview.get();
                // get ArrayList of Comments
                logger.info("Looking up comments...");
                // Need to initialize this so that it doesn't end up as null if there are no comments.  
                ArrayList<MongoComment> mongoComments = new ArrayList<MongoComment>();
                try {
                    mongoComments.addAll(mongoReview.getComments());
                } catch (Exception e) {
                    logger.info("There were no previous comments: " +e);
                }
                // add mongoComment to ArrayList
                logger.info("Mapping to MongoComment object..");
                MongoComment mongoComment = mapper.convertValue(objectNode, MongoComment.class);
                Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
                mongoComment.setCreated_at((Date) timeStamp);
                logger.info("Adding Comment to array");
                mongoComments.add(mongoComment);
                logger.info("Adding comments to review.");
                mongoReview.setComments(mongoComments);
                // Save
                logger.info("Saving to Mongo");
                mongoReviewRepository.save(mongoReview);
                HttpHeaders headers = new HttpHeaders();
                headers.add("Saved", "CommentController");
                return ResponseEntity.accepted().headers(headers).body(comment);
            } else {
                logger.info("Mongo Review not found, sending 404");
                return ResponseEntity.notFound().build();
            }
        } else {
            logger.info("Mysql Review not found, sending 404");
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * List comments for a review.
     *
     * 2. Check for existence of review. 3. If review not found, return NOT_FOUND.
     * 4. If found, return list of comments.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.GET)
    public List<?> listCommentsForReview(@PathVariable("reviewId") Integer reviewId) {
        logger.info("Looking up review: " + reviewId);
        // Lookup the comments in mongo and return them if they exist
        Optional<MongoReview> lookupReview = mongoReviewRepository.findByreviewId(reviewId);
        if (lookupReview.isPresent()) {
            logger.info("Review is present, looking up comments");
            MongoReview mongoReview = lookupReview.get();
            // Need to initialize this so that it doesn't end up as null if there are no comments
            ArrayList<MongoComment> comments = new ArrayList<MongoComment>();
            logger.info("Found Review, looking for comments");
            try {
                comments.addAll(mongoReview.getComments());
            } catch (Exception e) {
                logger.info("No comments for review: " + e);
                List<?> emptyList = new ArrayList();
                return emptyList;
            }
            logger.info("Found comments...");
            return comments;
        }else{
            logger.info("Review not found " + reviewId);
            List<?> emptyList = new ArrayList();
                return emptyList;
        }

    }
}