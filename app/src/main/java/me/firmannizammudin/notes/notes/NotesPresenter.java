package me.firmannizammudin.notes.notes;

import android.support.annotation.NonNull;

import java.util.List;

import me.firmannizammudin.notes.data.Note;
import me.firmannizammudin.notes.data.source.NotesRepository;
import me.firmannizammudin.notes.util.scheduler.BaseSchedulerProvider;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Firman on 04-Oct-16.
 */

public class NotesPresenter implements NotesContract.Presenter {

    @NonNull
    private final NotesRepository notesRepository;

    @NonNull
    private final NotesContract.View notesView;

    @NonNull
    private final BaseSchedulerProvider schedulerProvider;

    @NonNull
    private CompositeSubscription subscriptions;

    private boolean mFirstLoad = true;

    public NotesPresenter(@NonNull NotesRepository notesRepository,
                          @NonNull NotesContract.View notesView,
                          @NonNull BaseSchedulerProvider schedulerProvider) {
        this.notesRepository = notesRepository;
        this.notesView = notesView;
        this.schedulerProvider = schedulerProvider;

        subscriptions = new CompositeSubscription();
        notesView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadTasks(false);
    }

    @Override
    public void unsubscribe() {
        subscriptions.clear();
    }

    @Override
    public void result(int requestCode, int resultCode) {
    }

    @Override
    public void loadTasks(boolean forceUpdate) {
        loadTasks(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    private void loadTasks(final boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            notesView.setLoadingIndicator(true);
        }

        subscriptions.clear();
        Subscription subscription = notesRepository
                .getNotes()
                .flatMap(new Func1<List<Note>, Observable<Note>>() {
                    @Override
                    public Observable<Note> call(List<Note> tasks) {
                        return Observable.from(tasks);
                    }
                })
                .filter(new Func1<Note, Boolean>() {
                    @Override
                    public Boolean call(Note note) {
                        return true;
                    }
                })
                .toList()
                .subscribeOn(schedulerProvider.computation())
                .observeOn(schedulerProvider.ui())
                .subscribe(new Observer<List<Note>>() {
                    @Override
                    public void onCompleted() {
                        notesView.setLoadingIndicator(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<Note> tasks) {
                        processTasks(tasks);
                    }
                });
        subscriptions.add(subscription);
    }

    private void processTasks(@NonNull List<Note> notes) {
        notesView.showTasks(notes);
    }
}
