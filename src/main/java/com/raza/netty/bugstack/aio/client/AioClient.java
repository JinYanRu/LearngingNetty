package com.raza.netty.bugstack.aio.client;

import com.raza.netty.constant.StringConstant;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.Future;

public class AioClient {

    public static void main(String[] args) throws Exception {
        // 使用 AsynchronousSocketChannel 打开一个异步得 socket 通道
        try (AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open()) {
            // 进行链接
            Future<Void> future = socketChannel.connect(new InetSocketAddress(StringConstant.LOCAL_HOST_IP, 7397));
            //等待链接完成
            future.get();
            //读取 创建 1024字节的 ByteBuffer 对象，接口读取的数据
            socketChannel.read(ByteBuffer.allocate(1024), null, new AioClientHandler(socketChannel, Charset.forName("GBK")));
        }
        Thread.sleep(100000);
    }

}
