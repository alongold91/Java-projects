    public class OptimisticCounter {
    private final AtomicInteger count = new AtomicInteger(0);
   
    public void increment() {
        int current;
        int updated;
        do {
            current = count.get();        // Read current value
            updated = current + 1;        // Calculate new value
        } while (!count.compareAndSet(current, updated)); // Update only if unchanged
    }
}
