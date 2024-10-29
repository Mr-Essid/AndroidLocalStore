package edu.google.compose.databasepracticeroom.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import edu.google.compose.databasepracticeroom.database.Category
import edu.google.compose.databasepracticeroom.database.Product
import edu.google.compose.databasepracticeroom.database.TagProduct
import java.util.UUID


@Dao
interface ProductDoa {
    @Insert
    suspend fun insertProduct(product: Product)
    @Update
    suspend fun updateProduct(product: Product): Int
    @Delete
    suspend fun deleteProduct(product: Product): Int

    @Query("SELECT * FROM products")
    suspend fun productAll(): List<Product>

    @Query("SELECT * FROM products WHERE product_id = :id")
    suspend fun productFindById(id: UUID): List<Product>

    @Query("SELECT * FROM categories JOIN products ON categories.id = products.category_id")
    suspend fun categoryProducts(): Map<Category, List<Product>>

    @Transaction
    @Query("SELECT * FROM products")
    suspend fun productsWithTags(): List<TagProduct>

    @Transaction
    @Query("SELECT * FROM products WHERE admin_id = :userId")
    suspend fun productsWithTagsOfUser(userId: UUID): List<TagProduct>
}


