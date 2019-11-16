package com.chat.messaging;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gdimitrova
 */
public class ServicePointRegistryImpl extends AbstractServicePointRegistry implements AutoCloseable {

    private static Client client;

    private Properties config = null;

    protected Map<Class, ServicePoint> servicePoints = new ConcurrentHashMap<>();

    public ServicePointRegistryImpl() {
        config = loadProperties();
    }

    public ServicePointRegistryImpl(String host, int port) throws IOException {
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

    private Properties loadProperties() {
        if (config == null) {
            try {
                config = new Properties();
                InputStreamReader in = new InputStreamReader(getClass().getResourceAsStream("/config.properties"));
                config.load(in);
            } catch (IOException ex) {
                Logger.getLogger(ServicePointRegistryImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return config;
    }
}
