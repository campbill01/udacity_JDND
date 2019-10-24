package com.udacity.course3.reviews;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepositoryTests {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private CommentRepository commentRepository;


    /**
     * Test that creation of Comment object is retrievable by review and matches the
     * created object in the repository
     *  ArrayList<Comment> findByreview(Review review);
     * 
     * @throws Exception
     */
    @Test
    public void testfindByreview() throws Exception {
    Product product = testProduct();
    productRepository.save(product);
    Review review = testReview(product);
    reviewRepository.save(review);

    Comment comment = new Comment();
    comment.setCommentAuthor("An angry dude.");
    comment.setCommentText("What do you know about anything, anyway.");
    comment.setReview(review);
    commentRepository.save(comment);

    ArrayList<Comment> actual = commentRepository.findByreview(review);
    assertNotNull(actual);
    assertEquals(comment, actual.get(0));
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
    review.setReviewAuthor("Random Reviewer");
    review.setReviewText("I would buy a million of these.");
    return review;
  }

  private Product testProduct(){
      Product product = new Product();
      product.setProductName("Some Product");
      product.setPrice(4.99);
      return product;
  }

}