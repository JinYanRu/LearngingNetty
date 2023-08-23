package com.raza.netty.luban;

import com.raza.netty.constant.StringConstant;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SocketChannel;

public class Client {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(7379));
            while (true){
                Socket accept = serverSocket.accept();
                InputStream inputStream = accept.getInputStream();
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader br = new BufferedReader(isr);
                String msg = null;
                while ( (msg = br.readLine()) != null){
                    System.out.println(msg);
                }
            }
        } catch (Exception e) {
        }

    }
}
