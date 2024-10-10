package com.mycompany.notes;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author Fernando Iván Ascencio Cortés
 */
public class MongoDBConnection {
 //Variables and constants
    // Constant containing the connection URL to mongo DB
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";
    // Constant containing the database name
    private static final String DATABASE_NAME = "notesDB";
    // Instance representing the connection to MongoDB
    private MongoClient mongoClient;
    // Instance representing the database
    private MongoDatabase database;

 // Methods
    // Constructor
    public MongoDBConnection() {
     // Initialization of variables
        // A connection to MongoDB is established
        mongoClient = MongoClients.create(CONNECTION_STRING);
        // Obtaining the database reference
        database = mongoClient.getDatabase(DATABASE_NAME);
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
