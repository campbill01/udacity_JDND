package edu.udacity.java.nano;

import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * WebSocket Server
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session   WebSocket Session
 */

@Component
@ServerEndpoint(value="/chat/{username}")
public class WebSocketChatServer {
    Logger logger = Logger.getLogger(WebSocketChatServer.class.getName());
    /**
     * All chat sessions.
     */
    private static Map<Session, String> onlineSessions = new ConcurrentHashMap<>();
    private static void sendMessageToAll(Message message) throws IOException {
          int numChatters = onlineSessions.size();
          if(numChatters > 0){
            message.setOnlineCount(numChatters);
            message.setType("SPEAK");
            Gson g = new Gson();
            String stringMessage = g.toJson(message);
            for(Session session: onlineSessions.keySet()) {
                session.getBasicRemote().sendText(stringMessage);
            }
          }
        }

    /**
     * Open connection, 1) add session, 2) add user.
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException {
        onlineSessions.put(session, username);    
        logger.log(Level.WARNING, "Added session for: " + onlineSessions.get(session));
        Message newMessage = new Message();
        newMessage.setMessage("Joined the chat");
        newMessage.setUsername(WebSocketChatServer.onlineSessions.get(session));
        WebSocketChatServer.sendMessageToAll(newMessage);
 
    }

    
    @OnMessage
    public void onMessage(Session session, String jsonStr) throws IOException, EncodeException {
        //System.out.println("input to onMessage: " + jsonStr);
        Gson g = new Gson();
        Message newMessage = g.fromJson(jsonStr, Message.class);
        //System.out.println(newMessage.getUsername());
        if(newMessage.getUsername().length() < 1){
            System.out.println("Input username is less than 1 char, setting default username");
            newMessage.setUsername("OhSoImpatient");
        }
        String message = newMessage.getMessage();
        String user = newMessage.getUsername();
        System.out.println("Sending message: " + message + " from: " + user);
        WebSocketChatServer.sendMessageToAll(newMessage);
    }

    /**
     * Close connection, 1) remove session, 2) update user.
     * 
     * @throws IOException
     */
    @OnClose
    public void onClose(Session session) throws IOException {
        Message newMessage = new Message();
        newMessage.setMessage("Left the chat");
        newMessage.setUsername(WebSocketChatServer.onlineSessions.get(session));
        WebSocketChatServer.onlineSessions.remove(session);
        session.close();
        WebSocketChatServer.sendMessageToAll(newMessage);
    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

}
