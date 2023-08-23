package com.raaq.netty.core.test;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void inputTest() throws  Exception{
        FileInputStream fileInputStream = new FileInputStream("E://file.txt");
        // 获取  fileInputStream 中的 channel
        FileChannel channel = fileInputStream.getChannel();
        //创建一个LISt集合
        List<Byte> list = new ArrayList<>();
        //创建一个8字节空间的 byteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        int len = -1;
        byte[] bytes = new byte[8];
        //  while 循环读取 channel 中的数据 (len=channel.read(buffer))!=-1
        while ((len  =channel.read(byteBuffer))!=-1){
            byteBuffer.flip();
            byteBuffer.get(bytes,0,len);

            for (int i = 0; i < len; i++) {
                list.add(bytes[i]);
            }
            byteBuffer.clear();
        }
        fileInputStream.close();
        byte[] resByte = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            resByte[i] = list.get(i);
        }
        System.out.println(new String(resByte));
        //切换 buffer的读取模式
        //



    }
}
