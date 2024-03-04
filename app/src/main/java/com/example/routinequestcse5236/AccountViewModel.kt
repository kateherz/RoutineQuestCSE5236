package com.example.routinequestcse5236

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

private const val TAG = "AccountViewModel"
class AccountViewModel : ViewModel() {
    init {
        Log.d(TAG,"ViewModel instance created")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance about to be destroyed")
    }
    //live data list that contains all the user accounts
    private val mAllUserAccounts: LiveData<List<UserAccount>>? = null
}