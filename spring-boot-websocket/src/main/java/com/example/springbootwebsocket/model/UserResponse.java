package com.example.springbootwebsocket.model;

public class UserResponse {
    private String content;
    
    public UserResponse(){}
    // getters and setters.
    public UserResponse(String content){
        this.content = content;
    }
    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content = content;
    }
  }