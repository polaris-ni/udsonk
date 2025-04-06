package com.lyni.udsonk.application.client

import com.lyni.udsonk.protocol.transport.TransportInterface
import com.lyni.udsonk.protocol.uds.services.UdsServiceInterface

abstract class UdsClient(
    protected val context: UdsClientContext,
    protected val transporter: TransportInterface
) : AutoCloseable {

    fun start() {
        transporter.connect()
    }

    abstract fun sendRequest(service: UdsServiceInterface)

    override fun close() {
        transporter.disconnect()
        transporter.close()
    }
}