package com.lyni.udsonk.protocol.uds.services

import com.lyni.udsonk.application.client.UdsClientContext
import com.lyni.udsonk.common.miscell.SizeRange
import com.lyni.udsonk.common.util.NumberExtensions
import com.lyni.udsonk.common.util.ResponseUtil.checkSid
import com.lyni.udsonk.protocol.exceptions.NegativeResponseException
import com.lyni.udsonk.protocol.uds.UdsConstants
import com.lyni.udsonk.protocol.uds.nrc.UdsStandardNrc
import org.tinylog.kotlin.Logger
import java.nio.ByteBuffer

interface UdsServiceInterface {

    val service: UdsServices

    fun getRequestSize(context: UdsClientContext): SizeRange

    fun getResponseSize(context: UdsClientContext): SizeRange

    fun buildRequest(context: UdsClientContext, buffer: ByteBuffer)

    @OptIn(ExperimentalStdlibApi::class)
    fun onPositiveResponse(context: UdsClientContext, buf: ByteBuffer) {
        Logger.info(
            "service ${service.desc} received response: ${
                buf.array()
                    .toHexString(startIndex = buf.position(), endIndex = buf.limit(), NumberExtensions.UPPER_HEX_FORMAT)
            }"
        )
        getResponseSize(context).sizeCheck(buf.remaining())
        checkSid(buf.get(), service.value)
    }

    fun onNegativeResponse(context: UdsClientContext, buf: ByteBuffer) {
        check(buf.get() == UdsConstants.NEGATIVE_RESP) { "Unexpected response sid, ${UdsConstants.NEGATIVE_RESP} required" }
        checkSid(buf.get(), service.value)
        val nrc = buf.get()
        if (nrc == UdsStandardNrc.RESPONSE_PENDING.nrc) {
            throw NegativeResponseException(service, nrc)
        }
        Logger.info("service ${service.desc} received pending response")
    }
}