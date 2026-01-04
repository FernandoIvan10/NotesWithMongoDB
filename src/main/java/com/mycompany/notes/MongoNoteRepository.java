package com.mycompany.notes;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.eq;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

public class MongoNoteRepository implements NoteRepository{
    private final MongoCollection<Document> collection;
    // constants representing the names of fields and the collection itself
    private static final String TITLE_FIELD = "title";
    private static final String CONTENT_FIELD = "content";
    private static final String CREATION_DATE_FIELD = "createdAt";
    private static final String UPDATE_DATE_FIELD = "updateAt";

    public MongoNoteRepository(MongoCollection<Document> collection) {
        this.collection = collection;
    }
    
    
    @Override
    public Note findById(String id) {
        Document doc = collection.find(eq("_id", new ObjectId(id))).first();
        if(doc == null){
            throw new RuntimeException("Note not found");
        }
        return mapToNote(doc);
    }

    @Override
    public List<Note> findAll() {
        List<Note> notes = new ArrayList<>();
        for(Document doc : collection.find()){
            notes.add(mapToNote(doc));
        }
        return notes;
    }

    @Override
    public void insert(Note note){
        try{
            Document doc = new Document()
                .append(TITLE_FIELD, note.getTitle())
                .append(CONTENT_FIELD, note.getContent())
                .append(CREATION_DATE_FIELD, Instant.now())
                .append(UPDATE_DATE_FIELD, Instant.now());

            collection.insertOne(doc);
        }catch(MongoException e){
            throw new RuntimeException("Could not save note");
        }
    }
    
    @Override
    public void update(Note note) {
        try{
            Document doc = mapToDocument(note);
            collection.replaceOne(eq("_id", new ObjectId(note.getId())), doc);
        }catch(MongoException e){
            throw new RuntimeException("Could not modify note");
        }
    }
    
    @Override
    public void deleteById(String id) {
        collection.deleteOne(eq("_id", new ObjectId(id)));
    }
    
    // Mappers
    private Note mapToNote(Document doc) {
        return new Note(
            doc.getObjectId("_id").toHexString(),
            doc.getString(TITLE_FIELD),
            doc.getString(CONTENT_FIELD),
            doc.getDate(CREATION_DATE_FIELD).toInstant(),
            doc.getDate(UPDATE_DATE_FIELD).toInstant()
        );
    }

    private Document mapToDocument(Note note) {
        return new Document("_id", new ObjectId(note.getId()))
            .append(TITLE_FIELD, note.getTitle())
            .append(CONTENT_FIELD, note.getContent())
            .append(CREATION_DATE_FIELD, Date.from(note.getCreatedAt()))
            .append(UPDATE_DATE_FIELD, Date.from(note.getUpdatedAt()));
    }
}
