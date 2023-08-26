package com.raza.netty.core.heima.bio.multithreaded;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * des. 多线程可以同时处理多个socket的 bio通信模型
 * 实现服务的可以同时接受多个客户端的请求
 * 1. 服务端每次接受到客户端的socket 情况都使用一个独立的线程处理
 *
 * @author        : 靳砚茹
 * {@code @date}  : 2023/8/25 15:26
 */
public class Server {

    public void server() throws Exception{
        //注册端口
        ServerSocket serverSocket = new ServerSocket(7515);
        //不断接口客户端的链接请求
        while (true){
            Socket accept = serverSocket.accept();
            //开启一个线程
        }


    }
}
