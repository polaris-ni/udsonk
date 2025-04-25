package com.lyni.udsonk.protocol.uds.services

enum class UdsStandardService(
    override val value: Byte,
    override val desc: String,
    override val serviceType: UdsServiceType
) : UdsService {
    SID_10_DIAGNOSTIC_SESSION_CONTROL(
        0x10,
        "DiagnosticSessionControl",
        UdsServiceType.DIAGNOSTIC_AND_COMMUNICATION_MANAGEMENT
    ),
    SID_11_ECU_RESET(
        0x11,
        "ECUReset",
        UdsServiceType.DIAGNOSTIC_AND_COMMUNICATION_MANAGEMENT
    ),
    SID_2E_WRITE_DATA_BY_IDENTIFIER(
        0x2E,
        "WriteDataByIdentifier",
        UdsServiceType.DIAGNOSTIC_AND_COMMUNICATION_MANAGEMENT
    );
}