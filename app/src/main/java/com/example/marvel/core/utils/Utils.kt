package com.example.marvel.core.utils

import java.math.BigInteger
import java.math.RoundingMode
import java.security.MessageDigest
import java.text.DecimalFormat

object Utils {
    fun convertMD5(valueInput: String): String {
        val md5 = MessageDigest.getInstance("MD5")
        return BigInteger(
            1,
            md5.digest(
                valueInput.toByteArray()
            )
        )
            .toString(16)
            .padStart(32, '0')
    }

    fun roundOffDecimal(number: Double): String {
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.DOWN
        return df.format(number)
    }
}