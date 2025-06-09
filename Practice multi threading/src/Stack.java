public class Stack {
    private int[] array;
    private int stackTop;
    // which ever thread that has access to this class has to pass a lock object
    Object lock;

    public Stack(int capacity) {
        array = new int[capacity];
        stackTop = -1;
        lock = new Object();
    }

    public boolean push(int element) {
        /* 
         By using the synchronized keyword we are saying "Hey, this is a critical section,
        no more than 1 thread allowed here at a time */
        synchronized (lock) {

            if (isFull())
                return false;
            ++stackTop;
    
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
            array[stackTop] = element;
            return true;
        }
    }

    public int pop() {
        synchronized(lock) {

            if (isEmpty())
                return Integer.MIN_VALUE;
            int obj = array[stackTop];
            array[stackTop] = Integer.MIN_VALUE;
    
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
            stackTop--;
            return obj;
        }
    }

    public boolean isEmpty() {
        return stackTop < 0;
    }

    public boolean isFull() {
        return stackTop >= array.length - 1;
    }
}
