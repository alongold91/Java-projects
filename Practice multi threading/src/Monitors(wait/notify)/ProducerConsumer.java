    public class ProducerConsumer {
    private Queue<Integer> queue = new LinkedList<>();
    private final int CAPACITY = 5;
   
    public synchronized void produce(int item) throws InterruptedException {
        while (queue.size() == CAPACITY) {
            wait(); // Wait until space is available
        }
        queue.add(item);
        System.out.println("Produced: " + item);
        notify(); // Wake up waiting consumers
    }
   
    public synchronized int consume() throws InterruptedException {
        while (queue.isEmpty()) {
            wait(); // Wait until item is available
        }
        int item = queue.poll();
        System.out.println("Consumed: " + item);
        notify(); // Wake up waiting producers
        return item;
    }
}
