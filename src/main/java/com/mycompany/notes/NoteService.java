package com.mycompany.notes;

import java.util.List;

public class NoteService {
    private final NoteRepository repository;
    
    // Constructor
    public NoteService(NoteRepository repository) {
        this.repository = repository;
    }
    
    public void addNote(String title, String content){
        if (title == null || title.isBlank()) { // Title is required
            throw new IllegalArgumentException("Title is required");
        }
        
        Note note = new Note(null,title,content);
        
        repository.insert(note);
    }
    
    public void modifyNote(String id, String title, String content) {
        if(id == null || id.isBlank()){ // id is required
                throw new IllegalArgumentException("id is required");
            }
        
        if (title == null || title.isBlank()) { // Title is required
            throw new IllegalArgumentException("Title is required");
        }
        Note note = repository.findById(id);

        note.changeTitle(title);
        note.changeContent(content);

        repository.update(note);
    }
    
    public void deleteNote(String id){
        if(id == null || id.isBlank()){ // id is required
            throw new IllegalArgumentException("id is required");
        }
        
        repository.deleteById(id);
    }
    
    public List<Note> getNotes(){
        return repository.findAll();
    }
}