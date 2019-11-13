package com.example.demo.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CartControllerTest {
    Logger logger = LoggerFactory.getLogger(CartControllerTest.class);

    private CartController cartController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp() {
        cartController = new CartController();

        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjects(cartController, "itemRepository", itemRepository);

        Item item = new Item();
        item.setDescription("Fake item");
        item.setId((long) 0);
        item.setName("Round Widget");
        BigDecimal price = new BigDecimal(100.99);
        item.setPrice(price);

        Cart cart = new Cart();
        cart.addItem(item);
        
        User user = new User();
        user.setId(0);
        user.setCart(cart);
        user.setPassword("testpassword");
        user.setUsername("Test Guy");
        
        List<UserOrder> orders = new ArrayList<UserOrder>();
        UserOrder order = new UserOrder();
        
        order.setId((long)0);
        order.setUser(user);
        orders.add(order);

        logger.info("Order Controller Test: setup " + order.getUser());
        when( userRepository.findByUsername("Test Guy")).thenReturn(user);
        when( cartRepository.findById((long) 0)).thenReturn(Optional.of(cart));
        when( itemRepository.findById((long) 0)).thenReturn(Optional.of(item));
    }

    @Test
    public void addTocart() throws Exception {
        logger.info("Cart Controller test: addTocart");
        ModifyCartRequest mcr = new ModifyCartRequest();
        mcr.setItemId(0);
        mcr.setQuantity(1);
        mcr.setUsername("Test Guy");
        //save cart
        ResponseEntity<Cart> created = cartController.addTocart(mcr);
        try {
            List<Cart> cars = cartRepository.findAll();
            System.out.println("\n\n\nPrepare for what the fark...");
            for(Cart car: cars){
               System.out.println("what the fark? " + car.getUser());
              }
        } catch (Exception e) {
            System.out.println("Prepare to prepare for what the fark..." + e);
        }
        
        
        Optional<Cart> optionalTestCart = cartRepository.findById((long) 0);
        Cart testCart = null;
        if(optionalTestCart.isPresent()){
            testCart = optionalTestCart.get();
            logger.info("Optional cart " + testCart.getItems());
        }
        Cart c = created.getBody();
        logger.info("Cart Controller test: is testCart null?");
        assertNotNull(c);
        logger.info("Is c and testCart the same?");
        assertEquals(c.getUser(), testCart.getUser());      
    }

    @Test
    public void addToCartNullUser() throws Exception {
        logger.info("Cart Controller test: nullUser");
        ModifyCartRequest mcr = new ModifyCartRequest();
        mcr.setItemId(0);
        mcr.setQuantity(1);
        mcr.setUsername("");
        //save cart
        ResponseEntity<Cart> created = cartController.addTocart(mcr);
        assertEquals(HttpStatus.NOT_FOUND, created.getStatusCode());
    }

    @Test
    public void removeFromCart() throws Exception {
        logger.info("Cart Controller test: removeFromCart");
        ModifyCartRequest mcr = new ModifyCartRequest();
        mcr.setItemId(0);
        mcr.setQuantity(1);
        mcr.setUsername("Test Guy");
        //empty cart
        ResponseEntity<Cart> removed = cartController.removeFromcart(mcr);
        assertNotNull(removed);
        assertEquals(HttpStatus.OK, removed.getStatusCode());
    }

    @Test
    public void removeFromCartNullUser() throws Exception {
        logger.info("Cart Controller test: removeFromCartNullUser");
        ModifyCartRequest mcr = new ModifyCartRequest();
        mcr.setItemId(0);
        mcr.setQuantity(1);
        mcr.setUsername("");
        //empty cart
        ResponseEntity<Cart> removed = cartController.removeFromcart(mcr);
        assertEquals(HttpStatus.NOT_FOUND, removed.getStatusCode());
    }

}