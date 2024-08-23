package thread;

class SharedResource {
    //Synchorized method
    /*
    private boolean flag = false;
    public synchronized boolean isFlag() {
        return flag;
    }*/

    //make volatile the variable
    private volatile boolean flag = false;

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    public boolean isFlag() {
        return flag;
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