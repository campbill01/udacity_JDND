package com.example.springboothelloworld;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.HashMap;

@RestController
public class HelloController {
   private Map<String, Object> result = new HashMap<>();

   @RequestMapping("/hello")
   public Map<String, Object> hello() {
       result.put("name", "Stephen");
       result.put("city", "San Jose");
       return result;
   }
//    @RequestMapping("/")
//    public Map<String, Object> root() {
//        result.put("name", "NotStephen");
//        result.put("city", "Rho Disland");
//        return result;
//    }
}