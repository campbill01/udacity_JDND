package com.example.demo.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

//import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //System.out.println("\n\n###############  loadbyUsername ###########\n\n");
        //return this.userRepository.findByUsername(username).map(CreateUserRequest::new).orElseThrow
        com.example.demo.model.persistence.User applicationUser = userRepository.findByUsername(username);
        if (applicationUser == null) {
             throw new UsernameNotFoundException(username);
         }
        //System.out.println("\n\n###############  loadbyUsername return statement ###########\n\n");
        return new User(applicationUser.getUsername(), applicationUser.getPassword(), emptyList());
    }
}