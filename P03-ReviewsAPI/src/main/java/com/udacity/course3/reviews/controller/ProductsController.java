package com.udacity.course3.reviews.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.repository.ProductRepository;

/**
 * Spring REST controller for working with product entity.
 */
@RestController
@RequestMapping("/products")
public class ProductsController {
    private static Logger logger = LoggerFactory.getLogger(ProductsController.class);
    @Autowired
    private ProductRepository productRepository;
    /**
     * Creates a product.
     *
     * 1. Accept product as argument. Use {@link RequestBody} annotation.
     * 2. Save product.
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody Product product) {
        logger.info("Creating product...");
        productRepository.save(product);
    }

    /**
     * Finds a product by id.
     *
     * @param id The id of the product.
     * @return The product if found, or a 404 not found.
     */
    @RequestMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Integer id) {
        logger.info("Running Products controller get method of id");
        Optional<Product> lookupProduct = productRepository.findByProductId(id);
        HttpHeaders headers = new HttpHeaders();
        if(lookupProduct.isPresent()){
            headers.add("Responded", "ProductsController");
            return ResponseEntity.accepted().headers(headers).body(lookupProduct.get());
        }else{
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
        }

    }

    /**
     * Lists all products.
     *
     * @return The list of products.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<?> listProducts() {
        logger.info("Running Products controller get method of id");
        Iterable<Product> products = productRepository.findAll();
        if(products.equals(null)){
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
        }else{
            List<Product> results = new ArrayList<Product>();
            for(Product item: products){
                results.add(item);
            }
            return results;

        }
 }
}