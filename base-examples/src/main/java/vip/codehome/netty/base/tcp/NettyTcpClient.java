package vip.codehome.netty.base.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.nio.charset.StandardCharsets;

public class NettyTcpClient {
    Channel channel;
    public static void main(String[] args) throws InterruptedException {
        NettyTcpClient nettyTcpClient=new NettyTcpClient();
        nettyTcpClient.connect("127.0.0.1",8000);
        for(int i=0;i<1000;i++){
            Thread.sleep(1000);
            nettyTcpClient.send("Hello world");
        }

    }
    public void send(String msg){
        channel.writeAndFlush(Unpooled.copiedBuffer(msg.getBytes(StandardCharsets.UTF_8)));
    }
    public void connect(String host, Integer port) {
        EventLoopGroup group = new NioEventLoopGroup();
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new LoggingHandler(LogLevel.INFO));
                            p.addLast(new NettyEchoClientInboundHandler());
                        }
                    });
            //启动客户端
        ChannelFuture f = null;
        try {
            f = b.connect(host, port).sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        channel = f.channel();

    }
    public void close(){

    }
}
