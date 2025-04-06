package com.lyni.udsonk.protocol.uds.session

import com.lyni.udsonk.protocol.uds.UdsCvtType

interface UdsSession {
    val value: Byte
    val info: String
    val desc: String
    val cvt: UdsCvtType
    val mnemonic: String
}