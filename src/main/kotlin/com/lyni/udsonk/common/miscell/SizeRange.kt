package com.lyni.udsonk.common.miscell

/**
 * Size range
 *
 * @property min min size of data
 * @property max max size of data
 * @constructor min and max size of data
 */
data class SizeRange(val min: Int, val max: Int) {
    /**
     * @param exactValue the data size in bytes, which means [min] = [max] = [exactValue]
     * @constructor data size is exactly [exactValue] bytes
     */
    constructor(exactValue: Int) : this(exactValue, exactValue)

    init {
        require(min <= max) { "min value($min) should not be greater than max value($max)" }
    }

    fun sizeCheck(size: Int) = check((size >= min) && (size <= max)) {
        "size check failed, it should be between $min and $max, but it is $size"
    }
}