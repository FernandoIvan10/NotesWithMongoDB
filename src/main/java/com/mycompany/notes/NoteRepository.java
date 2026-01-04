package com.mycompany.notes;

import java.util.List;

public interface NoteRepository {
    Note findById(String id);
    List<Note> findAll();
    void insert(Note note);
    void update(Note note);
    void deleteById(String id);
}