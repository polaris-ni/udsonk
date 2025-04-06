package com.lyni.udsonk.common.io

import com.lyni.udsonk.protocol.uds.UdsDefine
import org.tinylog.kotlin.Logger
import java.util.concurrent.ConcurrentHashMap

class UdsDefinesCache<T : UdsDefine>(list: List<T>) {
    private val cache = ConcurrentHashMap<Byte, T>()

    init {
        list.forEach {
            cache[it.value] = it
        }
    }

    fun valueOf(value: Byte): T? {
        return cache[value]
    }

    fun put(define: T) {
        cache[define.value]?.also {
            Logger.info("Overwriting existing NRC: ${it.value} with new implementation")
        }
        cache[define.value] = define
    }
}