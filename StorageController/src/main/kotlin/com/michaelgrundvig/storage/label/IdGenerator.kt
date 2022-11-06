package com.michaelgrundvig.storage.label

import java.util.*

// TODO: remove this soon as it doesn't look like it's going to be needed in the end
class IdGenerator {

    private val maxIterations = 1000

    private val digits = listOf(
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
        "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" )
    private val rand = Random()


    fun generateCode(list:List<String> = listOf()): String {
        for (i in 1..maxIterations) {
            val code = String.format(
                "%s%s%s%s%s",
                digits[rand.nextInt(digits.size)],
                digits[rand.nextInt(digits.size)],
                digits[rand.nextInt(digits.size)],
                digits[rand.nextInt(digits.size)],
                digits[rand.nextInt(digits.size)]
            )
            if(!list.contains(code)) {
                return code;
            }
        }
        throw RuntimeException("Unable to generate a unique code after $maxIterations attempts")
    }
}