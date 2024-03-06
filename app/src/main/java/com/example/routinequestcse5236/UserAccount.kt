package com.example.routinequestcse5236

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey
import java.util.Objects
@Fts4
@Keep
@Entity(tableName = "useraccount")
data class UserAccount (@field:ColumnInfo(name = "name") var username: String, @field:ColumnInfo(name = "password") var passwd: String){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    var rowid = 0

    fun getName(): String {
        return username
    }

    fun getPassword(): String {
        return passwd
    }

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
        //TO DO: add rowId
    }

    override fun hashCode(): Int {
        return Objects.hash(rowid, username, passwd)
    }
}

