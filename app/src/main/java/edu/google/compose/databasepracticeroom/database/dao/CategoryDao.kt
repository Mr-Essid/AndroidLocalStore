package edu.google.compose.databasepracticeroom.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import edu.google.compose.databasepracticeroom.database.Category
import edu.google.compose.databasepracticeroom.database.Product
import java.util.UUID


@Dao
interface CategoryDao {
    @Insert
    suspend fun insertCategory(category: Category)
    @Update
    suspend fun updateCategory(category: Category)
    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("SELECT * FROM categories")
    suspend fun categoryAll(): List<Category>

    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun categoryFindById(id: UUID): Category


    @Query("SELECT * FROM categories JOIN products ON categories.id = products.category_id")
    suspend fun categoryProducts(): Map<Category, List<Product>>
}