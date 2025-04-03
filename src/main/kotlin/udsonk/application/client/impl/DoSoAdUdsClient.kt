package com.lyni.udsonk.application.client.impl

import com.lyni.udsonk.application.client.UdsClient
import com.lyni.udsonk.application.client.UdsClientContext
import com.lyni.udsonk.common.io.DirectBufferPool
import com.lyni.udsonk.common.util.NumberExtensions.byteOf
import com.lyni.udsonk.common.util.NumberExtensions.highByte
import com.lyni.udsonk.common.util.NumberExtensions.lowByte
import com.lyni.udsonk.protocol.services.UdsServiceInterface
import com.lyni.udsonk.protocol.transport.impl.SocketTransport

class DoSoAdUdsClient(testerAddress: UShort, targetAddress: UShort, socketConfig: SocketTransport.TransportConfig) :
    UdsClient(UdsClientContext(testerAddress, targetAddress)) {

    private val transporter = SocketTransport(socketConfig)

    override fun sendRequest(service: UdsServiceInterface) {
        val requestSize = service.getRequestSize() + 12
        val buffer = DirectBufferPool.allocate(requestSize)
        buffer.put(context.testerAddress.highByte())
        buffer.put(context.testerAddress.lowByte())
        buffer.put(context.targetAddress.highByte())
        buffer.put(context.targetAddress.lowByte())
        buffer.put(requestSize.byteOf(0))
        buffer.put(requestSize.byteOf(1))
        buffer.put(requestSize.byteOf(2))
        buffer.put(requestSize.byteOf(3))
        service.buildRequest(buffer)
        transporter.send(buffer.array())
    }
}