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

    fun shortValueOf(high: Byte, low: Byte): Short {
        return high.toInt().and(0xff).shl(8).or(low.toInt().and(0xff)).toShort()
    }

    fun intValueOf(byte0: Byte, byte1: Byte, byte2: Byte, byte3: Byte): Int {
        return byte0.toInt().shl(24)
            .or(byte1.toInt().shl(16))
            .or(byte2.toInt().shl(8))
            .or(byte3.toInt())
    }

    @OptIn(ExperimentalStdlibApi::class)
    val UPPER_HEX_FORMAT: HexFormat = HexFormat {
        upperCase = true
        bytes.byteSeparator = " "
    }
}