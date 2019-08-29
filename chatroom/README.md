# Chat Room
Complete the chat room application implementation using WebSocket.

## Background
WebSocket is a communication protocol that makes it possible to establish a two-way communication channel between a
server and a client.

## Instruction
### Implement the message model
Message model is the message payload that will be exchanged between the client and the server. Implement the Message
class in chat module. Make sure you cover all there basic actions.
1. ENTER
2. CHAT
3. LEAVE

### Complete WebSocketChatServer
Implement all TODOs inside WebSocketChatServer follow each method description.

### Run the application with command
mvn compile; mvn spring-boot:run

### Packaging instructions
Selenium is used for tests, the app must be running for this to complete

1. In terminal 1:mvn spring-boot:run
2. In terminal 2:mvn package
