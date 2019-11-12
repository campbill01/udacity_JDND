package com.example.demo.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;

import org.apache.tomcat.websocket.server.UriTemplate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.OngoingStubbing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderControllerTest {
    Logger logger = LoggerFactory.getLogger(OrderControllerTest.class);

    private OrderController orderController;
    private UserRepository userRepository = mock(UserRepository.class);
    private OrderRepository orderRepository = mock(OrderRepository.class);

    @Before
    public void setUp() {
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "userRepository", userRepository);
        TestUtils.injectObjects(orderController, "orderRepository", orderRepository);
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
        when( orderRepository.findByUser(any(User.class))).thenReturn(orders);
        when( userRepository.findByUsername("Test Guy")).thenReturn(user);

    }

    @Test
    public void findNonExistingUser() throws Exception {
        logger.info("Order Controller test:  findNonExistingUser");
        ResponseEntity<List<UserOrder>> test =  orderController.getOrdersForUser("I'm not a user");
        assertEquals(HttpStatus.NOT_FOUND, test.getStatusCode());
    }

    @Test
    public void findNullUser() throws Exception {
        logger.info("Order Controller test:  nullUser");
        ResponseEntity<List<UserOrder>> test =  orderController.getOrdersForUser("");
        assertEquals(HttpStatus.NOT_FOUND, test.getStatusCode());
    }

    @Test
    public void submitNonExistingUser() throws Exception {
        logger.info("Order Controller test:  findNonExistingUser");
        ResponseEntity<?> order =  orderController.submit("I'm not a user");
        assertEquals(HttpStatus.NOT_FOUND, order.getStatusCode());
    }

    @Test
    public void submitNullUser() throws Exception {
        logger.info("Order Controller test:  nullUser");
        ResponseEntity<?> test =  orderController.submit("");
        assertEquals(HttpStatus.NOT_FOUND, test.getStatusCode());
    }
    @Test
    public void getOrdersForUser() throws Exception {
        logger.info("Order Controller test:  findByUser");
        ResponseEntity<List<UserOrder>> test =  orderController.getOrdersForUser("Test Guy");
        assertNotNull(test.getBody());
        assertEquals(HttpStatus.OK, test.getStatusCode());

    }

    @Test
    public void submitOrderForUser() throws Exception {
        logger.info("Order Controller test:  submitOrderForUser");
        ResponseEntity<?> order =  orderController.submit("Test Guy");
        assertNotNull(order.getBody());
        assertEquals(HttpStatus.OK, order.getStatusCode());

    }
}