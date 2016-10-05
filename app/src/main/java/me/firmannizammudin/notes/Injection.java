package me.firmannizammudin.notes;

import android.content.Context;
import android.support.annotation.NonNull;

import me.firmannizammudin.notes.data.source.NotesRepository;
import me.firmannizammudin.notes.data.source.remote.NotesRemoteDataSource;
import me.firmannizammudin.notes.util.scheduler.BaseSchedulerProvider;
import me.firmannizammudin.notes.util.scheduler.SchedulerProvider;

/**
 * Created by Firman on 04-Oct-16.
 */

public class Injection {
    public static NotesRepository provideTasksRepository(@NonNull Context context) {
        return NotesRepository.getInstance(NotesRemoteDataSource.getInstance());
    }

    public static BaseSchedulerProvider provideSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }
}
