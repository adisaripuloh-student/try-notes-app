package me.firmannizammudin.notes.notes;

import java.util.List;

import me.firmannizammudin.notes.BasePresenter;
import me.firmannizammudin.notes.BaseView;
import me.firmannizammudin.notes.data.Note;

/**
 * Created by Firman on 04-Oct-16.
 */

public interface NotesContract {

    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);

        void showNotes(List<Note> notes);
    }

    interface Presenter extends BasePresenter {
        void loadNotes(boolean forceUpdate);
    }
}
