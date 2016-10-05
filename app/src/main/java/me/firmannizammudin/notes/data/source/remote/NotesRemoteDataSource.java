package me.firmannizammudin.notes.data.source.remote;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import me.firmannizammudin.notes.data.Note;
import me.firmannizammudin.notes.data.source.NotesDataSource;
import rx.Observable;

/**
 * Created by Firman on 04-Oct-16.
 */

public class NotesRemoteDataSource implements NotesDataSource {

    private static NotesRemoteDataSource INSTANCE;
    private static final int SERVICE_LATENCY_IN_MILLIS = 5000;
    private final static Map<String, Note> TASKS_SERVICE_DATA;

    static {
        TASKS_SERVICE_DATA = new LinkedHashMap<>(2);
        addTask("Build tower in Pisa", "Ground looks good, no foundation work required.");
        addTask("Finish bridge in Tacoma", "Found awesome girders at half the cost!");
    }

    public static NotesRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NotesRemoteDataSource();
        }
        return INSTANCE;
    }

    // Prevent direct instantiation.
    private NotesRemoteDataSource() {
    }

    private static void addTask(String title, String description) {
        Note newNote = new Note(title, description);
        TASKS_SERVICE_DATA.put(newNote.getId(), newNote);
    }

    @Override
    public Observable<List<Note>> getNotes() {
        return Observable
                .from(TASKS_SERVICE_DATA.values())
                .delay(SERVICE_LATENCY_IN_MILLIS, TimeUnit.MILLISECONDS)
                .toList();
    }
}
