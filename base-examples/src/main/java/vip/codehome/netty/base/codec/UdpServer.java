package vip.codehome.netty.base.codec;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author dsys
 * @version v1.0
 **/
public class UdpServer {
  public UdpServer(Integer port){
    this.port=port;
  }
  int port;
  public void start(){
    Bootstrap bootstrap=new Bootstrap();
    NioEventLoopGroup acceptGroup=new NioEventLoopGroup();
    bootstrap.group(acceptGroup)
        .channel(NioDatagramChannel.class)
        .option(ChannelOption.SO_BROADCAST,true)
        .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
        .option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(65535))
        .handler(new ChannelInitializer<NioDatagramChannel>() {
          protected void initChannel(NioDatagramChannel ch){
            ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
            ch.pipeline().addLast(new UdpHandler());
          }
        });
    try {
      bootstrap.bind(port).sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    UdpServer udpServer=new UdpServer(8000);
    udpServer.start();
  }
}
