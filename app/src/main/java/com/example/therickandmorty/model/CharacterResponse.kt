package com.example.therickandmorty.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "character_table", indices = [Index(value = ["id"], unique = true)])
data class CharacterResponse(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int = 0,
    @ColumnInfo(name = "name") var name: String = "",
    @ColumnInfo(name = "image") var image: String = "",
    @ColumnInfo(name = "favourite") var isFavourite: Boolean = false,

    @Ignore var status: String = "",
    @Ignore var species: String = "",
    @Ignore var gender: String = "",
    @Ignore var location: Location? = null,
    @Ignore var episode: List<String>? = null,
)


data class Location(
    val name: String,
    val url: String
)
