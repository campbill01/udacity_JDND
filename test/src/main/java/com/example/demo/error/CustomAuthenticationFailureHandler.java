package com.example.demo.error;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

/**
 *  Used to get auth exceptions in to splunk
 *  
 */ 

public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    Logger splunkLogger = LoggerFactory.getLogger("splunk.logger"); 

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {    

       if(exception.getClass().isAssignableFrom(BadCredentialsException.class)) {
              splunkLogger.error("Bad credentials " + request.getUserPrincipal() + " " + exception.toString());
        }

       else if (exception.getClass().isAssignableFrom(SessionAuthenticationException.class)) {      
        splunkLogger.error("Bad session " + request.getUserPrincipal() + " " + exception.toString());  
      }

      super.onAuthenticationFailure(request, response, exception); 
    }    
}