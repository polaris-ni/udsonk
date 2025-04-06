package com.lyni.udsonk.protocol.uds.session

import com.lyni.udsonk.protocol.uds.UdsCvtType
import com.lyni.udsonk.protocol.uds.UdsCvtType.M
import com.lyni.udsonk.protocol.uds.UdsCvtType.U

enum class UdsStandardSession(
    override val value: Byte,
    override val info: String,
    override val desc: String,
    override val cvt: UdsCvtType,
    override val mnemonic: String
) : UdsSession {
    ISO_SAE_RESERVED(
        0x00,
        "ISOSAEReserved",
        "This value is reserved by this document.",
        M,
        "ISOSAERESRVD"
    ),
    DEFAULT_SESSION(
        0x01,
        "DefaultSession",
        "This diagnostic session enables the default diagnostic session in the \n" +
                "server(s) and does not support any diagnostic application timeout \n" +
                "handling provisions ( e.g. no Tester Present service is necessary to keep \n" +
                "the session active). \n" +
                "If any other session than the defaultSession has been active in the server \n" +
                "and the defaultSession is once again started, then the following \n" +
                "implementation rules shall be followed (see also the server diagnostic \n" +
                "session state diagram given above): \n" +
                "The server shall stop the current diagnostic session when it has sent the \n" +
                "DiagnosticSessionControl positive response message and shall start the \n" +
                "newly requested diagnostic session afterwards. \n" +
                "If the server has sent a DiagnosticSessionControl positive response \n" +
                "message it shall have re-locked the server if the client unlocked it during \n" +
                "the diagnostic session. \n" +
                "If the server sends a negative response message with the \n" +
                "DiagnosticSessionControl request service identifier the active session \n" +
                "shall be continued. \n" +
                "In case the used data link requires an initialization step then the \n" +
                "initialized server(s) shall start the default diagnostic session by default. \n" +
                "No DiagnosticSessionControl with diagnosticSession set to defaultSession \n" +
                "shall be required after the initialization step. ",
        U,
        "DS"
    ),
    PROGRAMMING_SESSION(
        0x02, "ProgrammingSession",
        "This diagnosticSession enables all diagnostic services required to \n" +
                "support the memory programming of a server. \n" +
                "It is vehicle-manufacturer specific whether the positive response is sent \n" +
                "prior or after the ECU has switched to/from programmingSession. \n" +
                "In case the server runs the programmingSession in the boot software, the \n" +
                "programmingSession shall only be left via an ECUReset (1116) service \n" +
                "initiated by the client, a DiagnosticSessionControl (1016) service with \n" +
                "sessionType equal to defaultSession, or a session layer timeout in the \n" +
                "server. \n" +
                "In case the server runs in the boot software when it receives the \n" +
                "DiagnosticSessionControl (1016) service with session Type equal to \n" +
                "defaultSession or a session layer timeout occurs and a valid application \n" +
                "software is present for both cases then the server shall restart the \n" +
                "application software. This document does not specify the various \n" +
                "implementation methods of how to achieve the restart of the valid \n" +
                "application software (e.g. a valid application software can be determined \n" +
                "directly in the boot software, during the ECU startup phase when \n" +
                "performing an ECU reset, etc.). ",
        U,
        "PRGS"
    ),
    EXTENDED_DIAGNOSTIC_SESSION(
        0x03,
        "ExtendedDiagnosticSession",
        "This diagnosticSession can be used to enable all diagnostic services \n" +
                "required to support the adjustment of functions like \"Idle Speed, CO \n" +
                "Value, etc.\" in the server's memory. It can also be used to enable \n" +
                "diagnostic services, which are not specifically tied to the adjustment of \n" +
                "functions (e.g. refer to timed services in Table 23).",
        U,
        "EXTDS"
    ),
    SAFETY_SYSTEM_DIAGNOSTIC_SESSION(
        0x04,
        "SafetySystemDiagnosticSession",
        "This diagnosticSession enables all diagnostic services required to \n" +
                "support safety system related functions (e.g. airbag deployment). ",
        U,
        "SSDS"
    ),

    /* >>> NOTES */

    /* 0x05 ~ 0x3F ISOSAEReserved This value is reserved by this document for future definition. M ISOSAERESRVD */

    /* 0x40 ~ 0x5F vehicleManufacturerSpecific This range of values is reserved for vehicle manufacturer specific use. U VMS */

    /* 0x60 ~ 0x7E systemSupplierSpecific This range of values is reserved for system supplier specific use. */

    /* <<< NOTES */

    ISO_SAE_RESERVED2(
        0x7F,
        "ISOSAEReserved",
        "This value is reserved by this document for future definition. ",
        M,
        "ISOSAERESRVD"
    );

}