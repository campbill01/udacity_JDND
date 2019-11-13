package com.example.demo.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ItemControllerTest {
    private Logger logger =  LoggerFactory.getLogger(ItemControllerTest.class);
    private ItemController itemController;
    private ItemRepository itemRepository = mock(ItemRepository.class);

    @Before
    public void setUp() {
        itemController = new ItemController();
        TestUtils.injectObjects(itemController, "itemRepository", itemRepository);
        Item fakeItem = new Item();
        fakeItem.setName("NotaReal Widget");
        fakeItem.setId((long) 1);
        List<Item> fakeItems = new ArrayList<Item>();
        fakeItems.add(fakeItem);
        when(itemRepository.findByName("NotaReal Widget")).thenReturn(fakeItems);
        when(itemRepository.findById((long) 1)).thenReturn(Optional.of(fakeItem));
    }
    
    @Test
    public void getItem() {
        logger.info("Item Controller test: getItem");
        assertNotNull(itemRepository);
        Item item = new Item();
        item.setName("Round Widget");
        item.setDescription("A description");
        BigDecimal price = new BigDecimal(2.90);
        item.setPrice(price);
        try {
            Item newItem = itemRepository.save(item);
            logger.info("New item saved: " + newItem.getName());
        } catch (Exception e) {
            System.out.println("Item Controller test: Unable to save new item to repository. " + e );
        }
        logger.info("Looking up item");
        try {
            List<Item> items = itemRepository.findAll();
            for(Item foo: items){
                logger.info("Here is an item: " + foo.getName());
            }
            
            ResponseEntity<List<Item>> responseItems  = itemController.getItems();
            List<Item> testItems = responseItems.getBody();
            assertNotNull(testItems.get(0));
            assertEquals("Round Widget", testItems.get(0).getName());
            assertEquals("A widget that is round", testItems.get(0).getDescription());   
            } catch (Exception e) {
                System.out.println("Item Controller test: Error instantiating testItem: " +e);
        }
    }

    @Test
    public void getItemByName() {
        logger.info("Item Controller test: getItemByName");
        Item item = new Item();
        item.setName("NotaReal Widget");
        item.setDescription("A fake item, to be sure.");
        BigDecimal price = new BigDecimal(2.90);
        item.setPrice(price);
        try {
            Item newItem = itemRepository.save(item);
            logger.info("New item saved: " + newItem.getName());
        } catch (Exception e) {
            System.out.println("Item Controller test: Unable to save new item to repository. " + e );
        }
        try {
            ResponseEntity<List<Item>> responseItems = itemController.getItemsByName("NotaReal Widget");
            List<Item> response = responseItems.getBody();
            assertNotNull(response);
            assertEquals("NotaReal Widget", response.get(0).getName());
        } catch (Exception e) {
            logger.info("Item Controller test: Unable to lookup by name");
        }
            
     }

     @Test
     public void ensureTwoItemsAreNotIdentical() {
         logger.info("Item Controller test: ensureTwoItemsAreNotIdentical");
         Item item = new Item();
         item.setName("NotaReal Widget");
         item.setDescription("A fake item, to be sure.");
         BigDecimal price = new BigDecimal(2.90);
         item.setPrice(price);
         Item item2 = new Item();
         item2.setName("Not even trying to be a Widget");
         item2.setDescription("A fake item!!!");
         item2.setPrice(price);
         try {
             Item newItem = itemRepository.save(item);
             Item newItem2 = itemRepository.save(item2);
             logger.info("New items saved: " + newItem.getName() + " " + newItem2.getName());
         } catch (Exception e) {
             System.out.println("Item Controller test: Unable to save new items to repository. " + e );
         }
         try {
             ResponseEntity<List<Item>> compareItems = itemController.getItems();
             List<Item> response = compareItems.getBody();
             
             assertNotEquals(null, response.get(0));

             assertNotEquals(response.get(1), response.get(0));
         } catch (Exception e) {
             logger.info("Item Controller test: Unable to lookup by name");
         }
             
      }

     @Test
     public void getNonExistantItemByName() {
         logger.info("Item Controller test: getNonExistantItemByName");
         try {
             ResponseEntity<List<Item>> responseItems = itemController.getItemsByName("Not an item that exists");
             List<Item> response = responseItems.getBody();
             assertNull(response);
         } catch (Exception e) {
             logger.info("Item Controller test: Unable to lookup nonexistant item by name");
         }
             
      }
 
     @Test
    public void getItemId() {
        logger.info("Item Controller test: getItemId");
        Item item = new Item();
        item.setName("NotaReal Widget");
        item.setDescription("A fake item, to be sure.");
        item.setId((long) 1);
        BigDecimal price = new BigDecimal(2.90);
        item.setPrice(price);

        try {
            Item newItem = itemRepository.save(item);
            logger.info("New item saved: " + newItem.getName());
        } catch (Exception e) {
            System.out.println("Item Controller test: Unable to save new item to repository. " + e );
        }

        try {
            ResponseEntity<Item> responseEntity = itemController.getItemById(item.getId());
            Item response = responseEntity.getBody();
            assertNotNull(response);
            assertEquals(item.getId(), response.getId());
        } catch (Exception e) {
            logger.info("Item Controller test: Unable to lookup by Id");
        }
            
     }
     
 }