package com.raaq.netty.core.test;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelTest {

    @Test
    public void outputTest() throws Exception{

        FileOutputStream fileOutputStream = new FileOutputStream("E://file.txt");
        FileChannel channel = fileOutputStream.getChannel();
        ByteBuffer allocate = ByteBuffer.allocate(10);
        allocate.put("123456799".getBytes());
        allocate.flip();
        int write = channel.write(allocate);
        System.out.println("写入字节数量" + write);
        fileOutputStream.close();
    }

    public void inputTest(){

    }
}
