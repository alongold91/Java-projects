    public class BankAccount {
    private int balance = 1000;
    private final ReentrantLock lock = new ReentrantLock();
   
    public void withdraw(int amount) {
        if (lock.tryLock()) { // Non-blocking attempt
            try {
                if (balance >= amount) {
                    balance -= amount;
                    System.out.println("Withdrew: " + amount);
                }
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("Could not acquire lock, try later");
        }
    }
   
    public void withdrawWithTimeout(int amount) throws InterruptedException {
        if (lock.tryLock(2, TimeUnit.SECONDS)) { // Wait max 2 seconds
            try {
                balance -= amount;
            } finally {
                lock.unlock();
            }
        }
    }
}
