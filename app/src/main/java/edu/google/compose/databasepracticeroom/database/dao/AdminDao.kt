package edu.google.compose.databasepracticeroom.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import edu.google.compose.databasepracticeroom.database.Admin
import edu.google.compose.databasepracticeroom.database.Product
import java.util.UUID

@Dao
interface AdminDao {

    @Insert
    suspend fun insertAdmin(admin: Admin)
    @Update
    suspend fun updateAdmin(admin: Admin)
    @Delete
    suspend fun deleteAdmin(admin: Admin)

    @Query("SELECT * FROM admins")
    suspend fun adminAll(): List<Admin>

    @Query("SELECT * FROM admins WHERE id = :id")
    suspend fun adminFindById(id: UUID): Admin


    @Query("SELECT * FROM admins WHERE username = :query or email = :query")
    suspend fun getAdminByUsernameOrEmail(query: String): Admin?

    @Query("SELECT * FROM admins WHERE email = :query")
    suspend fun getAdminByEmail(query: String): Admin?

    @Query("SELECT * FROM admins WHERE username = :query")
    suspend fun getAdminByUsername(query: String): Admin?
}