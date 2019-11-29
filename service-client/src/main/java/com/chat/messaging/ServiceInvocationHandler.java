package com.chat.messaging;

import com.chat.messaging.message.Request;
import com.chat.messaging.message.ResponseListener;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 * @author gdimitrova
 */
public class ServiceInvocationHandler implements InvocationHandler {

    private final Client client;

    public ServiceInvocationHandler(Client client) {
        this.client = client;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> serviceClazz = method.getDeclaringClass();
        Request req = (Request) args[0];
        ResponseListener listener = (ResponseListener) args[1];
        client.sendMessage(serviceClazz, method.getName(), req, listener);
        return null;
    }

}
