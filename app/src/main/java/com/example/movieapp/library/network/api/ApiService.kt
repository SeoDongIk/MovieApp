package com.example.movieapp.library.network.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun get(
        @HeaderMap headerMap: Map<String, String>,
        @Url url: String
    ): Response<String>

    @POST
    suspend fun post(
        @HeaderMap headerMap: Map<String, String>,
        @Url url: String,
    ): Response<String>

    @POST
    suspend fun post(
        @HeaderMap headerMap: Map<String, String>,
        @Url url: String,
        @Body body: Any
    ): Response<String>

    @PUT
    suspend fun put(
        @HeaderMap headerMap: Map<String, String>,
        @Url url: String
    ): Response<String>

    @PUT
    suspend fun put(
        @HeaderMap headerMap: Map<String, String>,
        @Url url: String,
        @Body body: Any
    ): Response<String>

    @DELETE
    suspend fun delete(
        @HeaderMap headerMap: Map<String, String>,
        @Url url: String
    ): Response<String>

    @HTTP(method = "DELETE", hasBody = true)
    suspend fun delete(
        @HeaderMap headerMap: Map<String, String>,
        @Url url: String,
        @Body body: Any
    ): Response<String>
}