package com.lyni.com.polaris.udsonk.transport

import java.io.Closeable

interface TransportInterface : Closeable {
    fun connect()
    fun disconnect()
    fun send(data: ByteArray, offset: Int = 0, size: Int = data.size)
    fun receive(): ByteArray
    fun isConnected(): Boolean
}