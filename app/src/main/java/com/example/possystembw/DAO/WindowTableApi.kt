package com.example.possystembw.DAO

import com.example.possystembw.database.WindowTable
import retrofit2.Response
import retrofit2.http.GET

interface WindowTableApi {
    @GET("api/windowtable/get-all-tables")
    suspend fun getWindowTables(): Response<List<WindowTable>>
}