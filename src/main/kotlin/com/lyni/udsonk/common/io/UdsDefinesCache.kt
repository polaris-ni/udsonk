package com.lyni.udsonk.common.io

import com.lyni.udsonk.protocol.uds.UdsDefine
import org.tinylog.kotlin.Logger
import java.util.concurrent.ConcurrentHashMap

class UdsDefinesCache<V, T : UdsDefine<V>>(list: List<T>) {
    private val cache = ConcurrentHashMap<V, T>()

    init {
        list.forEach {
            cache[it.value] = it
        }
    }

    fun valueOf(value: V): T? {
        return cache[value]
    }

    fun put(define: T) {
        cache[define.value]?.also {
            Logger.info("Overwriting existing NRC: ${it.value} with new implementation")
        }
        cache[define.value] = define
    }
}