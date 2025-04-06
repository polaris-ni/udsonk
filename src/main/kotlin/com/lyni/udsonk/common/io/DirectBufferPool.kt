package com.lyni.udsonk.common.io

import java.nio.ByteBuffer
import java.util.*

object DirectBufferPool {
    private const val MAX_POOL_SIZE = 1024

    private val pool = LinkedList<ByteBuffer>()

    fun allocate(size: Int): ByteBuffer {
        pool.forEach {
            if (it.capacity() >= size) {
                pool.remove(it)
                return it.clear().limit(size)
            }
        }
        return ByteBuffer.allocate(size)
    }

    fun release(buffer: ByteBuffer) {
        if (pool.size < MAX_POOL_SIZE) {
            pool.addLast(buffer)
        }
    }
}