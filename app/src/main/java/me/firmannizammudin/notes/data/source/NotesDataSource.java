package me.firmannizammudin.notes.data.source;

import java.util.List;

import me.firmannizammudin.notes.data.Note;
import rx.Observable;

/**
 * Created by Firman on 04-Oct-16.
 */

public interface NotesDataSource {

    Observable<List<Note>> getNotes();

}
