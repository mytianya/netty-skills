package vip.codehome.netty.base.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author dsys
 * @version v1.0
 **/
public class ByteBufDemo {
  public static void main(String[] args) {
    ByteBufDemo byteBufDemo=new ByteBufDemo();
    byteBufDemo.write();
  }

  /**
   * 普通的buff是固定大小的堆buff，而directBuffer是固定大小的direct buff。direct
   * buff使用的是堆外内存，省去了数据到内核的拷贝，因此效率比普通的buff要高。
   *
   * <p>wrappedBuffer是对现有的byte arrays或者byte buffers的封装，可以看做是一个视图，当底层的数据发生变化的时候，Wrapped
   * buffer中的数据也会发生变化。
   *
   * <p>Copied buffer是对现有的byte arrays、byte buffers 或者 string的深拷贝，所以它和wrappedBuffer是不同的，Copied
   * buffer和原数据之间并不共享数据。
   */
  public void create() {
    ByteBuf heapBuffer= Unpooled.buffer(128);
    ByteBuf directBuffer=Unpooled.directBuffer(256);
    ByteBuf wrappedBuffer=Unpooled.wrappedBuffer(new byte[128],new byte[256]);
    ByteBuf copieedBuffer=Unpooled.copiedBuffer(ByteBuffer.allocate(128));
  }
  /**
   +-------------------+------------------+------------------+
   | discardable bytes |  readable bytes  |  writable bytes  |
   |                   |     (CONTENT)    |                  |
   +-------------------+------------------+------------------+
   |                   |                  |                  |
   0      <=      readerIndex   <=   writerIndex    <=    capacity
   */
  public void write(){
    ByteBuf heapBuffer= Unpooled.buffer(128);
    heapBuffer.writeBytes("hello world".getBytes(StandardCharsets.UTF_8));
    byte[] bytes=new byte[heapBuffer.readableBytes()];
    heapBuffer.readBytes(bytes);
    System.out.println(new String(bytes));
  }
  public void read(){

  }
}
