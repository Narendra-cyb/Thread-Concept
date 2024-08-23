package thread;

import java.util.concurrent.atomic.AtomicInteger;

class ShareRes {
//    private int count;
    //Atomic class solutions
   /* private AtomicInteger count=new AtomicInteger(0);
    public void increment(){
        count.incrementAndGet();
    }
    public int getCount(){
        return count.get();
    }*/

    //syncronized way
    /*
    private int count;
    public synchronized void increment(){
        count++;
    }
     public int getCount(){
        return count;
    }

    */

    //volatile way == it can't be happen because it's written direct through main memory sometimes thread dont' udpate concurrently
    private volatile int count;

    public void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}

public class AtomicClassThreadPrac {
    public static void main(String[] args) {
        ShareRes res = new ShareRes();
        // Thread1
        Thread t1 = new Thread(() -> {
            System.out.println("Thread 1 started ");

            for (int i = 0; i < 50000; i++) {
                res.increment();
            }
            System.out.println("Thread 1 completed");
        });
        Thread t2 = new Thread(() -> {
            System.out.println("Thread 2 started ");

            for (int i = 0; i < 50000; i++) {
                res.increment();
            }
            System.out.println("Thread 2 completed");
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("final count:" + res.getCount());

    }
}
