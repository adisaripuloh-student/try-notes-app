package me.firmannizammudin.notes.data.source;

import java.util.List;

import me.firmannizammudin.notes.data.Note;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Firman on 04-Oct-16.
 */

public class NotesRepository implements NotesDataSource {

    private static NotesRepository INSTANCE = null;
    private final NotesDataSource notesRemoteDataSource;

    public NotesRepository(NotesDataSource notesRemoteDataSource) {
        this.notesRemoteDataSource = notesRemoteDataSource;
    }

    public static NotesRepository getInstance(NotesDataSource notesRemoteDataSource) {
        if (INSTANCE == null)
            INSTANCE = new NotesRepository(notesRemoteDataSource);

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Observable<List<Note>> getNotes() {
        return notesRemoteDataSource
                .getNotes()
                .flatMap(new Func1<List<Note>, Observable<List<Note>>>() {
                    @Override
                    public Observable<List<Note>> call(List<Note> tasks) {
                        return Observable.from(tasks).doOnNext(new Action1<Note>() {
                            @Override
                            public void call(Note note) {
                            }
                        }).toList();
                    }
                })
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                    }
                });
    }
}
