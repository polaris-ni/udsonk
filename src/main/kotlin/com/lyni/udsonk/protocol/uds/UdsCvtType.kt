package com.lyni.udsonk.protocol.uds

enum class UdsCvtType(val value: Byte, val desc: String) {
    M(0, "Mandatory"),
    U(1, "Conditional"),
}