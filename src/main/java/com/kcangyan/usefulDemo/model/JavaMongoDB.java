package com.kcangyan.usefulDemo.model;

import com.google.gson.Gson;
import com.kcangyan.usefulDemo.config.MyselfConfig;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaMongoDB {
    private static String dataBase = MyselfConfig.getMongoDBDataBase();
    public static MongoClient connect() {
        try{
            MongoCredential credential = MongoCredential.createCredential(MyselfConfig.getMongoDBUser(),dataBase,
                    MyselfConfig.getMongoDBPass().toCharArray());
            ServerAddress serverAddress = new ServerAddress(MyselfConfig.getMongoDBHost(), MyselfConfig.getMongoDBPort());
            MongoClient mongoClient = new MongoClient(serverAddress, Arrays.asList(credential));
            return mongoClient;
            /*
            try{
                MongoDatabase db = mongoClient.getDatabase(dbName);
                return db;
            }catch (Exception e){
                mongoClient.close();
                return null;
            }*/
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        //MongoCollection<Document> collection = db.getCollection("test");
        //BasicDBObject query = new BasicDBObject();
        //query.put("name", "mongoDB");
        //MongoCursor<org.bson.Document> cursor = collection.find();
        //FindIterable<Document> findIterable= collection.find();
        //MongoCursor<Document> mongoCursor = collection.find(query).iterator();
        // while(mongoCursor.hasNext()){
        // String mongojson = mongoCursor.next().toJson();
        //}
        //mongoClient.close();
        //System.out.print("\nend...");
    }
    //查询
    public static String SelectCollection(MongoClient mc, String CollectionName){
        MongoDatabase db = mc.getDatabase(dataBase);
        MongoCollection<Document> collection = db.getCollection(CollectionName);
        MongoCursor<Document> mongoCursor = collection.find().iterator();
        List reList = new ArrayList();
        while(mongoCursor.hasNext()){
            String mongoJson = mongoCursor.next().toJson();
            reList.add(mongoJson);
        }
        Gson gs = new Gson();
        return gs.toJson(reList);
    }
    //插入
    public static String InsertCollection(MongoClient mc, String CollectionName, ArrayList InsertDocument){
        if(InsertDocument == null || InsertDocument.size() == 0){
            return "fail";
        }
        MongoDatabase db = mc.getDatabase(dataBase);
        MongoCollection<Document> collection = db.getCollection(CollectionName);
        try{
            collection.insertMany(InsertDocument);
            return "success";
        }catch (Exception e){
            //e.printStackTrace();
            return "ERROR";
        }
    }
    //删除 Bson对象 Bson filter1 = Filters.in("filterName",value1,value2...)  filter = Filters.and(filter1,filter2)
    public static String DeleteCollection(MongoClient mc, String CollectionName, Bson filter, boolean Many){
        MongoDatabase db = mc.getDatabase(dataBase);
        MongoCollection<Document> collection = db.getCollection(CollectionName);
        if(Many){
            try{
                collection.deleteMany(filter);
                return "success";
            }catch (Exception e){
                //e.printStackTrace();
                return "ERROR";
            }
        }else {
            try{
                collection.deleteOne(filter);
                return "success";
            }catch (Exception e){
                //e.printStackTrace();
                return "ERROR";
            }
        }
    }
    public static String DeleteCollection(MongoClient mc, String CollectionName, Bson filter){
        MongoDatabase db = mc.getDatabase(dataBase);
        MongoCollection<Document> collection = db.getCollection(CollectionName);
        try{
            collection.deleteMany(filter);
            return "success";
        }catch (Exception e){
            //e.printStackTrace();
            return "ERROR";
        }
    }
    //修改 Document document = new Document("$set", new Document("age", 100));
    public static String UpdateCollection(MongoClient mc, String CollectionName, Bson filter, Document UpdateDocument){
        MongoDatabase db = mc.getDatabase(dataBase);
        MongoCollection<Document> collection = db.getCollection(CollectionName);
        try{
            collection.updateMany(filter,UpdateDocument);
            return "success";
        }catch (Exception e){
            //e.printStackTrace();
            return "ERROR";
        }
    }
}
