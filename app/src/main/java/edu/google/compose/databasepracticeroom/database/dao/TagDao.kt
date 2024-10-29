package edu.google.compose.databasepracticeroom.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import edu.google.compose.databasepracticeroom.database.Tag
import edu.google.compose.databasepracticeroom.database.Product
import java.util.UUID


@Dao
interface TagDao {
    @Insert
    suspend fun insertTag(tag: Tag)
    @Update
    suspend fun updateTag(tag: Tag)
    @Delete
    suspend fun deleteTag(tag: Tag)

    @Query("SELECT * FROM tags")
    suspend fun tagAll(): List<Tag>

    @Query("SELECT * FROM tags WHERE tag_id = :id")
    suspend fun tagFindById(id: Int): Tag
}
