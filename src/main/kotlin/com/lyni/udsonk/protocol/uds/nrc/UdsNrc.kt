package com.lyni.udsonk.protocol.uds.nrc

import com.lyni.udsonk.protocol.uds.nrc.UdsStandardNrc.*
import org.tinylog.kotlin.Logger

interface UdsNrc {
    val nrc: Byte
    val hexString: String
    val info: String
    val desc: String
    val mnemonic: String

    companion object {
        private val cache = mutableMapOf<Byte, UdsNrc?>()

        fun valueOf(nrc: Byte): UdsNrc? {
            val udsStandardNrc = cache.getOrPut(nrc) {
                when (nrc) {
                    SUB_FUNCTION_NOT_SUPPORTED.nrc -> SUB_FUNCTION_NOT_SUPPORTED
                    INCORRECT_MESSAGE_LENGTH_OR_INVALID_FORMAT.nrc -> INCORRECT_MESSAGE_LENGTH_OR_INVALID_FORMAT
                    CONDITION_NOT_CORRECT.nrc -> CONDITION_NOT_CORRECT
                    RESPONSE_PENDING.nrc -> RESPONSE_PENDING
                    else -> null
                }
            }
            return udsStandardNrc
        }

        fun registerNrc(nrc: UdsNrc) {
            if (cache.containsKey(nrc.nrc)) {
                Logger.info("registering nrc: ${nrc.nrc}, original nrc is replaced")
            }
            cache[nrc.nrc] = nrc
        }
    }
}