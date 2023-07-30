package vip.codehome.javaio.base;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class TcpSocketChannel {
    public static void main(String[] args) throws IOException {
        SocketChannel channel=SocketChannel.open();
        channel.connect(new InetSocketAddress("127.0.0.1",8000));
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("hello".getBytes(StandardCharsets.UTF_8));
        buffer.flip();
        channel.write(buffer);
    }
}
