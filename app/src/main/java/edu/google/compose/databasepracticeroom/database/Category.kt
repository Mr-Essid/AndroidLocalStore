package edu.google.compose.databasepracticeroom.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date
import java.util.UUID

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey
    val id: UUID,
    val label: String,
    val description: String,

)
