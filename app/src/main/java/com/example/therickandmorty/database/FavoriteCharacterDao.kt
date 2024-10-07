    package com.example.therickandmorty.database
    import com.example.therickandmorty.model.CharacterResponse
    import androidx.room.Dao
    import androidx.room.Insert
    import androidx.room.OnConflictStrategy
    import androidx.room.Query
    import androidx.lifecycle.LiveData
    import androidx.room.Delete
    import androidx.room.Update

    @Dao
    interface FavoriteCharacterDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertCharacter(character: CharacterResponse)

        @Delete
        suspend fun deleteCharacter(character: CharacterResponse)

        @Query("SELECT * FROM character_table")
        fun getAllCharacters(): LiveData<List<CharacterResponse>>

        @Query("SELECT * FROM character_table")
        fun getAllFavoriteCharacters(): LiveData<List<CharacterResponse>>

        @Query("SELECT * FROM character_table WHERE id = :characterId LIMIT 1")
        suspend fun getCharacterById(characterId: Int): CharacterResponse?

    }


