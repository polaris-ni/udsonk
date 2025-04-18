@file:JvmName("NumberExtensions")
package com.lyni.udsonk.common.io

import java.nio.ByteBuffer
import java.util.*

object DirectBufferPool {
    private const val MAX_POOL_SIZE = 1024

    private val pool = LinkedList<ByteBuffer>()

    @JvmStatic
    fun allocate(size: Long): ByteBuffer {
        pool.forEach {
            if (it.capacity() >= size) {
                pool.remove(it)
                return it.clear().limit(size.toInt())
            }
        }
        return ByteBuffer.allocate(size.toInt())
    }

    @JvmStatic
    fun release(buffer: ByteBuffer) {
        if (pool.size < MAX_POOL_SIZE) {
            pool.addLast(buffer)
        }
    }
}