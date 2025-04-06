package com.lyni.udsonk.protocol.exceptions

abstract class UdsTransportException(
    message: String? = null,
    cause: Throwable? = null
) : RuntimeException(message, cause)