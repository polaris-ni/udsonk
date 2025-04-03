package com.lyni.udsonk.protocol.services

import java.nio.ByteBuffer

interface UdsServiceInterface {

    val desc: String get() = getService().desc

    fun getService(): UdsServiceInterface

    fun getRequestSize(): Int

    fun buildRequest(buffer: ByteBuffer)
}