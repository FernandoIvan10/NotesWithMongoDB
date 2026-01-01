package com.mycompany.notes;

import java.time.Instant;

public class Note {
    // Attributes
    private final String id;
    private String title;
    private String content;
    private final Instant createdAt;
    private Instant updatedAt;

    // Constructor
    public Note(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }
    
    // Method to change the update date
    private void touch() {
        this.updatedAt = Instant.now();
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        // Modification date is updated
        touch(); 
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        // Modification date is updated
        touch(); 
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
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
