package com.raza.netty.core.csdn;

import org.junit.Test;

import java.nio.ByteBuffer;

public class BufferTest {
    public static void main(String[] args) {
        ByteBuffer allocate = ByteBuffer.allocate(10);
        for (int j = 0; j < 3; j++) {
            allocate.put((byte) j);
        }

        allocate.flip();

        while (allocate.hasRemaining()){
            System.out.println(allocate.get());
        }
        allocate.clear();

        for (int j = 0; j < 4; j++) {
            allocate.put((byte) j);
        }

        while (allocate.hasRemaining()){
            System.out.println(allocate.get());
        }

    }
}
