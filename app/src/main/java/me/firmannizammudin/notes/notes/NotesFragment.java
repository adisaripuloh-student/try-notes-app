package me.firmannizammudin.notes.notes;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
        listAdapter = new NotesAdapter(getActivity(), new ArrayList<Note>(0));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notes, container, false);

        RecyclerView rv = (RecyclerView) root.findViewById(R.id.notes_rv);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setAdapter(listAdapter);
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

        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
    }

    @Override
    public void showNotes(List<Note> notes) {
        listAdapter.replaceData(notes);
    }

    @Override
    public void setPresenter(NotesContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private static class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

        private List<Note> dataSet;
        private Context context;

        public class ViewHolder extends RecyclerView.ViewHolder {
            private CheckBox cbComplete;
            private TextView txtTitle;

            public ViewHolder(View v) {
                super(v);
                cbComplete = (CheckBox) v.findViewById(R.id.notes_cb_complete);
                txtTitle = (TextView) v.findViewById(R.id.notes_txt_title);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
            }
        }

        public NotesAdapter(Context context, List<Note> dataSet) {
            this.context = context;
            this.dataSet = dataSet;
        }

        @Override
        public NotesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context)
                    .inflate(R.layout.note_row_item, parent, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(NotesAdapter.ViewHolder holder, int position) {
            Note note = dataSet.get(position);
            holder.txtTitle.setText(note.getTitle());
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }

        public void replaceData(List<Note> notes) {
            this.dataSet = notes;
            notifyDataSetChanged();
        }
    }
}
