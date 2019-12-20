package com.bjs.mongo;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class MongoDBTest {

    @Test
    public void insertOneTest() {
        System.out.println("插入单个对象");
        MongoDatabase database = MongoDBUtil.getConnect();
        MongoCollection<Document> collection = database.getCollection("user");
        Document document = new Document("name", "张三")
                .append("sex", "男")
                .append("age", 18);
        collection.insertOne(document);
    }

    @Test
    public void insertManyTest() {
        System.out.println("插入多个对象");
        MongoDatabase database = MongoDBUtil.getConnect();
        MongoCollection<Document> collection = database.getCollection("user");
        List<Document> documents = Arrays.asList(
                new Document("name", "李四").append("sex", "男").append("age", 19),
                new Document("name", "王五").append("sex", "男").append("age", 19),
                new Document("name", "张三").append("sex", "男").append("age", 19));
        collection.insertMany(documents);
    }

    @Test
    public void findAllTest() {
        System.out.println("查询所有文档");
        MongoDatabase database = MongoDBUtil.getConnect();
        MongoCollection<Document> collection = database.getCollection("user");
        FindIterable<Document> documents = collection.find();
        MongoCursor<Document> cursor = documents.iterator();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }

    @Test
    public void findWithFilterTest() {
        System.out.println("按照过滤条件查询所有文档");
        MongoDatabase database = MongoDBUtil.getConnect();
        MongoCollection<Document> collection = database.getCollection("user");
        Bson bson = Filters.eq("name", "张三");
        FindIterable<Document> documents = collection.find(bson);
        MongoCursor<Document> cursor = documents.iterator();
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }
}
