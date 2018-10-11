package com.quadminds.leandronanni.quadmindsdemo.adapters;

import com.quadminds.leandronanni.quadmindsdemo.pojo.Note;

public interface NotesAdapterListener {

    void openNoteDialog(final Note note);
    void openNoteRemoveDialog(final Note note);
}
