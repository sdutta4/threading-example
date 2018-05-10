package com.rapid.core;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

/**
 * Created by Chellams on 10/05/18.
 */
public class ProducerConsumerLegacy {

    public class Producer implements Runnable{
        private Queue<Integer> queue;
        private int queueSize;

        public Producer(Queue queue, int queueSize){
            this.queue = queue;
            this.queueSize = queueSize;
        }

        @Override
        public void run() {
            while(true) {
                synchronized (queue) {
                    try {
                        if (queue.size() == queueSize) {
                            System.out.println("queue is full so can't produce any more item");
                            queue.wait();
                        } else {
                            Random num = new Random();
                            int number = num.nextInt();
                            System.out.println("Producing a random number : " + number);
                            queue.add(number);
                            queue.notifyAll();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    public class Consumer implements Runnable{
        private Queue<Integer> queue;
        private int queueSize;

        public Consumer(Queue queue, int queueSize){
            this.queue = queue;
            this.queueSize= queueSize;
        }

        @Override
        public void run() {
            while(true) {
                synchronized (queue) {
                    try {
                        if (queue.size() == 0) {
                            System.out.println("Queue is empty so can't consume anything");
                            queue.wait();
                        } else {
                            int number = queue.remove();
                            System.out.println("Consumed number :" + number);
                            queue.notifyAll();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String [] args){
        System.out.println("Main thread initiated.");
        int maxSize = 1;
        Queue<Integer> queue = new PriorityQueue<>(maxSize);
        Thread producer = new Thread(new ProducerConsumerLegacy().new Producer(queue, maxSize));
        Thread consumer = new Thread(new ProducerConsumerLegacy().new Consumer(queue, maxSize));
        producer.start();
        consumer.start();
        System.out.println("Main thread finished execution..");
    }
}
