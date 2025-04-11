package com.lyni.udsonk.protocol.uds.session

import com.lyni.udsonk.common.io.UdsDefinesCache
import com.lyni.udsonk.protocol.uds.UdsCvtType
import com.lyni.udsonk.protocol.uds.UdsDefine

interface UdsSession : UdsDefine<Byte> {
    val info: String
    val cvt: UdsCvtType
    val mnemonic: String

    companion object {
        private val cache = UdsDefinesCache<UdsSession>(UdsStandardSession.entries)

        fun valueOf(session: Byte): UdsSession? = cache.valueOf(session)

        fun registerSession(session: UdsSession) = cache.put(session)
    }
}