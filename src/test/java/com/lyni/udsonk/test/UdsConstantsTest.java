package com.lyni.udsonk.test;

import com.lyni.udsonk.protocol.uds.UdsConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UdsConstantsTest {

    @Test
    public void testConstantsDirectAccess() {
        assertEquals(0x7F, UdsConstants.NEGATIVE_RESP);
        assertEquals(0x40, UdsConstants.POSITIVE_RESP_OFFSET);
    }
}