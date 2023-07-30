package vip.codehome.javaio.base;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class TcpServerSocketChannel {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel channel=ServerSocketChannel.open();
        channel.bind(new InetSocketAddress("127.0.0.1",8000));
        while (true){
            SocketChannel socketChannel=channel.accept();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.clear();
            buffer.put("hello".getBytes(StandardCharsets.UTF_8));
            buffer.flip();
            // 给客户端发送消息
            socketChannel.write(buffer);
            // 在收下客户端的消息
            buffer.clear();
            int readLength = socketChannel.read(buffer);
            if (readLength >= 0) {
                buffer.flip();
                byte[] b = new byte[buffer.limit()];
                int bufferReceiveIndex = 0;
                while (buffer.hasRemaining()) {
                    b[bufferReceiveIndex++] = buffer.get();
                }
                System.out.println("收到消息 " + ":" + new String(b, StandardCharsets.UTF_8));
            }
        }
    }
}
