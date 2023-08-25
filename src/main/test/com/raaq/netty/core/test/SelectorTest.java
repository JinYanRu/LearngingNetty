package com.raaq.netty.core.test;

import com.raza.netty.constant.StringConstant;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class SelectorTest {
    /**
     * des.
     * ------- selector 的核心方法
     * 1. select() 注册的管道上至少有一个管道有事件就返回，将产生的 selectionKey 返回到内部集合
     * 2. select(timeout) 超时返回
     * 3. selectNow() 立马返回，不阻塞
     * 3. selectEdKeys() 获取所有事件发生的 selectionKey 每个selectionKey 对象聚合了一个 selectableChannel对象
     * 4. keys() 获取注册在selector上的所有 selectionKey
     * 5. wakeup() 唤醒 selector
     * -------- selector 中的事件常量
     * 1. OP_ACCEPT :可接受的
     * 2. OP_CONNECT: 已连接的
     * 3. OP_READ: 可读的
     * 4. OP_WRITE: 可写的
     * 1. 服务端 开启端口监听
     * 2. 将一个ServerSocketChannel 注册到Selector上
     * 3.
     * @author        : 靳砚茹
     * {@code @date}  : 2023/8/24 16:47
     */
    @Test
    public void selectorTest() throws Exception{
        //构造一个 ServerSocketChannel ,设置为非阻塞
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //绑定端口号
        serverSocketChannel.bind(new InetSocketAddress(StringConstant.LOCAL_HOST_IP,7515));

        //开启Selector
        Selector selector = Selector.open();
        //将 ServerSocketChannel 注册到 selector上，服务端线程开始调用 selector的 select方法 进入阻塞
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //服务的开启循环处理客户端的通信
        while (true){
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey nextKey = iterator.next();
                if (nextKey.isAcceptable()){
                    getLink(serverSocketChannel,selector);
                }else if(nextKey.isReadable()){
                    read(nextKey);
                    nextKey.interestOps(SelectionKey.OP_WRITE);
                }
                if (nextKey.isWritable()){
                    write(nextKey);
                }
                iterator.remove();
            }
        }
    }
    private void getLink(ServerSocketChannel serverSocketChannel,Selector selector) throws Exception {
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        System.out.printf("客户端上线[%s]%n", socketChannel.getRemoteAddress().toString());
        socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
    }

    private void read(SelectionKey selectionKey) throws Exception{
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = (ByteBuffer)selectionKey.attachment();
        byteBuffer.clear();
        channel.read(byteBuffer);
        String s = new String(byteBuffer.array(), 0, byteBuffer.position());
        System.out.println(s);
    }

    private void write(SelectionKey selectionKey) throws Exception{
        SocketChannel channel = (SocketChannel)selectionKey.channel();
        ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
        byteBuffer.flip();
        channel.write(byteBuffer);
        selectionKey.interestOps(SelectionKey.OP_READ);
    }


    private void broadcast(){

    }
}




















