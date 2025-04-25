@file:JvmName("ECUReset")

package com.lyni.udsonk.protocol.uds.services.standard

import com.lyni.udsonk.application.client.UdsClientContext
import com.lyni.udsonk.common.miscell.SizeRange
import com.lyni.udsonk.protocol.exceptions.InvalidResponseException
import com.lyni.udsonk.protocol.uds.services.UdsService
import com.lyni.udsonk.protocol.uds.services.UdsServiceInterface
import com.lyni.udsonk.protocol.uds.services.UdsStandardService
import java.nio.ByteBuffer

/**
 * ECUReset standard implementation
 *
 * @property resetType ECU reset type
 * [com.lyni.udsonk.protocol.uds.services.standard.ECUReset.ISOSAEReserved]
 * [com.lyni.udsonk.protocol.uds.services.standard.ECUReset.hardReset]
 * [com.lyni.udsonk.protocol.uds.services.standard.ECUReset.keyOffReset]
 * [com.lyni.udsonk.protocol.uds.services.standard.ECUReset.softReset]
 * [com.lyni.udsonk.protocol.uds.services.standard.ECUReset.enableRapidPowerShutDown]
 * [com.lyni.udsonk.protocol.uds.services.standard.ECUReset.disableRapidPowerShutDown]
 * @property onReceivePositiveResponse callback will be invoked when received a positive response
 *              powerDownTime will be null if server don't replay it
 */
class ECUReset(
    private val resetType: Byte,
    private val onReceivePositiveResponse: ((powerDownTime: Byte?) -> Unit)? = null
) : UdsServiceInterface {

    companion object {
        const val ISOSAEReserved: Byte = 0
        const val hardReset: Byte = 1
        const val keyOffReset: Byte = 2
        const val softReset: Byte = 3
        const val enableRapidPowerShutDown: Byte = 4
        const val disableRapidPowerShutDown: Byte = 5

        /* >>> NOTES */
        /* 0x06 ~ 0x3F ISOSAEReserved This range of values is reserved by this document for future definition. M ISOSAERESRVD */
        /* 0x40 ~ 0x5F vehicleManufacturerSpecific This range of values is reserved for vehicle-manufacturer-specific use. U VMS */
        /* 0x60 ~ 0x7E systemSupplierSpecific This range of values is reserved for system-supplier-specific use. U SSS */
        /* 0x7F ISOSAEReserved This value is reserved by this document for future definition use. M ISOSAERESRVD */
        /* <<< NOTES */
    }

    override val service: UdsService
        get() = UdsStandardService.SID_11_ECU_RESET

    /**
     * Request message definition
     *
     * #1 SID M 0x11 ER
     *
     * #2 resetType M 0x00~0xFF LEV_RT_
     *
     * @param context [UdsClientContext]
     * @return [SizeRange]
     */
    override fun getRequestSize(context: UdsClientContext): SizeRange = SizeRange(2)

    /**
     * Positive response message definition
     *
     * #1 Response SID M 0x51 ERPR
     *
     * #2 resetType M 0x00~0x7F LEV_RT_
     *
     * #3 powerDownTime C 0x00~0xFF PDT
     *
     * @param context [UdsClientContext]
     * @return [SizeRange]
     */
    override fun getResponseSize(context: UdsClientContext): SizeRange = SizeRange(2, 3)

    override fun buildRequest(
        context: UdsClientContext,
        buffer: ByteBuffer
    ) {
        buffer.put(service.value)
        buffer.put(resetType)
    }

    override fun onPositiveResponse(context: UdsClientContext, buf: ByteBuffer) {
        super.onPositiveResponse(context, buf)
        buf.get()
        val type = buf.get()
        if (type != resetType) {
            throw InvalidResponseException("ECU Reset type not equal, $type received, but $resetType sent")
        }
        if (buf.hasRemaining()) {
            onReceivePositiveResponse?.invoke(buf.get())
        } else {
            onReceivePositiveResponse?.invoke(null)
        }
    }
}