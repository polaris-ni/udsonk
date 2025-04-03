package com.lyni.udsonk.common.util

object NumberExtensions {
    fun Int.byteOf(index: Int) = when (index) {
        0 -> this.ushr(24).and(0xFF).toByte()
        1 -> this.ushr(16).and(0xFF).toByte()
        2 -> this.ushr(8).and(0xFF).toByte()
        3 -> this.and(0xFF).toByte()
        else -> throw IndexOutOfBoundsException("index $index out of bounds, it should be between 0 and 3")
    }

    fun Short.highByte() = this.toInt().ushr(8).and(0xFF).toByte()

    fun Short.lowByte() = this.toByte()

    fun UShort.highByte() = this.toInt().ushr(8).and(0xFF).toByte()

    fun UShort.lowByte() = this.toByte()
}