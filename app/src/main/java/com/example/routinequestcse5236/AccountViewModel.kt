package com.example.routinequestcse5236

import android.app.Application
import android.util.Log
import androidx.annotation.Keep
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

private const val TAG = "AccountViewModel"
@Keep
class AccountViewModel(application: Application) : AndroidViewModel(application) {
    private val uRepository: UserAccountRepository = UserAccountRepository(application)
    var allUserAccounts: LiveData<List<UserAccount>> = uRepository.allUserAccounts

    fun containsUserAccount(userAccount: UserAccount): Boolean {
        var accountInList = false
        val userAccountLiveData = uRepository.findUserAccountByName(userAccount)
        val theUserAccount = userAccountLiveData.value ?: return false
        if (theUserAccount.username == userAccount.username && theUserAccount.passwd == userAccount.passwd) {
            accountInList = true
        }
        return accountInList
    }
    fun getUserAccount(userAccount: UserAccount): LiveData<UserAccount> {
        return uRepository.findUserAccountByName(userAccount)
    }

    fun insert(userAccount: UserAccount) {
        uRepository.insert(userAccount)
        allUserAccounts = uRepository.allUserAccounts
    }
    init {
        Log.d(TAG,"ViewModel instance created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance about to be destroyed")
    }
}