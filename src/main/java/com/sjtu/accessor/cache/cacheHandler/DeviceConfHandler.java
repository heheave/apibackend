package com.sjtu.accessor.cache.cacheHandler;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sjtu.config.Configure;
import com.sjtu.config.V;
import org.bson.Document;

import java.net.InetSocketAddress;

/**
 * Created by xiaoke on 17-11-9.
 */
public class DeviceConfHandler extends CacheHandlerExecutor {

    private MongoClient mc;

    private final String dbName;

    private final String colName;

    public DeviceConfHandler(Configure conf) {
        super(conf);
        dbName = conf.getStringOrElse(V.MONGO_DBNAME_DEVICECONF);
        colName = conf.getStringOrElse(V.MONGO_COLNAME_DEVICECONF);
    }

    @Override
    public void start() {
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        int timeout = conf.getIntOrElse(V.MONGO_DB_TIMEOUT);
        builder.serverSelectionTimeout(timeout);
        builder.connectTimeout(timeout);
        builder.socketTimeout(timeout);
        String host = conf.getStringOrElse(V.MONGO_DB_HOST);
        int port = conf.getIntOrElse(V.MONGO_DB_PORT);
        InetSocketAddress serverAddr = new InetSocketAddress(host, port);
        mc = new MongoClient(new ServerAddress(serverAddr), builder.build());
    }

    @Override
    public void stop() {
        if (mc != null) {
            mc.close();
        }
    }

    public byte[] getEntry(String key) {
        try {
            MongoDatabase mdb = mc.getDatabase(dbName);
            MongoCollection mc = mdb.getCollection(colName);
            //Map<String, Object> bson = new HashMap<String, Object>();
            String sql = "{'id':'" + key + "'}";
            FindIterable<Document> t = mc.find(Document.parse(sql));
            Document d = t.first();
            if (d != null) {
                return d.toJson().getBytes();
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

}
