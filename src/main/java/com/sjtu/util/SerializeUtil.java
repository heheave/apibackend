package com.sjtu.util;

import java.io.*;

/**
 * Created by xiaoke on 17-6-7.
 */
public class SerializeUtil {

    public static byte[] serialize(Object obj) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        byte[] bytes = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            bytes = baos.toByteArray();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    //ignore
                }
            }

            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    //ignore
                }
            }
        }
        return bytes;
    }

    public static Object deserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        Object obj = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            obj = ois.readObject();
        } catch (Exception ioe) {
            ioe.printStackTrace();
        } finally {
            if (bais != null) {
                try {
                    bais.close();
                } catch (IOException e) {
                    //ignore
                }
            }

            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    //ignore
                }
            }
        }
        return obj;
    }

}
