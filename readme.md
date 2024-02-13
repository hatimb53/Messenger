## Simple Messenger System Read Me

### Overview
This Simple Messenger system is built using Spring Boot with Java, incorporating JWT for authentication and authorization, and h2 db with JPA framework for database persistence. It provides both one-on-one chat and group chat functionality and can be easily deployed using Docker Compose.

### Features
- **Chat:** Users can send message to each other.
- **Group Chat:** Users can create groups, send messages and maintain group.
- **User Management:** Registration, login
- **Security:** It is ensured through JWT token-based authentication and authorization
    
### Installation

1. **Start Docker Compose:**
    ```
    docker-compose up 
    ```

2. **Access Messenger System:**
    - Once Docker Compose has finished setting up the containers, you can access the Messenger system through your browser at 
   ```
     http://localhost:8080/swagger-ui/index.html#/
   ```

### Usage
- **User Profile:**
    - Create New user
    - Get token from login api which will be used to access rest of the functionalities
    - Token will be expired after few time
    - Fetch all users
-  **Group :**
    - Create groups
    - Add members and Remove members, 
    - Fetch all groups and all members in the group
- **Chat:**
    - Users can send message to direct or group, 
    - Fetch chat history and unread messages.
