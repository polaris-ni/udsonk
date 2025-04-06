package com.lyni.udsonk.protocol.exceptions

import com.lyni.udsonk.protocol.uds.nrc.UdsNrc
import com.lyni.udsonk.protocol.uds.services.UdsServices

class NegativeResponseException(service: UdsServices, nrc: Byte) : UdsException(
    UdsNrc.valueOf(nrc)
        ?.let { "service ${service.desc} receive negative response ${it.hexString}-${it.info}, possible reason: ${it.desc}" }
        ?: "service %s receive negative response $%02X, nrc not defined".format(service.desc, nrc)
)