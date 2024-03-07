package com.example.routinequestcse5236

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AccountDao {

    @Query("SELECT * FROM user_table ORDER BY username ASC")
    fun getAlphabetizedWords(): List<UserAccount>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(userAccount: UserAccount)

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()
}