package com.lyni.udsonk.protocol.services

enum class UdsServices(
    val value: Byte,
    val desc: String,
    val serviceType: ServiceType,
    val isoReference: String
) {
    SID_10_DIAGNOSTIC_SESSION_CONTROL(
        0x10,
        "DiagnosticSessionControl",
        ServiceType.DIAGNOSTIC,
        "ISO14229-1 10.2"
    ),

    SID_34_REQUEST_DOWNLOAD(
        0x34,
        "RequestDownload",
        ServiceType.PROGRAMMING,
        "ISO14229-1 12.5"
    ),

    SID_27_SECURITY_ACCESS(
        0x27,
        "SecurityAccess",
        ServiceType.SECURITY,
        "ISO14229-1 11.4"
    );

    enum class ServiceType {
        DIAGNOSTIC,
        PROGRAMMING,
        SECURITY,
        DATA_TRANSFER,
        CONTROL
    }
}