package com.lyni.udsonk.protocol.uds.services.standard

import com.lyni.udsonk.application.client.UdsClientContext
import com.lyni.udsonk.common.miscell.SizeRange
import com.lyni.udsonk.common.util.NumberExtensions
import com.lyni.udsonk.protocol.uds.services.UdsService
import com.lyni.udsonk.protocol.uds.services.UdsServiceInterface
import com.lyni.udsonk.protocol.uds.services.UdsStandardService
import com.lyni.udsonk.protocol.uds.session.UdsSession
import org.tinylog.kotlin.Logger
import java.nio.ByteBuffer

/**
 * Represents the Diagnostic Session Control service, which is used to manage diagnostic sessions.
 * This class implements the [UdsServiceInterface] and provides functionality for switching between different diagnostic sessions.
 *
 * @property session The UDS session which will be switched to after the request.
 */
open class DiagnosticSessionControl(private val session: UdsSession) : UdsServiceInterface {

    override val service: UdsService
        get() = UdsStandardService.SID_10_DIAGNOSTIC_SESSION_CONTROL

    private val requestSize = SizeRange(2)
    private val responseSize = SizeRange(6)

    /**
     * Request message definition
     *
     * #1 SID M 0x10 DSC
     *
     * #2 SubFunction=[diagnosticSessionType](com.lyni.udsonk.protocol.uds.session.UdsSession) M 0x00 ~ 0xFF LEV_DS_
     *
     * @param context [UdsClientContext]
     * @return [SizeRange]
     * @see [UdsSession](com.lyni.udsonk.protocol.uds.session.UdsSession)
     * [UdsStandardSession](com.lyni.udsonk.protocol.uds.session.UdsStandardSession))
     */
    override fun getRequestSize(context: UdsClientContext) = requestSize

    override fun getResponseSize(context: UdsClientContext) = responseSize

    override fun buildRequest(context: UdsClientContext, buffer: ByteBuffer) {
        buffer.put(service.value)
        buffer.put(session.value)
    }

    override fun onPositiveResponse(context: UdsClientContext, buf: ByteBuffer) {
        super.onPositiveResponse(context, buf)
        buf.position(buf.position() + 1)
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