package com.bjs.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDBUtil {
    public static MongoDatabase getConnect() {
        MongoClient mongoClient = new MongoClient("192.168.18.50");
        MongoDatabase database = mongoClient.getDatabase("test");
        return database;
    }
}
