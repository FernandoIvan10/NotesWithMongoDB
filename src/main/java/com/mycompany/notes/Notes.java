package com.mycompany.notes;

import java.util.Date;

/**
 *
 * @author Fernando Iván Ascencio Cortés
 */

public class Notes {
 // Variables
    // Note identifier
    private String id;
    // Note title
    private String title;
    // Note content
    private String content;
    // Note creation date
    private Date createdAt;
    // Last modified date of the note
    private Date updatedAt;

    // Constructor
    public Notes(String id, String title, String content) {
     // Initialization of variables
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = new Date();
        this.updatedAt = this.createdAt;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        // Modification date is updated
        this.updatedAt = new Date(); 
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
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
