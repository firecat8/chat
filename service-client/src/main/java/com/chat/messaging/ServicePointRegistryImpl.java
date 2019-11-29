package com.chat.messaging;

import com.chat.messaging.services.ChatService;
import com.chat.messaging.services.ServicePointRegistry;
import com.chat.messaging.services.UserService;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.Properties;

/**
 *
 * @author gdimitrova
 */
public class ServicePointRegistryImpl implements ServicePointRegistry {

    private final Client client;
    private final UserService userService;
    private final ChatService chatService;

    public ServicePointRegistryImpl(Properties props) throws IOException, ClassNotFoundException {
        this(props.getProperty("host"), Integer.valueOf(props.getProperty("port")));
    }

    private ServicePointRegistryImpl(String host, int port) throws IOException, ClassNotFoundException {
        client = new Client(host, port);
        client.connectToServer();
        userService = createService(UserService.class, client);
        chatService = createService(ChatService.class, client);
    }
    
    private <T> T createService(Class<T> clazz, Client client)
    {
        return (T) Proxy.newProxyInstance(
                getClass().getClassLoader(), 
                new Class[]{clazz}, 
                new ServiceInvocationHandler(client));
    }


    @Override
    public void close() throws Exception {
        client.closeConnection();
    }

    @Override
    public UserService getUserService() {
        return userService;
    }

    @Override
    public ChatService getChatService() {
        return chatService;
    }

    
}
