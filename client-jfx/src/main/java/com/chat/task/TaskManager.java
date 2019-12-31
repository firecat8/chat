package com.chat.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author gdimitrova
 */
public class TaskManager {

    private static final ExecutorService POOL = Executors.newFixedThreadPool(30);

    public static void executeTask(Task task) {
        POOL.execute(task);
    }

}
