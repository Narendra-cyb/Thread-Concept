package thread;

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

         new Thread(()->{
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

         new Thread(()->{
             System.out.println("Thread2 is started");
             while(!sharedResource.isFlag()){

             }
             System.out.println("Thread2 is completed");
         }).start();
    }
}

/*

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

         new Thread(()->{
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

         new Thread(()->{
             System.out.println("Thread2 is started");
             while(!sharedResource.isFlag()){

             }
             System.out.println("Thread2 is completed");
         }).start();
    }
}
in the above code without synchronize or Volatile keyword issue is ,

        Heap ==> Thread Stack(Local Variable memory)

        Thread1 -> Cpu --> Caching --> Ram
        Thread2 -> Cpu --> Caching --> Ram
        Thread1 here started and start the logic and sleep for 1 second
        meantime Thread2 started and start the logic and it's also sleep for 1 second
        Thread1 Started it's work it set the flag to True
        But because of it's still it's in caching stage it is still not in Ram(Memory) thread2 is not getting the Accurate result
        so that's why this problem is occurred.

 */