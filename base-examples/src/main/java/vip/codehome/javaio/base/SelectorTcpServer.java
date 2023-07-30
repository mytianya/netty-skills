package vip.codehome.javaio.base;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SelectorTcpServer {
    public static void main(String[] args) throws IOException {
        try {
            // 创建一个ServerSocketChannel通道
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            // 绑定6800端口
            serverChannel.bind(new InetSocketAddress("0.0.0.0", 8000));
            // 设置非阻塞
            serverChannel.configureBlocking(false);
            // Selector创建
            Selector selector = Selector.open();
            // 注册 channel，并且指定感兴趣的事件是 Accept
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            ByteBuffer readBuff = ByteBuffer.allocate(1024);
            ByteBuffer writeBuff = ByteBuffer.allocate(1024);
            writeBuff.put("received".getBytes());
            writeBuff.flip();
            while (true) {
                if (selector.select() > 0) {
                    Set<SelectionKey> readyKeys = selector.selectedKeys();
                    Iterator<SelectionKey> readyKeyIterator = readyKeys.iterator();
                    while (readyKeyIterator.hasNext()) {
                        SelectionKey key = readyKeyIterator.next();
                        readyKeyIterator.remove();

                        if (key.isAcceptable()) {
                            // 连接
                            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            socketChannel.configureBlocking(false);
                            // 我们又给注册到Selector里面去了，声明这个channel只对读操作感兴趣。
                            socketChannel.register(selector, SelectionKey.OP_READ);
                        } else if (key.isReadable()) {
                            // 读
                            SocketChannel socketChannel = (SocketChannel) key.channel();
                            readBuff.clear();
                            socketChannel.read(readBuff);
                            readBuff.flip();
                            byte[] b = new byte[readBuff.limit()];
                            int bufferReceiveIndex = 0;
                            while (readBuff.hasRemaining()) {
                                b[bufferReceiveIndex++] = readBuff.get();
                            }
                            System.out.println("received : " + new String(b));
                            // 修改selector对channel感兴趣的事件
                            key.interestOps(SelectionKey.OP_WRITE);
                        } else if (key.isWritable()) {
                            // 写
                            writeBuff.rewind();
                            SocketChannel socketChannel = (SocketChannel) key.channel();
                            socketChannel.write(writeBuff);
                            // 修改selector对channel感兴趣的事件
                            key.interestOps(SelectionKey.OP_READ);
                        }
                    }
                }
            }

        } catch (IOException e) {
            // ignore
        }
    }
}
