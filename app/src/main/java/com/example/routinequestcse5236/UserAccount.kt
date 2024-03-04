package com.example.routinequestcse5236

data class UserAccount (val username: String, val passwd: String){


    fun getName(): String? {
        return username
    }

    fun getPassword(): String? {
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
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + passwd.hashCode()
        return result
    }
}

