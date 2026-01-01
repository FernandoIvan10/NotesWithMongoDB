package com.mycompany.notes;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {
    // Instance representing the connection to MongoDB
    private MongoClient mongoClient;
    // Instance representing the database
    private MongoDatabase database;

    // Constructor
    public MongoDBConnection() {
        String connectionString = getConfig("MONGO_URI","mongodb://localhost:27017");
        String databaseName = getConfig("MONGO_DB","notesDB");
        // A connection to MongoDB is established
        mongoClient = MongoClients.create(connectionString);
        // Obtaining the database reference
        database = mongoClient.getDatabase(databaseName);
    }
    
    // Method to obtain properties variables
    private static String getConfig(String key, String defaultValue) {
        return System.getProperty(key, defaultValue);
    }

    // Method to obtain the bd
    public MongoDatabase getDatabase() {
        return database;
    }

    // Method to close the connection to the database
    public void closeConnection() {
        // If there is a connection to the Database, it is closed
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
