package com.udacity.course3.reviews.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.repository.ProductRepository;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
            logger.info("Product not found, sending 404");
            return ResponseEntity.notFound().build();
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
            logger.info("Product not found, sending 404");
            return (List<?>) ResponseEntity.notFound().build();
        }else{
            List<Product> results = new ArrayList<Product>();
            for(Product item: products){
                results.add(item);
            }
            return results;

        }
 }
}