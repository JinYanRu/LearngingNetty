package com.raza.netty.core.heima.bio.client;

import org.junit.Test;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    @Test
    public void server() throws Exception{
        ServerSocket serverSocket = new ServerSocket(7515);
        Socket accept = serverSocket.accept();
        ServerThreadReader serverThreadReader = new ServerThreadReader(accept);
        serverThreadReader.start();
    }
}
