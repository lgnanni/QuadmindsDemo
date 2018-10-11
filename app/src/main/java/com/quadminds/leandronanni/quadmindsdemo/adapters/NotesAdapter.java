package com.quadminds.leandronanni.quadmindsdemo.adapters;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quadminds.leandronanni.quadmindsdemo.R;
import com.quadminds.leandronanni.quadmindsdemo.pojo.Note;
import com.quadminds.leandronanni.quadmindsdemo.pojo.Notes;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Note> notes;
    private Context context;
    private NotesAdapterListener listener;

    private int totalSize;
    private Handler handler;

    public NotesAdapter(final Context context, final NotesAdapterListener listener,
                        final List<Note> notes, final int totalSize) {
        this.context = context;
        this.listener = listener;
        this.notes = notes;
        this.totalSize = totalSize;

        handler = new Handler();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        final View view = LayoutInflater.from(context).inflate(R.layout.notes_item, null);

        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        final NotesViewHolder vh = (NotesViewHolder) holder;

        if (TextUtils.isEmpty(notes.get(position).getTitle())) {
            if (position < totalSize - 1) {
                modify(position);
            } else {
                remove(notes.get(position));
            }
        }

        final Note note = notes.get(position);


        vh.title.setText(note.getTitle());

        vh.content.setText(TextUtils.isEmpty(note.getContent())
                ? "" : note.getContent());

        vh.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.openNoteDialog(note);
            }
        });

        vh.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {

                listener.openNoteRemoveDialog(note);

                return false;
            }
        });
    }


    public void setNotes(final Notes notes) {

        clear();

        addAll(notes.getData());
    }
    @Override
    public int getItemCount() {
        return notes == null ? 0 : notes.size();
    }

    public static class NotesViewHolder extends RecyclerView.ViewHolder {

        public final TextView title;
        public final TextView content;
        public final View view;

        public NotesViewHolder (final View itemView) {
            super(itemView);

            this.view = itemView.findViewById(R.id.note_layout);
            this.title = itemView.findViewById(R.id.note_title);
            this.content= itemView.findViewById(R.id.note_content);
        }
    }


    public void add(final Note aNote) {
        notes.add(aNote);
        handler.post(new Runnable() {
            @Override
            public void run() {
                notifyItemInserted(notes.size() - 1);
            }
        });

    }

    public void addAll(final List<Note> newsList) {
        for (final Note aNote : newsList) {
            add(aNote);
        }
    }

    public void remove(final Note aNote) {
        final int position = notes.indexOf(aNote);
        if (position > -1) {
            notes.remove(position);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    notifyItemRemoved(position);
                }
            });

        }
    }

    public void modify(final int index) {
        if (index > -1) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    notifyItemChanged(index);
                }
            });

        }
    }

    private void clear() {
        while (getItemCount() > 0) {
            remove(notes.get(getItemCount() - 1));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public Note getItem(final int position) {
        return notes.get(position);
    }
}

