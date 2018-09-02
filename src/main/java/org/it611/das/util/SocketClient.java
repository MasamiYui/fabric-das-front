package org.it611.das.util;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.CharBuffer;

public class SocketClient {

    public static final String IP_ADDR = "127.0.0.1";
    public static final int PORT = 8000;
    private static Socket socket = null;

    static {
        try {
            socket = new Socket(IP_ADDR, PORT);
        } catch (IOException e) {
            System.out.println("套接字创建失败");
            e.printStackTrace();
        }
    }

    public static String send(String msg) throws IOException {

        OutputStream os=socket.getOutputStream();//字节输出流
        PrintWriter pw=new PrintWriter(os);//将输出流包装为打印流
        pw.write(msg);
        pw.flush();
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        char[] buffer = new char[1024];
        CharBuffer cb = CharBuffer.allocate(1024);

        int size = in.read(cb);


        return cb.toString();


    }
}
