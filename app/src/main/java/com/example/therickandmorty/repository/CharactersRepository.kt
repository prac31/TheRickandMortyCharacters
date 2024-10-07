package com.example.therickandmorty.repository

import androidx.lifecycle.LiveData
import com.example.therickandmorty.database.FavoriteCharacterDao
import com.example.therickandmorty.model.CharacterResponse
import com.example.therickandmorty.model.EpisodeResponse
import com.example.therickandmorty.network.ApiService
import javax.inject.Inject

class CharactersRepository @Inject constructor(private val apiService: ApiService,private val favoriteCharacterDao: FavoriteCharacterDao) {

    suspend fun getCharacters(): List<CharacterResponse> {
        return apiService.getCharacters().results
    }

    suspend fun getEpisodeById(episodeId: Int): EpisodeResponse {
        return apiService.getEpisodeById(episodeId)
    }

    fun extractEpisodeIdFromUrl(url: String): Int {
        return url.substringAfterLast("/").toInt()
    }

    suspend fun toggleFavorite(character: CharacterResponse) {
        val isFavorite = favoriteCharacterDao.getCharacterById(character.id) != null
        if (isFavorite) {
            favoriteCharacterDao.deleteCharacter(character)
        } else {
            favoriteCharacterDao.insertCharacter(character)
        }
    }


    fun getFavoriteCharacters(): LiveData<List<CharacterResponse>> {
        return favoriteCharacterDao.getAllFavoriteCharacters()
    }

    suspend fun isFavorite(characterId: Int): Boolean {
        return favoriteCharacterDao.getCharacterById(characterId) != null
    }
}
