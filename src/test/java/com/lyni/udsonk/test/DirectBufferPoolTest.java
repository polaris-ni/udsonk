package com.lyni.udsonk.test;

import com.lyni.udsonk.common.io.DirectBufferPool;
import org.junit.jupiter.api.Test;
import java.nio.ByteBuffer;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DirectBufferPoolTest {

    @Test
    public void testBufferAllocateAndRelease() {
        ByteBuffer buffer = DirectBufferPool.allocate(1024);
        assertTrue(buffer.capacity() >= 1024);
        DirectBufferPool.release(buffer);
    }
}