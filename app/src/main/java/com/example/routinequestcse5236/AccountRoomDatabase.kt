package com.example.routinequestcse5236

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(UserAccount::class), version = 1, exportSchema = false)
public abstract class UserAccountRoomDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: UserAccountRoomDatabase? = null

        fun getDatabase(context: Context): UserAccountRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserAccountRoomDatabase::class.java,
                    "user_account_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
