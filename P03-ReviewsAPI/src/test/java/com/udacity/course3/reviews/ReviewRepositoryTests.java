package com.udacity.course3.reviews;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Optional;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReviewRepositoryTests {
 
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ReviewRepository reviewRepository;


    /**
     * Test that creation of Review object is retrievable by review id and matches
     * the created object in the repository
     * 
     * @throws Exception
     */
    @Test
    public void testfindByreviewId() throws Exception {
    Product product = testProduct();
    productRepository.save(product);
    Review review = testReview(product);
    try {
        reviewRepository.save(review);
    } catch(Exception e) {
        System.out.println("Unable to save to H2: " + e);
        throw new Exception("Database error! " + e);
    }
    Integer id = review.getReviewId();
    assertFalse( id != 1);
    Optional<Review> actual = reviewRepository.findByreviewId(id);
    assertNotNull(actual);
    Review item = null;
    if(actual.isPresent()){
       item = actual.get();
    }
    assertNotNull(review);
    assertNotNull(item);
    assertEquals(review, item);
  }

    /**
     * Test that creation of Review object is retrievable by product and matches the
     * created object in the repository
     * 
     * @throws Exception
     */
    @Test
    public void testfindByProduct() throws Exception {
    Product product = testProduct();
    productRepository.save(product);
    Review review = testReview(product);
    try {
        reviewRepository.save(review);
    } catch(Exception e) {
        System.out.println("Unable to save to H2: " + e);
        throw new Exception("Database error! " + e);
    }

    ArrayList<Review> actual = reviewRepository.findByproduct(product);
    assertNotNull(actual);
    assertEquals(review, actual.get(0));
 }
  /**
   * 
   * @param Product
   * @return Review
   *  Generates a review object to be used in tests
   */
  private Review testReview(Product product){
    Review review = new Review();
    
    review.setProduct(product);
    review.setReviewAuthor("A mean guy");
    review.setReviewText("This is the worst product ever.");
    return review;
  }

  private Product testProduct(){
      Product product = new Product();
      product.setProductName("NewAndImproved Product");
      product.setPrice(4.99);
      return product;
  }

}