package com.rapid.core;

import java.util.concurrent.Semaphore;

/**
 * Created by Chellams on 09/05/18.
 */
public class SemaphoreExample {

    public class Account{
        public long balance;
        public String accountHolderName;

        public Account(long amount, String accountHolderName){
            this.balance = amount;
            this.accountHolderName = accountHolderName;
        }

        public long getBalance() {
            return balance;
        }

        public void setBalance(long balance) {
            this.balance = balance;
        }

        public String getAccountHolderName() {
            return accountHolderName;
        }
    }

    public class NetBankingDeduction implements Runnable{

        private Semaphore semaphore;
        private Account account;
        private long amount;

        public NetBankingDeduction(Semaphore semaphore, Account account, long amount){
            this.semaphore = semaphore;
            this.account = account;
            this.amount = amount;
        }

        @Override
        public void run() {
            System.out.println("Trying to transfer money through netbanking..");
            try {
                semaphore.acquire();
                System.out.println("Netbanking Deduction started..");
                Thread.sleep(3000);
                if(account.getBalance()-amount > 0) {
                    account.setBalance(account.getBalance() - amount);
                }else{
                    System.out.println("Not a valid transaction.");
                }
                System.out.println("Money deducted through Netbanking. New Balance : "+account.getBalance());
                semaphore.release();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public class ATMWithdrawl implements Runnable{
        private Semaphore semaphore;
        private Account account;
        private long amount;

        public ATMWithdrawl(Semaphore semaphore, Account account, long amount){
            this.semaphore = semaphore;
            this.account = account;
            this.amount = amount;
        }

        @Override
        public void run() {
            System.out.println("Trying to deduct money as ATM withdrawal");
            try{
                semaphore.acquire();
                System.out.println("ATM Deduction started..");
                Thread.sleep(3000);
                if(account.getBalance()-amount > 0) {
                    account.setBalance(account.getBalance() - amount);
                }else{
                    System.out.println("Not a valid transaction.");
                }
                System.out.println("Money deducted through ATM. New Balance : "+account.getBalance());
                semaphore.release();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String [] args){
        Account account = new SemaphoreExample().new Account(100000L, "Sutanu");
        Semaphore semaphore = new Semaphore(1);
        Thread netbanking = new Thread(new SemaphoreExample().new NetBankingDeduction(semaphore, account, 20000L));
        Thread atmWithdrawl = new Thread(new SemaphoreExample().new ATMWithdrawl(semaphore, account, 30000L));

        System.out.println("MAIN Thread executing.");
        netbanking.start();
        atmWithdrawl.start();
        System.out.println("MAIN thread execution completed");
    }
}
