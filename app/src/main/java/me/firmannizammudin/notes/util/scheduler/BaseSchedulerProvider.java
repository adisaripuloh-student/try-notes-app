package me.firmannizammudin.notes.util.scheduler;

import android.support.annotation.NonNull;

import rx.Scheduler;

/**
 * Created by Firman on 04-Oct-16.
 */

public interface BaseSchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}
