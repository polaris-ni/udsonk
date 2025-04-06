package com.lyni.udsonk.protocol.exceptions

import com.lyni.udsonk.protocol.uds.services.UdsServices

class InvalidResponseException(msg: String) : UdsException(msg) {
    constructor(
        service: UdsServices,
        testerAddr: Short,
        targetAddr: Short,
        rcvSrcAddr: Short,
        rcvDestAddr: Short
    ) : this(
        "service %s receive response address error, client address[0x%02X 0x%02X], server address[0x%02X 0x%02X]".format(
            service.desc, testerAddr, rcvDestAddr, targetAddr, rcvSrcAddr
        )
    )

    constructor(
        service: UdsServices,
        length: Int,
        totalLength: Int
    ) : this("service ${service.desc} receive response length error, parse length is $length, but $totalLength received")
}