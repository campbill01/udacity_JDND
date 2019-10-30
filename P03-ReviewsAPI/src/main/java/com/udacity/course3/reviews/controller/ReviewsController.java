package com.udacity.course3.reviews.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.udacity.course3.reviews.entity.MongoReview;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.MongoReviewRepository;
import com.udacity.course3.reviews.repository.ProductRepository;
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
 * Spring REST controller for working with review entity.
 */
@RestController
public class ReviewsController {
    private static Logger logger = LoggerFactory.getLogger(ReviewsController.class);

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MongoReviewRepository mongoReviewRepository;
    /**
     * Creates a review for a product.
     * <p>
     * 1. Add argument for review entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of product.
     * 3. If product not found, return NOT_FOUND.
     * 4. If found, save review.
     *
     * @param productId The id of the product.
     * @return The created review or 404 if product id is not found.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.POST)
    public ResponseEntity<?> createReviewForProduct(@PathVariable("productId") Integer productId, @RequestBody ObjectNode objectNode) {
        logger.info("Looking up product " + productId);
        Optional<Product> lookupProduct = productRepository.findById(productId);
        if(lookupProduct.isPresent()){
            logger.info("Found product " + productId );
            // need to assign product to review and save in mysql
            Product product = lookupProduct.get();
            // Object mapper is used to map json to a generic object that can be used by both Review and MongoReview
            ObjectMapper mapper = new ObjectMapper();
            Review review = mapper.convertValue(objectNode, Review.class);
            review.setProduct(product);
            logger.info("saving new review to Mysql....");
            reviewRepository.save(review);
            // save to mongo as well
            MongoReview mongoReview = mapper.convertValue(objectNode, MongoReview.class);
            // Mongo gets the ID, not the object
            mongoReview.setProductId(productId);
            mongoReview.setReviewId(review.getReviewId());
            Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
            mongoReview.setCreated_at((Date)timeStamp);
            logger.info("saving new review to Mongo....");
            mongoReviewRepository.save(mongoReview);
            // Response
            logger.info("Saved review for productId " + productId);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Saved", "ReviewsController");
            logger.info("Sending response.");
            return ResponseEntity.accepted().headers(headers).body(review);
        }else{
            logger.info("Product not found, sending 404");
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Lists reviews by product.
     *
     * @param productId The id of the product.
     * @return The list of reviews.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<List<?>> listReviewsForProduct(@PathVariable("productId") Integer productId) {
        logger.info("Starting Request method for reviews/products/" + productId);
        // check for existence of productId
        // if found, find reviews for that productId
        Optional<Product> lookupProduct = productRepository.findById(productId);
        if(lookupProduct.isPresent()){
            logger.info("Found productId: " + productId);
            ArrayList<MongoReview> reviews = mongoReviewRepository.findAllByproductId(productId);
            if(reviews.equals(null)){
                logger.info("No reviews found for: " +productId + ", sending 404");
                return ResponseEntity.notFound().build();
            }else{
                logger.info("Building list of reviews...");
                ArrayList<MongoReview> results = new ArrayList<MongoReview>();
                for(MongoReview item: reviews){
                    results.add(item);
                }
                logger.info("Sending Response.");
                HttpHeaders headers = new HttpHeaders();
                headers.add("Responded", "ReviewsController");
                return ResponseEntity.accepted().headers(headers).body(results);   
            }

        }else{
            logger.info("ProductId not found, sending 404");
            return ResponseEntity.notFound().build();
        }
    }
}