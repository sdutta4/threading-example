package com.rapid.core;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by sdutta on 02/05/18.
 */
public class CyclicBarrierExample {
    public class ThreadTest implements Runnable{
        private CyclicBarrier barrier;
        public ThreadTest(CyclicBarrier barrier){
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                for(int i=0; i<10; i++){
                    System.out.println("Printing in thread 1: "+i);
                }
                System.out.println("Sleep completed!");
                barrier.await();

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public class ThreadTest2 implements Runnable{
        private CyclicBarrier barrier;

        public ThreadTest2(CyclicBarrier barrier){
            this.barrier = barrier;
        }
        @Override
        public void run() {
            try {
                for(int i=0; i<10; i++){
                    System.out.println("Printing in thread 2: "+i);
                }
                System.out.println("Sleep completed!");
                barrier.await();

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public class BarrierActionThread implements Runnable{

        @Override
        public void run() {
            System.out.println("Finally executing barrier action ");
        }
    }

    public static void main(String [] args){
        System.out.println("Main execution started");
        CyclicBarrier barrier = new CyclicBarrier(2, new CyclicBarrierExample().new BarrierActionThread());
        Thread thread1 = new Thread(new CyclicBarrierExample().new ThreadTest(barrier));
        Thread thread2 = new Thread(new CyclicBarrierExample().new ThreadTest2(barrier));
        thread1.start();
        thread2.start();
        System.out.println("Main execution completed");
    }
}
