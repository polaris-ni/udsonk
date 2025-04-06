package com.lyni.udsonk.protocol.uds.nrc

import org.tinylog.kotlin.Logger
import java.util.concurrent.ConcurrentHashMap

interface UdsNrc {
    val nrc: Byte
    val hexString: String
    val info: String
    val desc: String
    val mnemonic: String

    companion object {
        private val cache = ConcurrentHashMap<Byte, UdsNrc?>()

        /**
         * Retrieves UdsNrc instance for the given NRC value.
         * Checks cache first, then standard NRCs if not found.
         * @param nrc Byte value of the NRC to retrieve
         * @return Matching UdsNrc instance or null if not found
         */
        fun valueOf(nrc: Byte): UdsNrc? = cache.getOrPut(nrc) {
            UdsStandardNrc.entries.firstOrNull { it.nrc == nrc }
        }

        /**
         * Registers a custom NRC implementation.
         * Overwrites existing entry if same NRC value exists.
         * @param nrc Custom UdsNrc instance to register
         */
        @Suppress("unused")
        fun registerNrc(nrc: UdsNrc) {
            cache[nrc.nrc]?.also {
                Logger.info("Overwriting existing NRC: ${it.nrc} with new implementation")
            }
            cache[nrc.nrc] = nrc
        }
    }
}
