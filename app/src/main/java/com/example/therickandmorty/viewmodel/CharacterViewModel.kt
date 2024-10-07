package com.example.therickandmorty.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.therickandmorty.model.CharacterResponse
import com.example.therickandmorty.repository.CharactersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: CharactersRepository
) : ViewModel() {

    private val _characters = MutableLiveData<List<Pair<CharacterResponse, String>>>()
    val characters: LiveData<List<Pair<CharacterResponse, String>>> get() = _characters
    private val _filteredCharacters = MutableLiveData<List<Pair<CharacterResponse, String>>>()
    val filteredCharacters: LiveData<List<Pair<CharacterResponse, String>>> get() = _filteredCharacters

    val favoriteCharacters: LiveData<List<CharacterResponse>> = repository.getFavoriteCharacters()
    var showLoader = MutableLiveData(false)


    fun fetchCharacters() {
        viewModelScope.launch {
            showLoader.value = true
            try {
                val charactersList = repository.getCharacters()
                val charactersWithFirstEpisode = charactersList.map { character ->
                    val firstEpisodeUrl = character.episode?.firstOrNull()
                    val firstEpisodeName = firstEpisodeUrl?.let {
                        val episodeId = repository.extractEpisodeIdFromUrl(it)
                        val episode = repository.getEpisodeById(episodeId)
                        episode.name
                    } ?: "Unknown Episode"

                    Pair(character, firstEpisodeName)
                }

                _characters.postValue(charactersWithFirstEpisode)
                _filteredCharacters.postValue(charactersWithFirstEpisode)
                showLoader.value = false
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun toggleFavorite(character: CharacterResponse) {
        viewModelScope.launch {
            repository.toggleFavorite(character)
            refreshFilteredCharacters()
        }
    }

    fun filterCharacters(query: String) {
        val filteredList = characters.value?.filter {
            it.first.name.contains(query, ignoreCase = true)
        }
        _filteredCharacters.value = filteredList ?: emptyList()
    }

    private fun refreshFilteredCharacters() {
        viewModelScope.launch {
            _filteredCharacters.value = _filteredCharacters.value?.map { pair ->
                val isFavorite = repository.isFavorite(pair.first.id)
                pair.copy(first = pair.first.copy(isFavourite = isFavorite))
            }
        }
    }
}

