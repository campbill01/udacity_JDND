package com.example.demo.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserControllerTest {
    Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

    private UserController userController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp() {
        userController = new UserController();
        TestUtils.injectObjects(userController, "userRepository", userRepository);
        TestUtils.injectObjects(userController, "cartRepository", cartRepository);
        TestUtils.injectObjects(userController, "bCryptPasswordEncoder", encoder);

        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("TestId");
        r.setPassword("testpassword");
        r.setConfirmPassword("testpassword");
        logger.info("User Controller test: creating user");
        ResponseEntity<User> created = userController.createUser(r);

        when( userRepository.findByUsername("TestId")).thenReturn(created.getBody());
        when( userRepository.findById((long) 0)).thenReturn(Optional.of(created.getBody()));

    }

    @Test
    public void findById() throws Exception {
        logger.info("User Controller test: findById");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername( "TestId");
        r.setPassword("testpassword");
        r.setConfirmPassword("testpassword");
        
        ResponseEntity<User> created = userController.createUser(r);
        ResponseEntity<User> response = userController.findById(created.getBody().getId());

        User u = response.getBody();
        assertNotNull(u);
        assertEquals(r.getUsername(), u.getUsername());      
    }

    @Test
    public void findByUserName() throws Exception {
        logger.info("User Controller test: findByUserName");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("TestId");
        r.setPassword("testpassword");
        r.setConfirmPassword("testpassword");

        final ResponseEntity<User> created = userController.createUser(r);
        final ResponseEntity<User> response = userController.findByUserName(created.getBody().getUsername());     
        
        User u = response.getBody();
        assertNotNull(u);
        assertEquals(r.getUsername(), u.getUsername());      
    }

    @Test
    public void noSuchUser() throws Exception {
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("Not in there");
        r.setPassword("testpassword");
        r.setConfirmPassword("testpassword");
        logger.info("User Controller test: creating user");

        final ResponseEntity<User> created = userController.createUser(r);
        final ResponseEntity<User> response = userController.findByUserName(created.getBody().getUsername());     
        
        User u = response.getBody();
        assertNull(u);
    }

    @Test
    public void createUserHappyPath() throws Exception {
        logger.info("User Controller test: createUserHappyPath");
        when(encoder.encode("testpassword")).thenReturn("thisisHashed");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername( "test");
        r.setPassword("testpassword");
        r.setConfirmPassword("testpassword");

        final ResponseEntity<User> response = userController.createUser(r);
        
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        User u = response.getBody();
        assertNotNull(u);
        assertEquals(0, u.getId());
        assertEquals("test", u.getUsername());
        assertEquals("thisisHashed", u.getPassword());
        
    }

    @Test
    public void createUserShortPassword() throws Exception {
        logger.info("User Controller test: createUserShortPassword");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername( "test");
        r.setPassword("test");
        r.setConfirmPassword("test");

        final ResponseEntity<User> response = userController.createUser(r);
        
        assertNotNull(response);
        //Password is too short
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void createUserMisMatchedPassword() throws Exception {
        logger.info("User Controller test: createUserMisMatchedPassword");
        when(encoder.encode("testpassword")).thenReturn("thisisHashed");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername( "test");
        r.setPassword("testpassword");
        r.setConfirmPassword("wrongPassword");

        final ResponseEntity<User> response = userController.createUser(r);
        
        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
        
    }

}