package com.example.springbootexception.exceptions;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

@Configuration
public class MySimpleMappingExceptionResolver {
   @Bean
   public SimpleMappingExceptionResolver getSimpleMappingExceptionResolver() {
       SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
       Properties mapping = new Properties();
       mapping.put("java.lang.ArithmeticException", "mathError"); //key=exception full name. value, view name
       mapping.put("java.lang.NullPointerException", "nullPointerError");
       resolver.setExceptionMappings(mapping);
       return resolver;
   }
}