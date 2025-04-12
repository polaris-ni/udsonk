package com.lyni.udsonk.test;


import com.lyni.udsonk.common.util.NumberExtensions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NumberExtensionsTest {

    @Test
    public void testByteOf() {
        int value = 0xA1B2C3D4;
        assertEquals((byte) 0xA1, NumberExtensions.byteOf(value, 0));
        assertEquals((byte) 0xB2, NumberExtensions.byteOf(value, 1));
    }

    @Test
    public void testShortExtensions() {
        short test = 0x7F80;
        assertEquals((byte) 0x7F, NumberExtensions.highByte(test));
        assertEquals((byte) 0x80, NumberExtensions.lowByte(test));
    }

    @Test
    public void testNumberValueOf() {
        assertEquals(0x1234, NumberExtensions.shortValueOf((byte) 0x12, (byte) 0x34));
        assertEquals(0x12345678, NumberExtensions.intValueOf((byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x78));
    }

    @Test
    public void testHexFormat() {
        assertNotNull(NumberExtensions.UPPER_HEX_FORMAT);
    }
}