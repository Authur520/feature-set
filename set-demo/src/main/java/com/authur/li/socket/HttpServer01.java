package com.authur.li.socket;

import org.springframework.util.StopWatch;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description:
 * @Author: jibing.Li
 * @Date: 2022/4/7 11:50
 */
public class HttpServer01 {
    public static void main(String[] args) throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("HttpServer01");
        ServerSocket serverSocket = new ServerSocket(8801);
        Socket accept = serverSocket.accept();
        invokeService(accept);

        stopWatch.stop();
    }

    private static void invokeService(Socket socket) {
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            String body = "hello,nio3";
            printWriter.println("Content-Length: " + body.getBytes().length);
            printWriter.println();
            System.out.println(body);
            printWriter.write(body);
            printWriter.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
