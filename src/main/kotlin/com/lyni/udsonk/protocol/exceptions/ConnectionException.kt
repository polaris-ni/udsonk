package com.lyni.udsonk.protocol.exceptions

class ConnectionException(
    message: String? = null,
    cause: Throwable? = null
) : UdsTransportException(message, cause)