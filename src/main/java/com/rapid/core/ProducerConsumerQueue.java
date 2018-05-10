package com.rapid.core;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Chellams on 10/05/18.
 */
public class ProducerConsumerQueue {

    public class Producer implements Runnable{
        private BlockingQueue<Integer> queue;

        public Producer(BlockingQueue queue){
            this.queue = queue;
        }

        @Override
        public void run() {
                try {
                    for(int i = 0; i<50; i++) {
                        Random random = new Random();
                        int number = random.nextInt();
                        System.out.println("produced number : "+number);
                        queue.put(number);
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

        }
    }

    public class Consumer implements Runnable{
        private BlockingQueue<Integer> queue;

        public Consumer(BlockingQueue queue){
            this.queue = queue;
        }

        @Override
        public void run() {
            while(true) {
                try {
                    int number = queue.take();
                    System.out.println("Consumed from queue : " + number);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String [] args){
        System.out.println("Main thread executing..");
        BlockingQueue queue = new ArrayBlockingQueue(10);
        Thread producer = new Thread(new ProducerConsumerQueue().new Producer(queue));
        Thread consumer = new Thread(new ProducerConsumerQueue().new Consumer(queue));
        producer.start();
        consumer.start();
        System.out.println("Main thread execution finishes");
    }


}
