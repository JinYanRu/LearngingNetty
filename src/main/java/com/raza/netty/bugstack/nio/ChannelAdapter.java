package com.raza.netty.bugstack.nio;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 */
public abstract class ChannelAdapter extends Thread {

    private Selector selector;

    private ChannelHandler channelHandler;
    private Charset charset;

    public ChannelAdapter(Selector selector, Charset charset) {
        this.selector = selector;
        this.charset = charset;
    }

    @Override
    public void run() {
        while (true) {
            try {
                //等待就绪的 i/0时间，最多等待1秒
                selector.select(1000);  //Selects a set of keys whose corresponding channels are ready for I/O
                //获取就绪的 key集合
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                // 迭代
                Iterator<SelectionKey> it = selectedKeys.iterator();
                SelectionKey key = null;
                while (it.hasNext()) {
                    key = it.next();
                    //删除
                    it.remove();
                    //处理当前事件
                    handleInput(key);
                }
            } catch (Exception ignore) {
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (!key.isValid()) {
            return;
        }

        // 客户端SocketChannel
        Class<?> superclass = key.channel().getClass().getSuperclass();
        //判断通道的类型
        if (superclass == SocketChannel.class){
            SocketChannel socketChannel = (SocketChannel) key.channel();
            //判断是否已经建立链接
            if (key.isConnectable()) {
                if (socketChannel.finishConnect()) {
                    //创建一个处理器
                    channelHandler = new ChannelHandler(socketChannel, charset);
                    //通道已经激活
                    channelActive(channelHandler);
                    //
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else {
                    System.exit(1);
                }
            }
        }

        // 服务端ServerSocketChannel
        if (superclass == ServerSocketChannel.class){
            if (key.isAcceptable()) {
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                SocketChannel socketChannel = serverSocketChannel.accept();
                socketChannel.configureBlocking(false);
                socketChannel.register(selector, SelectionKey.OP_READ);

                channelHandler = new ChannelHandler(socketChannel, charset);
                channelActive(channelHandler);
            }
        }

        if (key.isReadable()) {
            SocketChannel socketChannel = (SocketChannel) key.channel();
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            int readBytes = socketChannel.read(readBuffer);
            if (readBytes > 0) {
                readBuffer.flip();
                byte[] bytes = new byte[readBuffer.remaining()];
                readBuffer.get(bytes);
                channelRead(channelHandler, new String(bytes, charset));
            } else if (readBytes < 0) {
                key.cancel();
                socketChannel.close();
            }
        }
    }

    // 链接通知抽象类
    public abstract void channelActive(ChannelHandler ctx);

    // 读取消息抽象类
    public abstract void channelRead(ChannelHandler ctx, Object msg);

}
