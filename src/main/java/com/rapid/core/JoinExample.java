package com.rapid.core;

/**
 * Created by Chellams on 10/05/18.
 */
public class JoinExample {


    public class WorkerA implements Runnable{

        @Override
        public void run() {
            System.out.println("I am supposed to execute first");
        }
    }

    public class WorkerB implements Runnable{
        @Override
        public void run() {
            System.out.println("I am supposed to execute second");
        }
    }

    public class WorkerC implements Runnable{
        @Override
        public void run() {
            System.out.println("I am supposed to execute third");
        }
    }

    public static void main(String [] args){
        Thread workerA = new Thread(new JoinExample().new WorkerA());
        Thread workerB = new Thread(new JoinExample().new WorkerB());
        Thread workerC = new Thread(new JoinExample().new WorkerC());
        try{
            workerA.start();
            workerA.join();
            workerB.start();
            workerB.join();
            workerC.start();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
