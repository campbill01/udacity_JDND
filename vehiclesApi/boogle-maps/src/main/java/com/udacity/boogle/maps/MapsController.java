package com.udacity.boogle.maps;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/maps")
public class MapsController {
    private static final Logger log = LoggerFactory.getLogger(MapsController.class);
    @GetMapping
    public Address get(@RequestParam Double lat, @RequestParam Double lon) {
    	log.info("Request recieved by boggle maps microservice.");
        return MockAddressRepository.getRandom();
    }
}
