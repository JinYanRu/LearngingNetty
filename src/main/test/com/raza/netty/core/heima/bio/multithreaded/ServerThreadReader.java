package com.raza.netty.core.heima.bio.multithreaded;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerThreadReader extends Thread{

    private Socket socket;

    public ServerThreadReader(Socket socket) {
        this.socket = socket;
    }

    @SneakyThrows
    @Override
    public void run() {
        InputStream inputStream = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String msg ;
        while ((msg = br.readLine()) != null){
            System.out.println(msg);
        }
    }
}

