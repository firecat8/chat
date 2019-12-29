package com.chat.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gdimitrova
 */
public class FileUtils {

    private static final Logger LOGGER = Logger.getLogger(FileUtils.class.getName());

    public static <S extends Serializable> void writeFile(String fileName, S object) {
        writeFile(fileName, Arrays.asList(object));
    }

    public static <S extends Serializable> void writeFile(String fileName, List<S> objects) {
        try (ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream(new File(fileName + ".txt")));) {
            for (S obj : objects) {
                o.writeObject(obj);
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public static <S extends Serializable> List<S> readFile(String fileName) {
        List<S> objects = new ArrayList<>();
        try (ObjectInputStream oi = new ObjectInputStream(new FileInputStream(new File(fileName + ".txt")))) {
            S obj = null;
            do {
                obj = (S) oi.readObject();
                if (obj != null) {
                    objects.add(obj);
                }
            } while (obj != null);
        } catch (IOException | ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return objects;
    }
}
