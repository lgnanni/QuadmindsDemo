package com.quadminds.leandronanni.quadmindsdemo.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.quadminds.leandronanni.quadmindsdemo.api.APIController;
import com.quadminds.leandronanni.quadmindsdemo.pojo.Note;
import com.quadminds.leandronanni.quadmindsdemo.pojo.NotePost;
import com.quadminds.leandronanni.quadmindsdemo.pojo.Notes;
import com.quadminds.leandronanni.quadmindsdemo.presenter.NotesPresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotesModel {

    private NotesPresenter presenter;

    public NotesModel(final NotesPresenter presenter) {
        this.presenter = presenter;
    }

    public void getNotes() {
        APIController.getInstance().getNotes().enqueue(new Callback<Notes>() {
            @Override
            public void onResponse(final Call<Notes> call, final Response<Notes> response) {
                if (response.isSuccessful()) {
                    presenter.getNotesOk(response.body());
                } else {
                    presenter.apiError(response.message());
                }
            }

            @Override
            public void onFailure(final Call<Notes> call, final Throwable t) {
                presenter.apiError(t.getLocalizedMessage());
            }
        });
    }

    public void saveNewPost(final String title, final String content) {
        APIController.getInstance().saveNewNote(title, content).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {

                    final Note note = new Gson().fromJson(response.body().get("data"), Note.class);
                    presenter.saveNoteOk(note);
                } else {
                    presenter.apiError(response.message());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                presenter.apiError(t.getLocalizedMessage());
            }
        });
    }


    public void saveExistingPost(final String id, final String title, final String content) {
        APIController.getInstance().saveExistingNote(id, title, content).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {

                    final Note note = new Gson().fromJson(response.body().get("data"), Note.class);

                    presenter.saveNoteOk(note);
                } else {
                    presenter.apiError(response.message());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                presenter.apiError(t.getLocalizedMessage());
            }
        });
    }

    public void deleteNote(final String id) {
        APIController.getInstance().deleteNote(id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()) {
                    presenter.deleteOk();
                } else {
                    presenter.apiError(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                presenter.apiError(t.getLocalizedMessage());
            }
        });
    }
}
