package com.example.routinequestcse5236

import android.app.Application
import androidx.annotation.Keep
import androidx.lifecycle.LiveData

/**
 * Single point of accessing UserAccount data in the app.
 *
 * Source: https://developer.android.com/codelabs/android-room-with-a-view
 * from TicTacToe-Kotlin
 *
 * Created by acc on 2021/08/04.
 */
@Keep
class UserAccountRepository internal constructor(application: Application) {
    private var mUserAccountDao: UserAccountDao

    // Room executes all queries on a separate thread.
    // Observed LiveData notify the observer upon data change.
    val allUserAccounts: LiveData<List<UserAccount>>

    fun findUserAccountByName(account: UserAccount): LiveData<UserAccount> {
        return mUserAccountDao.findByName(account.username, account.passwd)
    }

    // You MUST call this on a non-UI thread or the app will throw an exception.
    // I'm passing a Runnable object to the database.
    fun insert(account: UserAccount) {
        UserAccountDatabase.databaseWriteExecutor.execute {
			mUserAccountDao.insert(
				account
			)
		}
	}

	// Similarly, I'm calling update() on a non-UI thread.
	fun update(account: UserAccount) {
		UserAccountDatabase.databaseWriteExecutor.execute {
			mUserAccountDao.update(
				account
			)
		}
	}

	// Similarly, I'm calling delete() on a non-UI thread.
	fun delete(account: UserAccount) {
		UserAccountDatabase.databaseWriteExecutor.execute {
			mUserAccountDao.delete(
				account
			)
		}
	}

    init {
        val db: UserAccountDatabase = UserAccountDatabase.getDatabase(application)
        mUserAccountDao = db.userAccountDao
        allUserAccounts = mUserAccountDao.allUserAccounts
    }
}
