package com.lyni.udsonk.protocol.transport

import java.io.Closeable
import java.nio.ByteBuffer

interface TransportInterface : Closeable {
    fun connect()
    fun disconnect()
    fun send(data: ByteArray, offset: Int = 0, size: Int = data.size)
    fun receive(buffer: ByteBuffer)
    fun isConnected(): Boolean
}