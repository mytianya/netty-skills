package vip.codehome.netty.base.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import java.nio.charset.Charset;

/**
 * @author dsys
 * @version v1.0
 **/
public class UdpHandler extends SimpleChannelInboundHandler<DatagramPacket> {

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
    ByteBuf byteBuf=msg.content();
    byte[] bytes=new byte[byteBuf.readableBytes()];
    byteBuf.readBytes(bytes);
    System.out.println(bytes.length);
    System.out.println(new String(bytes, Charset.forName("UTF-8")));
  }
}
