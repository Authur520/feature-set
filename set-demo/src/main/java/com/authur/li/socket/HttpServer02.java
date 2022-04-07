package com.authur.li.socket;

import org.junit.rules.Stopwatch;
import org.springframework.util.StopWatch;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description:
 * @Author: jibing.Li
 * @Date: 2022/4/7 14:44
 */
public class HttpServer02 {
    public static void main(String[] args) throws IOException {
        StopWatch stopWatch = new StopWatch();

        ServerSocket serverSocket = new ServerSocket(8802);
        while (true) {
            try {
                final Socket socket = serverSocket.accept();
                new Thread(() -> {
                    invokeService(socket);
                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void invokeService(Socket socket) {
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("HTTP/1.1 200 OK");
            printWriter.println("Content-Type:text/html;charset=utf-8");
            String body = "hello,nio2";
            printWriter.println("Content-Length: " + body.getBytes().length);
            System.out.println(body);
            printWriter.println();
            printWriter.write(body);
            printWriter.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
