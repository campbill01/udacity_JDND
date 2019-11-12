package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.controllers.CartController;
import com.example.demo.controllers.ItemController;
import com.example.demo.controllers.OrderController;
import com.example.demo.controllers.UserController;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ECommerceTests {
	// @Autowired CartController cartController;
	// @Autowired ItemController itemController;
	// @Autowired OrderController orderController;
	// @Autowired UserController userController;
	
	@Test
	public void contextLoads() {
	}

}
