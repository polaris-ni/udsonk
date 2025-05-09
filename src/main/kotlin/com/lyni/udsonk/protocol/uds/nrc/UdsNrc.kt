package com.lyni.udsonk.protocol.uds.nrc

import com.lyni.udsonk.common.io.UdsDefinesCache
import com.lyni.udsonk.protocol.uds.UdsDefine

interface UdsNrc : UdsDefine<Byte> {
    val hexString: String
    val info: String
    val mnemonic: String

    companion object {
        private val cache = UdsDefinesCache<Byte, UdsNrc>(UdsStandardNrc.entries)

        @JvmStatic
        fun valueOf(nrc: Byte): UdsNrc? = cache.valueOf(nrc)

        @JvmStatic
        fun registerNrc(nrc: UdsNrc) = cache.put(nrc)
    }
}
