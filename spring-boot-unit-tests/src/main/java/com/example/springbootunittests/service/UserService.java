package com.example.springbootunittests.service;

import javax.annotation.Resource;
import com.example.springbootunittests.dao.UserDao;
import org.springframework.stereotype.Service;

@Service
public class UserService {
   @Resource
   private UserDao userDao;

   public String getUser() {
       return userDao.getUser();
   }
}