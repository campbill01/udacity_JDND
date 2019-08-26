package edu.udacity.java.nano;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
@RestController
public class WebSocketChatApplication {
    Logger logger = Logger.getLogger(WebSocketChatApplication.class.getName());


    public static void main(String[] args) {
        SpringApplication.run(WebSocketChatApplication.class, args);
    }

    /**
     * Login Page
     */
    
    @GetMapping("/")
    public ModelAndView login() {
        return new ModelAndView("/login");
    }
    
    /**
     * Chatroom Page
     */
    
    @GetMapping("/index")
    public ModelAndView index(String username, HttpServletRequest request) throws UnknownHostException {
        request.setAttribute("username", username);
        logger.log(Level.WARNING, "Inside of getmapping " + username);
        //System.out.println("Get some information username : " + username);
        //
        return new ModelAndView("/chat");
    }
    
}
