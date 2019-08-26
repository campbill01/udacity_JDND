package edu.udacity.java.nano;

/**
 * WebSocket message model
 */
public class Message {
    private String message;
    private String username;
    // I think there should be a message and a username
    // sending jsonStr here, probably need to split and set username and message
    // until I figure out more of this, leaving as one big string
    public Message(){}

    public Message(String username, String message ){
        this.message = message;
        this.username = username;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }
}
