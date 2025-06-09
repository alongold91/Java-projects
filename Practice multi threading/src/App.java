public class App {

    public static void main(String[] args) throws Exception {
        System.out.println("Main is starting");
        // Thread thread1 = new Thread1("thread 1");
        // thread1.start();
        // // Thread thread2 = new Thread(new Thread2(), "thread 2");

        // // Same Effect as passing new Thread2()
        // Thread thread2 = new Thread(() -> {
        // for (int i = 0; i < 5; i++) {
        // System.out.println(Thread.currentThread() + ", " + 1);
        // }
        // }, "tread2");

        // thread2.start();

        // Stack stack = new Stack(5);

        // new Thread(() -> {
        // int counter = 0;
        // while (++counter < 10) {
        // System.out.println("Pushed " + stack.push(100));
        // }
        // }, "pusher").start();

        // new Thread(() -> {
        // int counter = 0;
        // while (++counter < 10) {
        // System.out.println("Popped " + stack.pop());
        // }
        // }, "popper").start();

        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread());
        }, "Our Thread");
        thread.start();
        try {
            // All the other threads but the main one will finish executing and only then "Main is exiting will be printed out"
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Main is exiting");
    }
}
