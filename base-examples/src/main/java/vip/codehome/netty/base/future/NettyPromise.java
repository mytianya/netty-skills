package vip.codehome.netty.base.future;

import io.netty.util.concurrent.*;

import java.util.concurrent.TimeUnit;

public class NettyPromise {
    public static void main(String[] args) {
        EventExecutor eventExecutor=new DefaultEventExecutor();
        Promise promise=new DefaultPromise(eventExecutor);
        promise.addListener(new GenericFutureListener<Future<String>>() {
            @Override
            public void operationComplete(Future<String> future) throws Exception {
                if(future.isSuccess()){
                    System.out.println("任务结束,结果："+future.get());
                }else{
                    System.out.println("任务失败,失败原因"+future.cause());
                }
            }
        }).addListener(new GenericFutureListener<Future<String>>() {
            @Override
            public void operationComplete(Future<String> future) throws Exception {
                System.out.println("任务结束.............");
            }
        });
        eventExecutor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                promise.setSuccess("hello");
            }
        });
        try {
            promise.sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
