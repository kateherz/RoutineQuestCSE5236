package com.example.routinequestcse5236

import android.app.Application

class AccountApplication : Application() {
        // Using by lazy so the database and the repository are only created when they're needed
        // rather than when the application starts
        val database by lazy { UserAccountRoomDatabase.getDatabase(this) }
        val repository by lazy { AccountRepository(database.accountDao()) }
    }