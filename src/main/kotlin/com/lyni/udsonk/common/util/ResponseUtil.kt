@file:JvmName("ResponseUtil")
package com.lyni.udsonk.common.util

import com.lyni.udsonk.protocol.exceptions.InvalidResponseException
import com.lyni.udsonk.protocol.uds.UdsConstants

object ResponseUtil {
    @JvmStatic
    @Throws(InvalidResponseException::class)
    fun checkSid(respSid: Byte, reqSid: Byte) {
        val targetSid = (reqSid + UdsConstants.POSITIVE_RESP_OFFSET).toByte()
        if (respSid != targetSid) {
            throw InvalidResponseException("sid not match, expected 0x%02X but given 0x%02X".format(targetSid, respSid))
        }
    }
}