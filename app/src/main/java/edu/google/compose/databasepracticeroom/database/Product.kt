package edu.google.compose.databasepracticeroom.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date
import java.util.UUID

@Entity("products")
data class Product(
    @PrimaryKey
    @ColumnInfo("product_id")
    val productId: UUID,
    @ColumnInfo("admin_id")
    val adminId: UUID,
    @ColumnInfo("category_id")
    val categoryId: UUID,
    val label: String,
    val description: String,
    @ColumnInfo("image_path")

    val imagePath: String,

)
