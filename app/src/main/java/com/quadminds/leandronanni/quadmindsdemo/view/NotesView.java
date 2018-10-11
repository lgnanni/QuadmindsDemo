package com.quadminds.leandronanni.quadmindsdemo.view;

import com.quadminds.leandronanni.quadmindsdemo.pojo.Note;
import com.quadminds.leandronanni.quadmindsdemo.pojo.Notes;

public interface NotesView {

    void showProgress();

    void hideProgress();

    void getNotesOk(final Notes notes);

    void saveNoteOk(final Note note);

    void removeNoteOk();

    void apiError(final String error);
}
