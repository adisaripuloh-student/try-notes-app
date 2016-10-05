package me.firmannizammudin.notes.notes;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import me.firmannizammudin.notes.data.Note;
import me.firmannizammudin.notes.data.source.remote.APIService;
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

    private final APIService notesRepository;
    private final NotesContract.View notesView;
    private final BaseSchedulerProvider schedulerProvider;
    private CompositeSubscription subscriptions;
    private boolean mFirstLoad = true;

    public NotesPresenter(APIService notesRepository,
                          NotesContract.View notesView,
                          BaseSchedulerProvider schedulerProvider) {
        this.notesRepository = notesRepository;
        this.notesView = notesView;
        this.schedulerProvider = schedulerProvider;

        subscriptions = new CompositeSubscription();
        notesView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadNotes(false);
    }

    @Override
    public void unsubscribe() {
        subscriptions.clear();
    }

    @Override
    public void loadNotes(boolean forceUpdate) {
        loadTasks(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    private void loadTasks(final boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            notesView.setLoadingIndicator(true);
        }

        subscriptions.clear();
        Subscription subscription = notesRepository.getNotes()
                .filter(new Func1<Note.Notes, Boolean>() {
                    @Override
                    public Boolean call(Note.Notes notes) {
                        return true;
                    }
                })
                .subscribeOn(schedulerProvider.computation())
                .observeOn(schedulerProvider.ui())
                .subscribe(new Observer<Note.Notes>() {
                    @Override
                    public void onCompleted() {
                        notesView.setLoadingIndicator(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("ERROR PRESENTER", "BOOM " + e.getMessage());
                    }

                    @Override
                    public void onNext(Note.Notes notes) {
                        processTasks(notes.getData());
                    }
                });

        subscriptions.add(subscription);
    }

    private void processTasks(List<Note> notes) {
        notesView.showNotes(notes);
    }
}
