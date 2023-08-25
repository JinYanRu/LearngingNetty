package com.raza.netty.core.heima.bio.client;

import com.raza.netty.constant.StringConstant;
import org.junit.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    @Test
    public void client() throws Exception{
        // 请求和服务端的socket 进行链接
        Socket socket = new Socket(StringConstant.LOCAL_HOST_IP,7515);
        OutputStream outputStream = socket.getOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        //使用循环发送信息
        while (true){
            printStream.println("1231");
            printStream.flush();
        }
    }
}
