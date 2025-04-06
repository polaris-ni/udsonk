package com.lyni.udsonk.protocol.uds.nrc

import com.lyni.udsonk.common.io.UdsDefinesCache
import com.lyni.udsonk.protocol.uds.UdsDefine

interface UdsNrc : UdsDefine {
    val hexString: String
    val info: String
    val desc: String
    val mnemonic: String

    companion object {
        private val cache = UdsDefinesCache<UdsNrc>(UdsStandardNrc.entries)

        fun valueOf(nrc: Byte): UdsNrc? = cache.valueOf(nrc)

        fun registerNrc(nrc: UdsNrc) = cache.put(nrc)
    }
}
