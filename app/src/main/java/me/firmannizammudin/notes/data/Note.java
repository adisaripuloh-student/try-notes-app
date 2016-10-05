package me.firmannizammudin.notes.data;

import java.util.List;

/**
 * Created by Firman on 04-Oct-16.
 */

public class Note {
    private String id;
    private String title;
    private String description;
    private boolean completed;

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Note(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Note(String id, String title, String description, boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean isActive() {
        return !completed;
    }

    public boolean isEmpty() {
        return (title == null || title.equals("")) &&
                (description == null || description.equals(""));
    }

    @Override
    public String toString() {
        return "Task with title " + title;
    }

    public class Notes extends BaseModel {
        private List<Note> data;

        public List<Note> getData() {
            return data;
        }
    }
}
