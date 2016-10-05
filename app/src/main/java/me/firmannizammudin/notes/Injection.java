package me.firmannizammudin.notes;

import android.content.Context;

import me.firmannizammudin.notes.data.source.remote.APIService;
import me.firmannizammudin.notes.util.scheduler.BaseSchedulerProvider;
import me.firmannizammudin.notes.util.scheduler.SchedulerProvider;

/**
 * Created by Firman on 04-Oct-16.
 */

public class Injection {
    public static APIService provideNotesRepository(Context context) {
        return APIService.factory.create();
    }

    public static BaseSchedulerProvider provideSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }
}
