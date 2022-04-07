package com.authur.li.socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description:
 * @Author: jibing.Li
 * @Date: 2022/4/7 14:50
 */
public class HttpServer03 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8803);
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 2);
        while (true) {
            try {
                final Socket socket = serverSocket.accept();
                executorService.execute(() -> invokeService(socket));
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
            String body = "hello,nio";
            printWriter.println("Content-Length: " + body.getBytes().length);
            printWriter.println();
            printWriter.write(body);
            printWriter.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
