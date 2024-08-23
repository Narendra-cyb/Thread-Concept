
# Memory Visibility Issues in Multi-Threaded Java Programs

## Problem Overview

In a multi-threaded environment, threads may have their own caches of variables. If one thread updates a shared variable, other threads might not immediately see the update if proper memory visibility is not enforced. This can lead to inconsistent or stale data being observed by threads.

### Example Code

```java
class SharedResource {
    private boolean flag = false;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}

public class VolatilePractise {

    public static void main(String[] args) {
        SharedResource sharedResource = new SharedResource();

        new Thread(() -> {
            System.out.println("Thread1 Started ");
            System.out.println("Thread1 Logic Started ");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            sharedResource.setFlag(true);
            System.out.println("Thread1 Completed");
        }).start();

        new Thread(() -> {
            System.out.println("Thread2 is started");
            while (!sharedResource.isFlag()) {
                // Busy-wait loop
            }
            System.out.println("Thread2 is completed");
        }).start();
    }
}
```
## Issues

### Memory Visibility

- `Thread1` updates the `flag` variable by calling `sharedResource.setFlag(true)`.
- `Thread2` checks the value of `flag` in a busy-wait loop with `sharedResource.isFlag()`.
- Without proper synchronization or the `volatile` keyword, `Thread2` might not immediately see the updated value of `flag` set by `Thread1` because the update might still be in `Thread1`'s local CPU cache.

### Caching

- CPUs use caching to improve performance. Threads may cache variables locally, and updates made by one thread might not be visible to others immediately without proper visibility mechanisms.

## Solutions

### Using `volatile` Keyword

- Mark the `flag` variable as `volatile` to ensure visibility across threads. Changes to a `volatile` variable are immediately visible to all threads.

```java
class SharedResource {
    private volatile boolean flag = false;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
```
### Using Synchronized Methods

- Use synchronized blocks or methods to ensure atomicity and visibility.

```java
class SharedResource {
    private boolean flag = false;

    public synchronized boolean isFlag() {
        return flag;
    }

    public synchronized void setFlag(boolean flag) {
        this.flag = flag;
    }
}
```

### Using Atomic Variables

- For atomic updates, use classes from `java.util.concurrent.atomic`, such as `AtomicBoolean`.

```java
import java.util.concurrent.atomic.AtomicBoolean;

class SharedResource {
    private AtomicBoolean flag = new AtomicBoolean(false);

    public boolean isFlag() {
        return flag.get();
    }

    public void setFlag(boolean flag) {
        this.flag.set(flag);
    }
}
```
## Summary

To ensure proper memory visibility and synchronization across threads in Java:

- Use the `volatile` keyword for simple scenarios.
- Use synchronized methods or blocks for ensuring atomicity and visibility.
- Consider atomic variables from `java.util.concurrent.atomic` for more complex scenarios.

By implementing these strategies, you can avoid common pitfalls related to memory visibility and synchronization in multi-threaded applications.
