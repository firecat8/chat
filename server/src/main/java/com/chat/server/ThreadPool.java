package com.chat.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author gdimitrova
 */
public class ThreadPool implements AutoCloseable {

    private ExecutorService executorService;

    public ThreadPool(int maxThreads) {
        executorService = Executors.newFixedThreadPool(maxThreads);
    }

    public void accept(Runnable r) {
        executorService.submit(r);
    }

    @Override
    public void close() throws Exception {
        executorService.shutdown();
    }

}
