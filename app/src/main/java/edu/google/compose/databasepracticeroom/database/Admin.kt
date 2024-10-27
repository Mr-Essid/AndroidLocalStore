package edu.google.compose.databasepracticeroom.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date
import java.util.UUID


@Entity(tableName = "admins")
data class Admin(
    @PrimaryKey
    val id: UUID,
    val username: String /* unique */,
    val name: String,
    val lastname: String,
    val password: String,
    val email: String,
    @ColumnInfo("image_file_path")
    val imageFilePath: String,
    @ColumnInfo("phone_number")
    val phoneNumber: String?,

    )
