---

# Memory Visibility and Atomicity in Multi-Threading in Java

## Problem Overview

In a multi-threaded Java application, shared variables can be accessed and modified by multiple threads concurrently. Without proper synchronization, this can lead to inconsistent states or incorrect results due to race conditions.

### Example Code

The following code demonstrates the issue with a shared variable (`count`) being incremented by two threads:

```java
package thread;

import java.util.concurrent.atomic.AtomicInteger;

class ShareRes {
    // Option 1: AtomicInteger (Thread-safe way)
//    private AtomicInteger count = new AtomicInteger(0);
//
//    public void increment() {
//        count.incrementAndGet();
//    }
//
//    public int getCount() {
//        return count.get();
//    }

    // Option 2: Synchronized method (Thread-safe way)
//    private int count;
//
//    public synchronized void increment() {
//        count++;
//    }
//
//    public synchronized int getCount() {
//        return count;
//    }

    // Option 3: Volatile keyword (Not thread-safe for compound actions)
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
        
        // Thread2
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
        
        System.out.println("final count: " + res.getCount());
    }
}
```

### Issue with the `volatile` Keyword

In the code above, if you use the `volatile` keyword for the `count` variable:

```java
private volatile int count;
```

The `volatile` keyword ensures that any write to the `count` variable is immediately visible to other threads. However, `volatile` does **not** guarantee atomicity. 

#### Why is `volatile` Not Sufficient?

The operation `count++` is **not atomic**. It consists of three steps:
1. **Read** the current value of `count`.
2. **Increment** the value.
3. **Write** the new value back to `count`.

If two threads execute `count++` concurrently, they may both read the same value before either has written back, resulting in only one increment instead of two. This is a **race condition**.

### Correct Ways to Ensure Thread Safety

To correctly handle the shared variable in a multi-threaded environment, you need to ensure that the increment operation is atomic. There are several ways to achieve this:

#### 1. **Using `AtomicInteger`**

`AtomicInteger` provides atomic methods for performing operations on an integer.

```java
private AtomicInteger count = new AtomicInteger(0);

public void increment() {
    count.incrementAndGet();
}

public int getCount() {
    return count.get();
}
```

**Why it Works:**  
`AtomicInteger` ensures that `incrementAndGet()` is performed as a single atomic operation, preventing race conditions.

#### 2. **Using `synchronized` Methods**

You can make the `increment` method `synchronized` to ensure that only one thread can execute it at a time.

```java
private int count;

public synchronized void increment() {
    count++;
}

public synchronized int getCount() {
    return count;
}
```

**Why it Works:**  
The `synchronized` keyword ensures mutual exclusion, so only one thread at a time can execute `increment()`, which prevents concurrent modifications to `count`.

### Summary

- **`volatile`**: Ensures visibility of changes to variables across threads but does not guarantee atomicity for compound operations like `count++`.
- **`AtomicInteger`**: Provides atomic operations that are thread-safe for single variables.
- **`synchronized`**: Ensures that only one thread can execute a critical section of code at a time, preventing race conditions.

For scenarios where you need thread-safe increment operations, prefer using `AtomicInteger` or `synchronized` methods.

By using the appropriate synchronization techniques, you can avoid common pitfalls and ensure consistent and correct behavior in multi-threaded applications.

---