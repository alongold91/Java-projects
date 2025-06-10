    public class Counter {
    private int count = 0;
   
    // Method-level synchronization
    public synchronized void increment() {
        count++;
    }
   
    // Block-level synchronization
    public void decrement() {
        synchronized(this) {
            count--;
        }
    }
}
