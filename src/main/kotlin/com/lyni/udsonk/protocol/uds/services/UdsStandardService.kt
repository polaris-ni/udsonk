package com.lyni.udsonk.protocol.uds.services

enum class UdsStandardService(
    override val value: Byte,
    override val desc: String,
    override val serviceType: UdsServiceType,
    override val isoReference: String
) : UdsService {
    SID_10_DIAGNOSTIC_SESSION_CONTROL(
        0x10,
        "DiagnosticSessionControl",
        UdsServiceType.DIAGNOSTIC_AND_COMMUNICATION_MANAGEMENT,
        "ISO14229-1 10.2"
    );
}