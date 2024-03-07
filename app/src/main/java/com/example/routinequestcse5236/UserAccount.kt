package com.example.routinequestcse5236

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="user_table")
data class UserAccount(@PrimaryKey(autoGenerate = true)val id: Int, @ColumnInfo(name = "username")val username: String, @ColumnInfo(name = "password")val passwd: String) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as UserAccount
        return username == that.username && passwd == that.passwd
    }
    override fun toString(): String {
        return "UserAccount{" +
                "; name='" + username + '\'' +
                "; password='" + passwd + '\'' +
                '}'
    }
}
