public class OptimisticStampedExample {
    private final StampedLock lock = new StampedLock();
    private double x = 0.0;
    private double y = 0.0;
   
    // Optimistic read - no actual locking
    public double calculateDistance() {
        long stamp = lock.tryOptimisticRead(); // Get optimistic stamp
       
        // Read values (might be inconsistent if writers are active)
        double currentX = x;
        double currentY = y;
       
        // Check if data was modified during our read
        if (!lock.validate(stamp)) {
            // Data was modified, fall back to pessimistic read lock
            stamp = lock.readLock();
            try {
                currentX = x;
                currentY = y;
            } finally {
                lock.unlockRead(stamp);
            }
        }
       
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }
   
    // Writer method
    public void updateCoordinates(double newX, double newY) {
        long stamp = lock.writeLock(); // Exclusive write lock
        try {
            x = newX;
            y = newY;
            System.out.println("Updated coordinates to (" + x + ", " + y + ")");
        } finally {
            lock.unlockWrite(stamp);
        }
    }
   
    // Another optimistic read example
    public String getCoordinates() {
        long stamp = lock.tryOptimisticRead();
       
        double currentX = x;
        double currentY = y;
       
        // Validate that no writes occurred during our read
        if (!lock.validate(stamp)) {
            // Fall back to pessimistic read
            stamp = lock.readLock();
            try {
                currentX = x;
                currentY = y;
            } finally {
                lock.unlockRead(stamp);
            }
        }
       
        return "(" + currentX + ", " + currentY + ")";
    }
}
