package vip.codehome.javaio.base;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.StandardCharsets;

public class UdpNioChannel {
    public static void main(String[] args) throws IOException {
        DatagramChannel datagramChannel=DatagramChannel.open();
        datagramChannel.bind(new InetSocketAddress(8000));
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        while (true){
            buffer.clear();
            SocketAddress socketAddress=datagramChannel.receive(buffer);
            if(socketAddress!=null){
                buffer.flip();
                byte[] b=new byte[buffer.limit()];
                int bufferReciveIndex=0;
                while (buffer.hasRemaining()){
                    b[bufferReciveIndex++]= buffer.get();
                }
                System.out.println("recive client:"+socketAddress.toString()+",msg:"+new String(b, StandardCharsets.UTF_8));
            }
        }
    }
    public void udpChannelClient() throws IOException {
        DatagramChannel channel=DatagramChannel.open();
        channel.connect(new InetSocketAddress("127.0.0.1",8000));
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        buffer.put("hello".getBytes(StandardCharsets.UTF_8));
        buffer.flip();
        channel.write(buffer);
    }
}
