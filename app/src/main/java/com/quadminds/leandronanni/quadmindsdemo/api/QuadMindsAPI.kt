package com.quadminds.leandronanni.quadmindsdemo.api

import com.google.gson.JsonObject
import com.quadminds.leandronanni.quadmindsdemo.pojo.Notes
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface QuadMindsAPI {

    @GET("notes")
    fun getNotes(): Call<Notes>

    @POST("notes")
    fun postNewNote(@Body body: RequestBody): Call<JsonObject>

    @PUT("notes/{noteId}")
    fun postExistingNote(@Path("noteId") noteId: String, @Body body: RequestBody): Call<JsonObject>

    @DELETE("notes/{noteId}")
    fun deleteNote(@Path("noteId") noteId: String): Call<JsonObject>
}
