package com.mycompany.notes;

import java.time.Instant;

public class Note {
    // Attributes
    private final String id;
    private String title;
    private String content;
    private final Instant createdAt;
    private Instant updatedAt;

    // Constructors
    public Note(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    public Note(String id, String title, String content, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters
    public String getId() {
        return id;
    }
    
    public String getTitle(){
        return title;
    }
    
    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public String getContent() {
        return content;
    }
    
    // Methods
    public void changeTitle(String title) {
        if(title == null || title.isBlank()){
            throw new IllegalArgumentException("Title is required");
        }
        
        this.title = title;
        touch(); // Modification date is updated
    }

    public void changeContent(String content) {
        this.content = content;
        touch(); // Modification date is updated
    }
    
    // Method to change the update date
    private void touch() {
        this.updatedAt = Instant.now();
    }

    @Override
    public String toString() {
        return "Notes{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
