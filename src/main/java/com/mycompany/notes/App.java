package com.mycompany.notes;

import java.util.Scanner;
import com.mongodb.client.MongoCollection;
import java.util.InputMismatchException;
import java.util.List;
import org.bson.Document;

public class App {
    // Instance of class MongoDBConnection
    private static MongoDBConnection mongoDBConnection;
    // Instance representing a collection
    private static MongoCollection<Document> notesCollection;
    
    private static NoteService noteService;
    private static NoteRepository repository;
    private static Scanner read;
    
    // Program entry point
    public static void main(String[] args) {
        read = new Scanner(System.in);
        byte choice; // User's choice
        
        // Connection to the database
        mongoDBConnection = new MongoDBConnection();
        notesCollection = mongoDBConnection.getDatabase().getCollection("notes");
        
        repository = new MongoNoteRepository(notesCollection);
        noteService = new NoteService(repository);
        
        do{
            showMenu();
            choice = readOption(); // User selects an option
            executeOption(choice);
        }while(choice!=5);
        
        // clean the program
        read.close();
        mongoDBConnection.closeConnection();
    }
    
    // Method to show the principal menu
    private static void showMenu(){
        System.out.println("\n ********** Notes App **********");
            System.out.println("1. Add Note");
            System.out.println("2. Edit Note");
            System.out.println("3. Delete Note");
            System.out.println("4. Show Notes");
            System.out.println("5. Exit");
            System.out.println("\n Choose an option: ");
    }
    
    private static byte readOption(){
        while(true){
            try{
                byte option = read.nextByte();
                read.nextLine();
                
                if(option<1 || option>5){
                    System.out.println("\n Invalid option. Try again:");
                    continue;
                }
                
                return option;
            }catch(InputMismatchException e){
                read.nextLine(); // cleaning the input
                System.out.println("\n Invalid input. Enter a number.");
            }
        }
    }
    
    // Method to validate user's choice
    private static void executeOption(byte choice){
            switch(choice){
                case 1:
                    addNote();
                    break;
                case 2:
                    modifyNote();
                    break;
                case 3:
                    deleteNote();
                    break;
                case 4:
                    showNotes();
                    break;
                case 5:
                    System.out.println("\n Bye");
                    break;
            }
    }
    
    // Method to add a note
    private static void addNote(){
        // Ask the user for data
        System.out.println("\n Title:");
        String title = read.nextLine();
        System.out.println("\n Content:");
        String content = read.nextLine();
        
        try{
            noteService.addNote(title, content);
            System.out.println("\n Note successfully added");
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    // Method that allows the user to modify a task
    private static void modifyNote() {
        // Ask the user for the note
        String id = selectNote();
        if(id == null){ // Validate exit early
            return;
        }
        
        // Asks the user for new data
        System.out.println("\n Enter the new data");
        System.out.print("\n New title: ");
        String newTitle = read.nextLine();
        System.out.print("\n New content: ");
        String newContent = read.nextLine();

        try{
            noteService.modifyNote(id, newTitle, newContent);
            System.out.println("\n Note successfully modified");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    // Method to delete a note
    private static void deleteNote() {
        // Ask the user for the note
        String id = selectNote();
        if(id == null){ // Validate exit early
            return;
        }
        
        try{
            noteService.deleteNote(id);
            System.out.println("\n Note successfully deleted");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    // Method to show the content of all notes
    private static void showNotes(){
         List<Note> notes = noteService.getNotes();
         
         if(notes.isEmpty()){
            System.out.println("\n No notes available.");
            return;
        }
         
         for(Note note : notes){
             System.out.println(note.toString());
         }
     }
    
    // Method to user can select a note
     private static String selectNote() {
        List<Note> notes = noteService.getNotes();
        if(notes.isEmpty()){
            System.out.println("\n No notes available.");
            return null;
        }
        
        // Notes are displayed
        System.out.println("\n Notes:");
        for (int i = 0; i < notes.size(); i++) {
            System.out.println((i + 1) + ") " + notes.get(i).getTitle());
        }
        System.out.println((notes.size()+1) + ") cancel");
        
        // User selects an option
         System.out.println("\n Select an option: ");
         int option = read.nextInt();
         read.nextLine();
         
         if(option < 1 || option > notes.size()){
             System.out.println("\n Invalid option.");
             return null;
         }
         
         if(option == (notes.size()+1)){
             return null;
         }
         
         return notes.get(option-1).getId();
    }
}
