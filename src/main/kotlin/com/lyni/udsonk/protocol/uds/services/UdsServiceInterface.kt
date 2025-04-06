package com.lyni.udsonk.protocol.uds.services

import com.lyni.udsonk.application.client.UdsClientContext
import com.lyni.udsonk.common.miscell.SizeRange
import com.lyni.udsonk.common.util.ResponseUtil.checkSid
import com.lyni.udsonk.protocol.exceptions.NegativeResponseException
import com.lyni.udsonk.protocol.uds.UdsConstants
import com.lyni.udsonk.protocol.uds.nrc.UdsStandardNrc
import java.nio.ByteBuffer

interface UdsServiceInterface {

    val service: UdsService

    fun getRequestSize(context: UdsClientContext): SizeRange

    fun getResponseSize(context: UdsClientContext): SizeRange

    fun buildRequest(context: UdsClientContext, buffer: ByteBuffer)

    fun onPositiveResponse(context: UdsClientContext, buf: ByteBuffer) {
        getResponseSize(context).sizeCheck(buf.remaining())
        val sid = buf[buf.position()]
        checkSid(sid, service.value)
    }


    fun onNegativeResponse(context: UdsClientContext, buf: ByteBuffer) {
        val originalPosition = buf.position()
        try {
            val responseSid = buf.get()
            check(responseSid == UdsConstants.NEGATIVE_RESP) {
                "Unexpected response sid: expected ${UdsConstants.NEGATIVE_RESP}, got $responseSid"
            }
            val receivedServiceId = buf.get()
            checkSid(receivedServiceId, service.value)
            val nrc = buf.get()
            if (nrc == UdsStandardNrc.RESPONSE_PENDING.value) {
                throw NegativeResponseException(service, nrc)
            }
        } catch (e: Exception) {
            buf.position(originalPosition)
            throw e
        }
    }

}