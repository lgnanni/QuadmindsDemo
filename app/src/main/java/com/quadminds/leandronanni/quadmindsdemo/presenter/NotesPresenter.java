package com.quadminds.leandronanni.quadmindsdemo.presenter;

import com.quadminds.leandronanni.quadmindsdemo.model.NotesModel;
import com.quadminds.leandronanni.quadmindsdemo.pojo.Note;
import com.quadminds.leandronanni.quadmindsdemo.pojo.Notes;
import com.quadminds.leandronanni.quadmindsdemo.view.NotesView;

public class NotesPresenter {

    private final NotesView view;
    private final NotesModel model;

    public NotesPresenter(final NotesView view) {
        this.view = view;
        this.model = new NotesModel(this);
    }

    public void getNotes() {
        view.showProgress();
        model.getNotes();
    }

    public void getNotesOk(final Notes notes) {
        view.hideProgress();
        view.getNotesOk(notes);
    }

    public void apiError(final String error) {
        view.hideProgress();
        view.apiError(error);
    }

    public void saveNewNote(final String title, final String content) {
        model.saveNewPost(title, content);
    }

    public void modifyNote(final String id, final String title, final String content) {
        model.saveExistingPost(id, title, content);
    }

    public void saveNoteOk(final Note note) {
        view.saveNoteOk(note);
    }

    public void deleteNote(final String noteId) {
        model.deleteNote(noteId);
    }

    public void deleteOk() {
        view.removeNoteOk();
    }

}
