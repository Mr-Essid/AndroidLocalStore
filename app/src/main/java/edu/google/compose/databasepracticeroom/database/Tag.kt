package edu.google.compose.databasepracticeroom.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

@Entity("tags")
data class Tag(
    @ColumnInfo("tag_id")
    @PrimaryKey(autoGenerate = true)
    val tagId: Int,
    val label: String,
)
