package edu.udacity.java.nano;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket Server
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session   WebSocket Session
 */

@Component
@ServerEndpoint(value="/chat")
public class WebSocketChatServer {
    private Session session;
    /**
     * All chat sessions.
     */
    private static Map<Session, String> onlineSessions = new ConcurrentHashMap<>();

    private static void sendMessageToAll(String msg) throws IOException {
        // TODO: add send message method.
          for(Session session: onlineSessions.keySet()) {
                //session.addMessageHandler(clazz, handler);
                System.out.println("Inside sendMessageToAll: " + session + " " + session + " " + session.toString());
                session.getBasicRemote().sendText(msg);
          }
        }

    /**
     * Open connection, 1) add session, 2) add user.
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException {
        // add session to map (key,value ??)
        // add user where ?
        this.session = session;
        Message message = new Message();
        message.setUsername(username);
        System.out.println("In onOpen, My session is: " + session);
        //session.getBasicRemote().sendText("{test:data}");
        WebSocketChatServer.onlineSessions.put(session, username);
        //onlineSessions.put(session.username, session.getId());

    }

    
    @OnMessage
    public void onMessage(Session session, String jsonStr) throws IOException, EncodeException {
        //TODO: add send message.
        //webSocket.send(JSON.stringify({username: $('#username').text(), msg: $message.val()}));
        //String message = jsonStr["username"] + ": " + jsonStr["message"];
        // get username and session ?? can't I just send the string ???
        //String message = new Message(jsonStr).getMessage();
        System.out.println("input to onMessage: " + jsonStr);
        Gson g = new Gson();
        //Gson gson = new GsonBuilder().create();
        //
        Message newMessage = g.fromJson(jsonStr, Message.class);
        newMessage.setUsername(WebSocketChatServer.onlineSessions.get(session.getId()));
        System.out.println(newMessage.getUsername());
        if(newMessage.getUsername().length() < 1){
            System.out.println("Input username is less than 1 char, setting default username");
            newMessage.setUsername("OhSoImpatient");
        }
        //JacksonJsonParser parser = new JacksonJsonParser();
        //jsonMessage = new ObjectMapper().readValue()
        //session.getBasicRemote().sendObject((JSONObject) parser.parseMap("{\"this\": \"test\"}"));
        //String response = "{\"username\":\"Bob\",\"msg\":\"Hi there.\"}";
        String response = "{\"username\":\"" + newMessage.getUsername() + "\",\"msg\":\"" + newMessage.getMessage() + "\"}";
        //String response = "{\"username:" + newMessage.getUsername() + "\",\"" + newMessage.getMessage() + "\"}";
        System.out.println("newMessage bits are: " + newMessage.getMessage() + " and " + newMessage.getUsername());
        //session.getBasicRemote().sendText(response);
        WebSocketChatServer.sendMessageToAll(response);
    }

    /**
     * Close connection, 1) remove session, 2) update user.
     * 
     * @throws IOException
     */
    @OnClose
    public void onClose(Session session) throws IOException {
        WebSocketChatServer.onlineSessions.remove(session.getId());
        session.close();
    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

}
