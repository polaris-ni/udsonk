package com.lyni.udsonk.protocol.uds.services

import com.lyni.udsonk.common.io.UdsDefinesCache
import com.lyni.udsonk.protocol.uds.UdsDefine

interface UdsService : UdsDefine<Byte> {
    val serviceType: UdsServiceType

    companion object {

        private val cache = UdsDefinesCache<Byte, UdsService>(UdsStandardService.entries)

        @JvmStatic
        fun valueOf(service: Byte): UdsService? = cache.valueOf(service)

        @JvmStatic
        fun registerService(service: UdsService) = cache.put(service)
    }
}