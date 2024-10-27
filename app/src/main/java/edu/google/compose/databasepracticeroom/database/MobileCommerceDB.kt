package edu.google.compose.databasepracticeroom.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.google.compose.databasepracticeroom.database.dao.AdminDao
import edu.google.compose.databasepracticeroom.database.dao.CategoryDao
import edu.google.compose.databasepracticeroom.database.dao.ProductDoa
import edu.google.compose.databasepracticeroom.database.dao.TagDao

@Database(
    entities = [Admin::class, Category::class, Product::class, Tag::class, TagProductCrossRef::class],
    version = 1
)
abstract class MobileCommerceDB: RoomDatabase() {
    abstract fun adminDao(): AdminDao
    abstract fun productDao(): ProductDoa
    abstract fun categoryDao(): CategoryDao
    abstract fun tagDao(): TagDao
}