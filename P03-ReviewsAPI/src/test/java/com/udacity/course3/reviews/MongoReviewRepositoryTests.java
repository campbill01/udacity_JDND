package com.udacity.course3.reviews;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Optional;

import com.udacity.course3.reviews.entity.MongoReview;
import com.udacity.course3.reviews.repository.MongoReviewRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataMongoTest
public class MongoReviewRepositoryTests {
 
    // @Autowired
    // private ProductRepository productRepository;
    @Autowired
    private MongoReviewRepository mongoReviewRepository;


    /**
     * Test that creation of Review object is retrievable by review id and matches
     * the created object in the repository
     * Need to test:
     * ArrayList<MongoReview> findAllByproductId(Integer productId);
     * Optional<MongoReview> findByreviewId(Integer reviewId);
     * 
     * @throws Exception
     */
    @Test
    public void testfindByreviewId() throws Exception {
    MongoReview review = testReview();
    try {
        mongoReviewRepository.save(review);
    } catch(Exception e) {
        System.out.println("Unable to save to Flapdoodle Mongo: " + e);
        throw new Exception("Database error! " + e);
    }
    
    Optional<MongoReview> actual = mongoReviewRepository.findByreviewId(1);
    assertNotNull(actual);
    MongoReview item = null;
    if(actual.isPresent()){
       item = actual.get();
    }
    assertNotNull(review);
    assertNotNull(item);
    assertEquals(actual.get().getReviewAuthor(), "A mean guy");
  }

    /**
     * Test that creation of Review object is retrievable as a list by productid and the first item matches the
     * created object in the repository
     * 
     * @throws Exception
     */
    @Test
    public void testfindAllByproductId() throws Exception {
    MongoReview review = testReview();
    try {
        mongoReviewRepository.save(review);
    } catch(Exception e) {
        System.out.println("Unable to save to Flapdoodle Mongo: " + e);
        throw new Exception("Database error! " + e);
    }

    ArrayList<MongoReview> actual = mongoReviewRepository.findAllByproductId(1);
    assertNotNull(actual);
    assertEquals(actual.get(0).getReviewAuthor(), "A mean guy");
 }
  /**
   * 
   * @param Product
   * @return MongoReview
   *  Generates a mongo review object to be used in tests
   */
  private MongoReview testReview(){
    MongoReview review = new MongoReview();
    
    review.setProductId(1);
    review.setReviewId(1);
    review.setReviewAuthor("A mean guy");
    review.setReviewText("This is the worst product ever.");
    return review;
  }
 

}