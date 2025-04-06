package com.lyni.udsonk.application.client.impl

import com.lyni.udsonk.application.client.UdsClientContext
import com.lyni.udsonk.common.io.DirectBufferPool
import com.lyni.udsonk.common.util.NumberExtensions
import com.lyni.udsonk.common.util.NumberExtensions.byteOf
import com.lyni.udsonk.common.util.NumberExtensions.highByte
import com.lyni.udsonk.common.util.NumberExtensions.lowByte
import com.lyni.udsonk.protocol.exceptions.InvalidResponseException
import com.lyni.udsonk.protocol.transport.impl.SocketTransport
import com.lyni.udsonk.protocol.uds.UdsConstants
import com.lyni.udsonk.protocol.uds.services.UdsServiceInterface
import org.tinylog.kotlin.Logger
import java.nio.ByteBuffer
import kotlin.experimental.or

class DoSoAdUdsClient(testerAddress: Short, targetAddress: Short, socketConfig: SocketTransport.TransportConfig) :
    com.lyni.udsonk.application.client.UdsClient(
        UdsClientContext(testerAddress, targetAddress, 0, 0),
        SocketTransport(socketConfig)
    ) {

    companion object {
        const val HEADER_SIZE = 8
    }

    private fun handleResponse(service: UdsServiceInterface, buffer: ByteBuffer) {
        when (val data = buffer.get(HEADER_SIZE)) {
            UdsConstants.NEGATIVE_RESP -> service.onNegativeResponse(context, buffer)
            service.service.value.or(UdsConstants.POSITIVE_RESP_OFFSET) -> service.onPositiveResponse(context, buffer)
            else -> Logger.warn(
                "response not belong to service ${service.service.desc}, first byte is ${
                    data.toString(
                        16
                    )
                }"
            )
        }
    }

    override fun sendRequest(service: UdsServiceInterface) {
        val reqSize = service.getRequestSize(context).max
        val req = DirectBufferPool.allocate(reqSize + HEADER_SIZE)
        val respSize = service.getResponseSize(context).max + HEADER_SIZE
        val resp = DirectBufferPool.allocate(respSize)
        try {
            req.put(context.testerAddress.highByte())
            req.put(context.testerAddress.lowByte())
            req.put(context.targetAddress.highByte())
            req.put(context.targetAddress.lowByte())
            req.put(reqSize.byteOf(0))
            req.put(reqSize.byteOf(1))
            req.put(reqSize.byteOf(2))
            req.put(reqSize.byteOf(3))
            service.buildRequest(context, req)
            transporter.send(req.array(), 0, req.limit())
            transporter.receive(resp)
            val rcvSrcAddr = NumberExtensions.shortValueOf(resp.get(), resp.get())
            val rcvDestAddr = NumberExtensions.shortValueOf(resp.get(), resp.get())
            if ((rcvSrcAddr != context.targetAddress) || (rcvDestAddr != context.testerAddress)) {
                throw InvalidResponseException(
                    service.service,
                    context.testerAddress,
                    context.targetAddress,
                    rcvSrcAddr,
                    rcvDestAddr
                )
            }
            val length = NumberExtensions.intValueOf(resp.get(), resp.get(), resp.get(), resp.get())
            if (length + HEADER_SIZE != resp.limit()) {
                throw InvalidResponseException(service.service, length + HEADER_SIZE, resp.limit())
            }
            handleResponse(service, resp)
        } catch (e: Exception) {
            throw e
        } finally {
            DirectBufferPool.release(req)
            DirectBufferPool.release(resp)
            transporter.disconnect()
            transporter.close()
        }
    }
}