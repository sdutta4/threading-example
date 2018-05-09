package com.rapid.core;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Chellams on 09/05/18.
 */
public class CountDownLatchExample {

    public class MQStarterThread implements Runnable{

        private CountDownLatch latch;
        public MQStarterThread(CountDownLatch latch){
            this.latch = latch;
        }
        @Override
        public void run() {
            try {
                System.out.println("MQ is initiating by.."+Thread.currentThread().getName());
                Thread.sleep(1000);
                System.out.println("MQ Initiated by.."+Thread.currentThread().getName());
                latch.countDown();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public class DBTestRunnerThread implements Runnable{

        private CountDownLatch latch;
        public DBTestRunnerThread(CountDownLatch latch){
            this.latch = latch;
        }
        @Override
        public void run() {
            System.out.println("DB Connectivity testing initiated by.."+Thread.currentThread().getName());
            try{
                Thread.sleep(1000);
                latch.countDown();
                System.out.println("DB connectivity testing completed by.."+Thread.currentThread().getName());
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public class ExternalServiceSyncThread implements Runnable{
        private CountDownLatch latch;
        public ExternalServiceSyncThread(CountDownLatch latch){
            this.latch = latch;
        }

        @Override
        public void run() {
            System.out.println("Making sync call to external service by.."+Thread.currentThread().getName());
            try{
                Thread.sleep(1000);
                latch.countDown();
                System.out.println("Sync up completed by.."+Thread.currentThread().getName());
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String [] args){
        CountDownLatch latch = new CountDownLatch(3);
        Thread mqThread = new Thread(new CountDownLatchExample().new MQStarterThread(latch));
        Thread dbThread = new Thread(new CountDownLatchExample().new DBTestRunnerThread(latch));
        Thread exeServiceThread = new Thread(new CountDownLatchExample().new ExternalServiceSyncThread(latch));

        System.out.println("Executing main thread..Awaiting service initiation by.."+Thread.currentThread().getName());
        mqThread.start();
        dbThread.start();
        exeServiceThread.start();

        try {
            latch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Services initiated.. [MAIN thread] is executing by.."+Thread.currentThread().getName());
    }
}
