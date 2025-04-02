package com.lyni.com.polaris.udsonk.exceptions

abstract class UdsTransportException(
    message: String? = null,
    cause: Throwable? = null
) : RuntimeException(message, cause)