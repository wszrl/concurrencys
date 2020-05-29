package com.zrl.concurrency;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/*
 *@autho  zhengruilong
 *@date 2020-05-29
 */
@Slf4j
public class concurrencyTest {


    private static int threadTotal=50;
    private static int c=5000;
    private static int count=0;
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
       final Semaphore semaphore=new Semaphore(threadTotal);

        final CountDownLatch countDownLatch=new CountDownLatch(c);
        for (int i = 0; i < c; i++) {
        executorService.execute(()->{

               try {
                   semaphore.acquire();
                   add();
                   semaphore.release();
               } catch (InterruptedException e) {
                   e.printStackTrace();
                   log.error("错误",e);
               }
            countDownLatch.countDown();//减一个
           });

       };
        countDownLatch.await();
         executorService.shutdown();
//         log.info("count{}",count);
        System.out.println(count);
    }


    public static void add(){
         count++;

    }
}
