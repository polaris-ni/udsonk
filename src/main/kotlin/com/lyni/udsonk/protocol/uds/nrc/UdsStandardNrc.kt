package com.lyni.udsonk.protocol.uds.nrc

enum class UdsStandardNrc(
    override val value: Byte,
    override val hexString: String,
    override val info: String,
    override val desc: String,
    override val mnemonic: String
) : UdsNrc {

    SUB_FUNCTION_NOT_SUPPORTED(
        0x12,
        "$12",
        "SubFunctionNotSupported",
        "This NRC indicates that the requested action will not be taken because the \n" +
                "server does not support the service specific parameters of the request \n" +
                "message. \n" +
                "The server shall send this NRC in case the client has sent a request message \n" +
                "with a known and supported service identifier but with \"SubFunction\" which \n" +
                "is either unknown or not supported.",
        "SFNS"
    ),
    INCORRECT_MESSAGE_LENGTH_OR_INVALID_FORMAT(
        0x13,
        "$13",
        "incorrectMessageLengthOrInvalidFormat",
        "This NRC shall be sent if the length of the message is wrong.",
        "IMLOIF"
    ),
    CONDITION_NOT_CORRECT(
        0x22,
        "$22",
        "conditionsNotCorrect",
        "This NRC shall be returned if the criteria for the request are not met.",
        "CNC"
    ),
    RESPONSE_PENDING(
        0x78,
        "$78",
        "requestCorrectlyReceived-ResponsePending",
        "This NRC indicates that the request message was received correctly, and that \n" +
                "all parameters in the request message were valid (these checks can be \n" +
                "delayed until after sending this NRC if executing the boot software), but the \n" +
                "action to be performed is not yet completed and the server is not yet ready to \n" +
                "receive another request. As soon as the requested service has been \n" +
                "completed, the server shall send a positive response message or negative \n" +
                "response message with a response code different from this. \n" +
                "The negative response message with this NRC may be repeated by the server \n" +
                "until the requested service is completed and the final response message is \n" +
                "sent. This NRC might impact the application layer timing parameter values. \n" +
                "The detailed specification shall be included in the data link specific \n" +
                "implementation document. \n" +
                "This NRC shall only be used in a negative response message if the server will \n" +
                "not be able to receive further request messages from the client while \n" +
                "completing the requested diagnostic service. \n" +
                "When this NRC is used, the server shall always send a final response (positive \n" +
                "or negative) independent of the suppressPosRspMsglndicationBit value or the \n" +
                "suppress requirement for responses with NRCs SNS, SFNS, SNSIAS, SFNSIAS \n" +
                "and ROOR on functionally addressed requests. \n" +
                "A typical example where this NRC may be used is when the client has sent a \n" +
                "request message, which includes data to be programmed or erased in flash \n" +
                "memory of the server. If the programming/erasing routine (usually executed \n" +
                "out of RAM) is not able to support serial communication while writing to the \n" +
                "flash memory the server shall send a negative response message with this \n" +
                "response code. \n" +
                "This NRC is in general supported by each diagnostic service, as not otherwise \n" +
                "stated in the data link specific implementation document, therefore it is not \n" +
                "listed in the list of applicable response codes of the diagnostic services. ",
        "RCRRP"
    );
}