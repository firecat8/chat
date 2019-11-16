package com.chat.messaging;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author gdimitrova
 */
public class ServicePointRegistryImpl extends AbstractServicePointRegistry implements AutoCloseable {

    private static Client client;

    private Properties config = null;

    protected Map<Class, ServicePoint> servicePoints = new ConcurrentHashMap<>();

    public ServicePointRegistryImpl(Properties props) throws IOException {
        this(props.getProperty("host"), Integer.valueOf(props.getProperty("port")));
        this.config = props;
    }

    private ServicePointRegistryImpl(String host, int port) throws IOException {
        client = new Client(host, port);
        client.connectToServer();
        registerServicePoints();
    }

    private void registerServicePoints() {
        servicePoints.put(UserServiceImpl.class, new UserServiceImpl(client));
    }

    @Override
    public void close() throws Exception {
        client.closeConnection();
    }

    @Override
    public <T extends ServicePoint> T getServicePoint(Class<T> sei) {
        return (T) servicePoints.get(sei);
    }

}
