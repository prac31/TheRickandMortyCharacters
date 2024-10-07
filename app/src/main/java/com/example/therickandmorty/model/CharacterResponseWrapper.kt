package com.example.therickandmorty.model

data class CharacterResponseWrapper(
    val info: Info,
    val results: List<CharacterResponse>
)

data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)
