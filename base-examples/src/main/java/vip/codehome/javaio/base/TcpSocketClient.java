package vip.codehome.javaio.base;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TcpSocketClient {
    public static void main(String[] args) {
        Socket socket=new Socket();
        try {
            socket.connect(new InetSocketAddress("192.168.50.247",8000));
            OutputStream outputStream=socket.getOutputStream();
            outputStream.write("hello".getBytes(StandardCharsets.UTF_8));
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
