package edu.udacity.java.nano;

/**
 * WebSocket message model
 */
public class Message {
    private String message;
    private String username;
    private String type;
    private int onlineCount;

    public Message(){}

    public Message(String username, String message, String type, int onlineCount ){
        this.message = message;
        this.username = username;
        this.type = type;
        this.onlineCount = onlineCount;
    }

    public String getMessage(){
        return this.message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }
   
    public String getType(){
        return this.type;
    }

    public void setType(String type){
        this.type = type;
    }

    public int getOnlineCount(){
        return this.onlineCount;
    }

    public void setOnlineCount(int onlineCount){
        this.onlineCount = onlineCount;
    }

}
