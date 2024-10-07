package com.example.therickandmorty.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.therickandmorty.model.CharacterResponse

@Database(entities = [CharacterResponse::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteCharacterDao(): FavoriteCharacterDao
}
