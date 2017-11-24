package com.sjtu.accessor.cache.cacheServer;

import com.sjtu.accessor.cache.CacheService;
import com.sjtu.accessor.cache.cacheHandler.CacheHandlerExecutor;
import com.sjtu.accessor.cache.rabbitmq.RabbitProducer;
import com.sjtu.config.Configure;
import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xiaoke on 17-11-8.
 */
public class CacheServerService implements CacheService {

    private static final Logger log = Logger.getLogger(CacheServerService.class);

    private final InetSocketAddress isa;

    private ServerSocket ss;

    //private final ZkManager zkManager;

    //private final String zkPubPath;

    private final RabbitProducer rabbitProducer;

    private final ExecutorService es;

    private volatile boolean isRunning;

    private Map<String, CacheHandlerExecutor> handlerMap;

    public CacheServerService(InetSocketAddress isa,
                              String host,
                              int port,
                              String queueName,
                              Map<String, String> handlerMeta,
                              Configure conf) {
        this.isa = isa;
        //this.zkManager = new ZkManager(zkUrl, zkTimeout);
        //this.zkPubPath = zkSubPath;
        this.rabbitProducer = new RabbitProducer(host, port, queueName);
        this.handlerMap = new HashMap<String, CacheHandlerExecutor>();
        for (Map.Entry<String, String> entry: handlerMeta.entrySet()) {
            String key = entry.getKey();
            String classPath = entry.getValue();
            CacheHandlerExecutor che = null;
            try {
                Class<?> clz = Class.forName(classPath);
                Constructor<?> constructor = clz.getConstructor(conf.getClass());
                che = (CacheHandlerExecutor)constructor.newInstance(conf);
                che.start();
                handlerMap.put(key.toUpperCase(), che);
            } catch (Exception e) {
                log.warn("Cannot instance " + key + " CacheHandler " + classPath);
                if (che != null) {
                    che.stop();
                }
                continue;
            }
        }
        this.es = Executors.newFixedThreadPool(handlerMap.size());
    }

    public void startService() throws Exception{
        isRunning = true;
        this.rabbitProducer.start();
        try {
            ss = new ServerSocket();
            ss.bind(isa);
        } catch (IOException e) {
            log.error("start cache server error");
            ss = null;
        }
        if (ss != null) {
            Thread t = new Thread() {
                public void run() {
                    while (isRunning) {
                        try {
                            Socket newSoc = ss.accept();
                            startNewAccess(newSoc);
                        } catch (IOException e) {
                            continue;
                        }
                    }
                }
            };
            t.setDaemon(true);
            t.start();
        }

    }

    public void stopService() {
        isRunning = false;
        if (ss != null) {
            try {
                ss.close();
            } catch (IOException e) {
                // do nothing
            }
        }
        if (es != null) {
            es.shutdown();
        }
        if (rabbitProducer != null) {
            rabbitProducer.stop();
        }
        for (CacheHandlerExecutor che: handlerMap.values()) {
            che.stop();
        }
    }

    public void change(String desc, String key) {
        //ZkClient zkClient = zkManager.getClient();
        //if (!zkClient.exists(zkPubPath)) {
        //    try {
        //        zkClient.create(zkPubPath, desc + REV_TOKEN1 + key.getBytes(), CreateMode.PERSISTENT);
        //    } catch (Exception e) {
        //        // do nothing
        //    }
        //} else {
        //    zkClient.writeData(zkPubPath, desc + REV_TOKEN1 + key);
        //}
        if (!rabbitProducer.publish(desc + REV_TOKEN1 + key)) {
            log.warn("Change Message Sending error");
        }
    }

    private void startNewAccess(final Socket s) {
        es.submit(new Runnable() {
            public void run() {
                startNewAccess0(s);
            }
        });
    }

    private void startNewAccess0(Socket s) {
        try {
            DataInputStream dis = new DataInputStream(s.getInputStream());
            int len = dis.readInt();
            //System.out.println("Server read len: " + len);
            byte[] bytes = new byte[len];
            dis.readFully(bytes, 0, len);
            String[] info = (new String(bytes)).split(REV_TOKEN1);
            //System.out.println("-------: " + info[0]);
            if (info == null || info.length < 2) return;
            byte[] retBytes = queryByKey(info[0], info[1]);
            //System.out.println("Server write len: " + ((retBytes == null) ? 0 : retBytes.length));
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            if (retBytes == null) {
                dos.writeInt(0);
            } else {
                dos.writeInt(retBytes.length);
                dos.write(retBytes, 0, retBytes.length);
            }
            dos.flush();
            dis.close();
            dos.close();
        } catch (Exception e) {
            log.warn("Tackle a connection error");
        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }
    }

    private byte[] queryByKey(String desc, String key) {
        CacheHandlerExecutor ch = handlerMap.get(desc.toUpperCase());
        if (ch == null) {
            return null;
        } else {
            return ch.getEntry(key);
        }
    }
}
