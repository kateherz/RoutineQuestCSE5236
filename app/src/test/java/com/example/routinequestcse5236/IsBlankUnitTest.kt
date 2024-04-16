package com.example.routinequestcse5236

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class IsBlankUnitTest {

    @Test
    @Throws(Exception::class)
    fun isBlankValidRegularWord() {
        val test1 = "kate"
        assertEquals(false, (test1 == null || ( test1 is String  && test1.trim().length == 0)))
    }
    @Test
    @Throws(Exception::class)
    fun isBlankValidNumber() {
        val test1 = "1"
        assertEquals(false, (test1 == null || ( test1 is String  && test1.trim().length == 0)))
    }

    @Test
    @Throws(Exception::class)
    fun isBlankValidFalse() {
        val test1 = "false"
        assertEquals(false, (test1 == null || ( test1 is String  && test1.trim().length == 0)))
    }

    @Test
    @Throws(Exception::class)
    fun isBlankValidZero() {
        val test1 = "0"
        assertEquals(false, (test1 == null || ( test1 is String  && test1.trim().length == 0)))
    }

    @Test
    @Throws(Exception::class)
    fun isBlankInValidEmptyString() {
        val test1 = ""
        assertEquals(true, (test1 == null || ( test1 is String  && test1.trim().length == 0)))
    }

    @Test
    @Throws(Exception::class)
    fun isBlankInValidSpace() {
        val test1 = "     "
        assertEquals(true, (test1 == null || ( test1 is String  && test1.trim().length == 0)))
    }

    @Test
    @Throws(Exception::class)
    fun isBlankInValidNull() {
        val test1 = null
        assertEquals(true, (test1 == null || ( test1 is String  && test1?.trim()?.length ?: 0 == 0)))
    }


}