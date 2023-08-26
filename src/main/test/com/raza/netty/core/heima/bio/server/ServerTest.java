package com.raza.netty.core.heima.bio.server;

import org.junit.Test;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {
    @Test
    public void server() throws Exception{
        ServerSocket serverSocket = new ServerSocket(7515);
        while (true){
            System.out.println("111");
            Socket accept = serverSocket.accept();
            ServerThreadReader serverThreadReader = new ServerThreadReader(accept);
            serverThreadReader.start();
        }
    }
}
