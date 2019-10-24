package com.udacity.course3.reviews.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.ProductRepository;
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
 * Spring REST controller for working with review entity.
 */
@RestController
public class ReviewsController {
    private static Logger logger = LoggerFactory.getLogger(ReviewsController.class);

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ReviewRepository reviewRepository;
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
    public ResponseEntity<?> createReviewForProduct(@PathVariable("productId") Integer productId, @RequestBody Review review) {
        logger.info("Looking up product " + productId);
        Optional<Product> lookupProduct = productRepository.findById(productId);
        if(lookupProduct.isPresent()){
            logger.info("Found product " + productId );
            // need to assign productId to review before saving
            Product product = lookupProduct.get();
            review.setProduct(product);
            logger.info(" saving new review....");
            reviewRepository.save(review);
            logger.info("Saved review for productId " + productId);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Saved", "ReviewsController");
            return ResponseEntity.accepted().headers(headers).body(review);
        }else{     
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
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
            Product product = lookupProduct.get();
            ArrayList<Review> reviews = reviewRepository.findByproduct(product);
            if(reviews.equals(null)){
                logger.info("No reviews for product: " + productId);
                throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
            }else{
                logger.info("Building list of reviews...");
                ArrayList<Review> results = new ArrayList<Review>();
                for(Review item: reviews){
                    results.add(item);
                }
                HttpHeaders headers = new HttpHeaders();
                headers.add("Responded", "ReviewsController");
                return ResponseEntity.accepted().headers(headers).body(results);
            }

        }else{
            logger.info("\nProductId not found\n");
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
        }
    }
}