package com.mycompany.notes;

import java.util.Scanner;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

/**
 *
 * @author Fernando Iván Ascencio Cortés
 */

public class App {
 //Variables and constants
    // Instance of class MongoDBConnection
    private static MongoDBConnection mongoDBConnection;
    // Instance representing a collection
    private static MongoCollection<Document> notesCollection;
    
    // constants representing the names of fields and the collection itself
    private static String NOTE_COLLECTION = "notes";
    private static String TITLE_FIELD = "title";
    private static String CONTENT_FIELD = "content";
    private static String CREATION_DATE_FIELD = "createdAt";
    private static String UPDATE_DATE_FIELD = "updateAt";
    
    // Program entry point
    public static void main(String[] args) {
     // Variables
        // scanner
        Scanner read = new Scanner(System.in);
        // User's choice
        byte choice;
        
        // The connection to the database is initialized
        mongoDBConnection = new MongoDBConnection();
        // Connection to the notes collection is established
        notesCollection = mongoDBConnection.getDatabase().getCollection(NOTE_COLLECTION);
        
        // Principal menu
        do{
            System.out.println("\n ********** Notes App **********");
            System.out.println("1. Add Note");
            System.out.println("2. Edit Note");
            System.out.println("3. Delete Note");
            System.out.println("4. Show Notes");
            System.out.println("5. Exit");
            System.out.println("\n Choose an option: ");
            choice = read.nextByte();
            read.nextLine();

            // The user's choice is validated
            switch(choice){
                case 1:
                    addNote(read);
                    break;
                case 2:
                    modifyNote(read);
                    break;
                case 3:
                    deleteNote(read);
                    break;
                case 4:
                    showNotes();
                    break;
                default:
                    System.out.println("\n Wrong entry, try again");
            }
        }while(choice!=5);
        
        // Scanner is closed
        read.close();
        // Connection is closed
        mongoDBConnection.closeConnection();
    }
    
    // Method to add a note
    private static void addNote(Scanner read){
        // User enters data
        System.out.println("\n Title:");
        String title = read.nextLine();
        System.out.println("Content:");
        String content = read.nextLine();
        
        // A new note is created
        Note note = new Note(null,title,content);
        
        // The note is saved in MongoDB
        Document doc = new Document(TITLE_FIELD, note.getTitle())
            .append(CONTENT_FIELD, note.getContent())
            .append(CREATION_DATE_FIELD, note.getCreatedAt())
            .append(UPDATE_DATE_FIELD, note.getUpdatedAt());
        notesCollection.insertOne(doc);
        System.out.println("\n Note added successfully");
    }
    
    // Method that allows the user to modify a task
    private static void modifyNote(Scanner scanner) {
        // User inserts a title
        System.out.print("\n Title of the note you want to modify: ");
        String oldTitle = scanner.nextLine();
        
        // Searching for the note
        Document existingNote = notesCollection.find(new Document(TITLE_FIELD, oldTitle)).first();
        if (existingNote == null) {
            System.out.println("\n No note was found with that title");
            return;
        }

        // Asks the user for new data
        System.out.print("New title: ");
        String newTitle = scanner.nextLine();
        System.out.print("New content: ");
        String newContent = scanner.nextLine();

        // Modify the document with the new values
        Document updatedDoc = new Document(TITLE_FIELD, newTitle)
                .append(CONTENT_FIELD, newContent)
                .append(UPDATE_DATE_FIELD, new java.util.Date()); // Actualizar la fecha
        notesCollection.updateOne(existingNote, new Document("$set", updatedDoc));
        System.out.println("\n Note successfully modified");
    }
    
    // Method to delete a note
    private static void deleteNote(Scanner scanner) {
        // User enters the title of the note to delete
        System.out.print("\n Title of the note you want to delete: ");
        String titleToDelete = scanner.nextLine();

        // Searching for the note
        Document existingNote = notesCollection.find(new Document(TITLE_FIELD, titleToDelete)).first();
        if (existingNote == null) {
            System.out.println("\n No note was found with that title");
            return;
        }

        // Delete the note
        notesCollection.deleteOne(existingNote);
        System.out.println("\n Note successfully deleted");
    }

    
    // Method that displays notes
     private static void showNotes() {
        // Query results iterator
        MongoCursor<Document> cursor = notesCollection.find().iterator();
        // Notes are displayed
        System.out.println("\n Notes:");
        while (cursor.hasNext()) {
            Document doc = cursor.next();
            System.out.println("Title: " + doc.getString(TITLE_FIELD));
            System.out.println("Content: " + doc.getString(CONTENT_FIELD));
            System.out.println("Creation date: " + doc.getDate(CREATION_DATE_FIELD));
            System.out.println("Modification date: " + doc.getDate(UPDATE_DATE_FIELD));
            System.out.println("--------------------------");
        }
    }
     
     
}
