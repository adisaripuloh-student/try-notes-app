package me.firmannizammudin.notes.notes;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.firmannizammudin.notes.R;
import me.firmannizammudin.notes.data.Note;


public class NotesFragment extends Fragment implements NotesContract.View {

    private NotesContract.Presenter presenter;

    private NotesAdapter listAdapter;

    public NotesFragment() {
    }

    public static NotesFragment newInstance() {
        return new NotesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listAdapter = new NotesAdapter(new ArrayList<Note>(0));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notes, container, false);

        ListView listView = (ListView) root.findViewById(R.id.notes_lv);
        listView.setAdapter(listAdapter);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        if (getView() == null) {
            return;
        }
        final SwipeRefreshLayout srl =
                (SwipeRefreshLayout) getView().findViewById(R.id.notes_sr);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
    }

    @Override
    public void showTasks(List<Note> notes) {
        listAdapter.replaceData(notes);
    }

    @Override
    public void setPresenter(NotesContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private static class NotesAdapter extends BaseAdapter {

        private List<Note> notes;

        public NotesAdapter(List<Note> notes) {
            setList(notes);
        }

        public void replaceData(List<Note> notes) {
            setList(notes);
            notifyDataSetChanged();
        }

        private void setList(List<Note> notes) {
            this.notes = notes;
        }

        @Override
        public int getCount() {
            return notes.size();
        }

        @Override
        public Note getItem(int i) {
            return notes.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View rowView = view;
            if (rowView == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                rowView = inflater.inflate(R.layout.note_row_item, viewGroup, false);
            }

            final Note note = getItem(i);

            TextView titleTV = (TextView) rowView.findViewById(R.id.notes_txt_title);
            titleTV.setText(note.getTitle());

            CheckBox completeCB = (CheckBox) rowView.findViewById(R.id.notes_cb_complete);

            completeCB.setChecked(note.isCompleted());
            if (note.isCompleted()) {
            } else {
            }

            completeCB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });

            return rowView;
        }
    }

}
