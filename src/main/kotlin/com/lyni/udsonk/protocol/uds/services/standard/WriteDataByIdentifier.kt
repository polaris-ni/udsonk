package com.lyni.udsonk.protocol.uds.services.standard

import com.lyni.udsonk.application.client.UdsClientContext
import com.lyni.udsonk.common.miscell.SizeRange
import com.lyni.udsonk.common.util.NumberExtensions
import com.lyni.udsonk.common.util.NumberExtensions.highByte
import com.lyni.udsonk.common.util.NumberExtensions.lowByte
import com.lyni.udsonk.protocol.exceptions.InvalidResponseException
import com.lyni.udsonk.protocol.exceptions.UdsParamException
import com.lyni.udsonk.protocol.uds.services.UdsService
import com.lyni.udsonk.protocol.uds.services.UdsServiceInterface
import com.lyni.udsonk.protocol.uds.services.UdsStandardService
import com.lyni.udsonk.protocol.uds.services.standard.did.UdsDataIdentifier
import com.lyni.udsonk.protocol.uds.services.standard.did.provider.UdsDataIdentifierProvider
import org.tinylog.kotlin.Logger
import java.nio.ByteBuffer

class WriteDataByIdentifier(private val did: UdsDataIdentifier, payload: ByteArray? = null) : UdsServiceInterface {

    private var data: ByteArray? = null

    init {
        data = payload
    }

    private var provider: UdsDataIdentifierProvider? = null

    constructor(did: UdsDataIdentifier, provider: UdsDataIdentifierProvider) : this(did, null) {
        this.provider = provider
    }

    override val service: UdsService
        get() = UdsStandardService.SID_2E_WRITE_DATA_BY_IDENTIFIER

    /**
     * Request message definition
     *
     * #1 SID M 0x2E WDBI
     *
     * #2 dataIdentifier_MSB M 0x00~0xFF DID_HB
     *
     * #3 dataIdentifier_LSB M 0x00~0xFF DID_LB
     *
     * #4 dataRecord_data_1 M 0x00~0xFF DREC_DATA_1
     *
     * #m+3 dataRecord_data_m M 0x00~0xFF DREC_DATA_m
     *
     * @param context [UdsClientContext]
     * @return [SizeRange]
     * @see [UdsDataIdentifier](com.lyni.udsonk.protocol.uds.services.standard.did.UdsDataIdentifier)
     */
    override fun getRequestSize(context: UdsClientContext): SizeRange {
        if (data == null) {
            if (provider == null) {
                throw UdsParamException("did data is null, and no provider found")
            }
            data = provider!!.provide(context, did)
        }
        return SizeRange(3 + data!!.size)
    }

    /**
     * Positive response message definition
     *
     * #1 Response SID M 0x6E WDBIPR
     *
     * #2 dataIdentifier_MSB M 0x00~0xFF DID_HB
     *
     * #3 dataIdentifier_LSB M 0x00~0xFF DID_LB
     *
     * @param context [UdsClientContext]
     * @return [SizeRange]
     */
    override fun getResponseSize(context: UdsClientContext): SizeRange = SizeRange(3)

    override fun buildRequest(context: UdsClientContext, buffer: ByteBuffer) {
        buffer.put(service.value)
        buffer.put(did.value.highByte())
        buffer.put(did.value.lowByte())
        buffer.put(data)
    }

    override fun onPositiveResponse(context: UdsClientContext, buf: ByteBuffer) {
        super.onPositiveResponse(context, buf)
        /* response SID, not care */
        buf.get()
        val respDid = NumberExtensions.shortValueOf(buf.get(), buf.get())
        if (respDid != did.value) {
            throw InvalidResponseException(
                "response did 0x%02X and request did 0x%02X are not equal".format(respDid, did.value)
            )
        }
        Logger.info("WriteDataByIdentifier success, did is 0x%02X".format(did.value))
    }
}