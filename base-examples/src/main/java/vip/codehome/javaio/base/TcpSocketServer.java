package vip.codehome.javaio.base;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpSocketServer {
    public static void main(String[] args) {
        TcpSocketServer tcpSocketServer=new TcpSocketServer();
       // tcpSocketServer.bind(8000);
        tcpSocketServer.bindThread(8001);
    }
    public void bind(int port){
        try {
               ServerSocket server=new ServerSocket(port);
               while (true){
                   Socket socket=server.accept();
                   InputStream inputStream=socket.getInputStream();
                   byte[] buffer=new byte[1024];
                   int len;
                   while ((len=inputStream.read(buffer))!=-1){
                       String s=new String(buffer,0,len,"UTF-8");
                       System.out.println("recive from "+socket.getInetAddress().toString()+",msg:"+s);
                   }
                   inputStream.close();
                   socket.close();
               }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static ExecutorService pool= Executors.newFixedThreadPool(10);
    public void bindThread(int port){
        try {
            ServerSocket server=new ServerSocket(port);
            while (true){
                Socket socket=server.accept();
                Thread thread=new TcpSocketThread(socket);
                pool.submit(thread);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public class TcpSocketThread extends Thread{
        Socket socket;
        TcpSocketThread(Socket socket){
            this.socket=socket;
        }

        @Override
        public void run() {
            InputStream inputStream= null;
            try {
                inputStream = socket.getInputStream();
                byte[] buffer=new byte[1024];
                int len;
                while ((len=inputStream.read(buffer))!=-1){
                    String s=new String(buffer,0,len,"UTF-8");
                    System.out.println("recive from "+socket.getInetAddress().toString()+",msg:"+s);
                }
                inputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
