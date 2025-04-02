package com.lyni.com.polaris.udsonk.exceptions

import java.net.InetSocketAddress

class ConnectionException : UdsTransportException {
    enum class ErrorCode(val code: Int, val description: String) {
        GENERIC_FAILURE(1000, "Generic communication failure"),
        CONNECTION_TIMEOUT(1001, "Connection timeout"),
        DATA_SEND_FAILURE(1002, "Data transmission failed"),
        DATA_RECEIVE_FAILURE(1003, "Data reception failed"),
        UNEXPECTED_DISCONNECT(1004, "Unexpected connection closure"),
        HANDSHAKE_FAILURE(1005, "Protocol handshake failed"),
        RESOURCE_UNAVAILABLE(1006, "Network resource unavailable")
    }

    val errorCode: ErrorCode
    val remoteEndpoint: InetSocketAddress?

    constructor(
        message: String,
        cause: Throwable? = null,
        errorCode: ErrorCode = ErrorCode.GENERIC_FAILURE,
        remoteEndpoint: InetSocketAddress? = null
    ) : super(message, cause) {
        this.errorCode = errorCode
        this.remoteEndpoint = remoteEndpoint
    }

    constructor(
        errorCode: ErrorCode,
        cause: Throwable? = null,
        remoteEndpoint: InetSocketAddress? = null
    ) : super(errorCode.description, cause) {
        this.errorCode = errorCode
        this.remoteEndpoint = remoteEndpoint
    }
}