package com.udacity.course3.reviews;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.repository.ProductRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTests {
 
    @Autowired
    private ProductRepository productRepository;

  /**
   * Test that creation of Product object is retrievable and matches the created object in the repository
   */
  @Test
  public void testfindByProductId(){
    Product product = new Product();
    product.setProductName("NewAndImproved Product");
    product.setPrice(4.99);
    
    productRepository.save(product);
    Integer id = product.getProductId();

    Optional<Product> actual = productRepository.findByProductId(id);
    Product item = null;
    if(actual.isPresent()){
      item = actual.get();
    }
    assertNotNull(item);
    assertEquals(product, item);
  }

}