package com.lyni.udsonk.protocol.uds.services.impl

import com.lyni.udsonk.application.client.UdsClientContext
import com.lyni.udsonk.common.miscell.SizeRange
import com.lyni.udsonk.common.util.NumberExtensions
import com.lyni.udsonk.protocol.uds.services.UdsServiceInterface
import com.lyni.udsonk.protocol.uds.services.UdsServices
import com.lyni.udsonk.protocol.uds.session.UdsSession
import org.tinylog.kotlin.Logger
import java.nio.ByteBuffer

open class DiagnosticSessionControl(private val session: UdsSession) : UdsServiceInterface {

    override val service: UdsServices
        get() = UdsServices.SID_10_DIAGNOSTIC_SESSION_CONTROL

    /**
     * Request message definition
     *
     * #1 SID M 0x10 DSC
     *
     * #2 SubFunction=[ diagnosticSessionType] (refer to [UdsSession](com.lyni.udsonk.protocol.uds.session.UdsSession)
     * standard implementation [UdsStandardSession](com.lyni.udsonk.protocol.uds.session.UdsStandardSession)) M 0x00 ~ 0xFF LEV_DS_
     */
    override fun getRequestSize(context: UdsClientContext): SizeRange = SizeRange(2)

    override fun getResponseSize(context: UdsClientContext): SizeRange = SizeRange(6)


    override fun buildRequest(context: UdsClientContext, buffer: ByteBuffer) {
        buffer.put(service.value)
        buffer.put(session.value)
    }

    override fun onPositiveResponse(context: UdsClientContext, buf: ByteBuffer) {
        super.onPositiveResponse(context, buf)
        val diagnosticSessionType = buf.get()
        val p2ServerMax: Short = NumberExtensions.shortValueOf(buf.get(), buf.get())
        val p2ServerMaxEnhanced: Short = NumberExtensions.shortValueOf(buf.get(), buf.get())
        onPositiveResponse(context, diagnosticSessionType, p2ServerMax, p2ServerMaxEnhanced)
    }

    open fun onPositiveResponse(
        context: UdsClientContext,
        diagnosticSessionType: Byte,
        p2ServerMax: Short,
        p2ServerMaxEnhanced: Short
    ) {
        Logger.info("diagnostic session control response, diagnosticSessionType $diagnosticSessionType, p2ServerMax $p2ServerMax, enhancedP2ServerMax $p2ServerMaxEnhanced")
        context.p2ServerMax = p2ServerMax
        context.p2ServerMaxEnhanced = p2ServerMaxEnhanced
    }
}