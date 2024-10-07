package com.example.therickandmorty.network

import com.example.therickandmorty.model.CharacterResponse
import com.example.therickandmorty.model.CharacterResponseWrapper
import com.example.therickandmorty.model.EpisodeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("character")
    suspend fun getCharacters(): CharacterResponseWrapper

    @GET("episode/{id}")
    suspend fun getEpisodeById(@Path("id") id: Int): EpisodeResponse
}
