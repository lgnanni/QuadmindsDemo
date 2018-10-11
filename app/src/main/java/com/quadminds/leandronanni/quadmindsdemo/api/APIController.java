package com.quadminds.leandronanni.quadmindsdemo.api;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.quadminds.leandronanni.quadmindsdemo.pojo.Note;
import com.quadminds.leandronanni.quadmindsdemo.pojo.NotePost;
import com.quadminds.leandronanni.quadmindsdemo.pojo.Notes;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIController {

    private static final String BASE_URL = "https://quadminds-notes-test.getsandbox.com/";

    private static final String JSON_TYPE = "application/json; charset=utf-8";

    private static APIController instance;

    private QuadMindsAPI quadmindsAPI;


    public static APIController getInstance() {

        if (instance == null) {
            instance = new APIController();
            instance.start();
        }

        return instance;

    }


    public void start() {

        final OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        final Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


        quadmindsAPI = retrofit.create(QuadMindsAPI.class);
    }

    public Call<Notes> getNotes() {
        return quadmindsAPI.getNotes();
    }

    public Call<JsonObject> saveNewNote(final String title, final String content) {

        final NotePost post = new NotePost(title, content);
        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse(JSON_TYPE),
                new Gson().toJson(post));

        return quadmindsAPI.postNewNote(body);
    }

    public Call<JsonObject> saveExistingNote(final String id,
                                             final String title, final String content) {

        final NotePost post = new NotePost(title, content);
        final RequestBody body = RequestBody.create(okhttp3.MediaType.parse(JSON_TYPE),
                new Gson().toJson(post));

        return quadmindsAPI.postExistingNote(id, body);
    }

    public Call<JsonObject> deleteNote(final String id) {

        return quadmindsAPI.deleteNote(id);
    }
}