package com.lyni.udsonk.test;

import com.lyni.udsonk.common.util.ResponseUtil;
import com.lyni.udsonk.protocol.exceptions.InvalidResponseException;
import org.junit.jupiter.api.Test;

import static com.lyni.udsonk.protocol.uds.UdsConstants.POSITIVE_RESP_OFFSET;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ResponseUtilTest {

    @Test
    void checkSidInvalidResponse() {
        byte reqSid = 0x01;
        byte invalidRespSid = 0x02;

        InvalidResponseException exception = assertThrows(InvalidResponseException.class,
                () -> ResponseUtil.checkSid(invalidRespSid, reqSid));

        byte expectedSid = (byte) (reqSid + POSITIVE_RESP_OFFSET);
        String expectedMessage = String.format("sid not match, expected 0x%02X but given 0x%02X",
                expectedSid & 0xFF, invalidRespSid & 0xFF);
        assertEquals(expectedMessage, exception.getMessage());
    }


    @Test
    public void testValidSid() {
        ResponseUtil.checkSid((byte) 0x42, (byte) 0x02); // 0x02 + 0x40 = 0x42
    }
}