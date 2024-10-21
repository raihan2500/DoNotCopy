import java.util.LinkedList;
import java.util.*;

public class ThreadSafeQueue<T> {
    private LinkedList<T> list = new LinkedList<>();
    private final Object lock = new Object();

    public void enqueue(T item) {
        synchronized (lock) {
            list.addLast(item);
            lock.notifyAll();
        }
    }

    public T dequeue() throws InterruptedException {
        synchronized (lock) {
            while (list.isEmpty()) {
                lock.wait();
            }
            return list.removeFirst();
        }
    }

    public boolean isEmpty() {
        synchronized (lock) {
            return list.isEmpty();
        }
    }

    public static void main(String[] args) {
        ThreadSafeQueue<Integer> queue = new ThreadSafeQueue<>();

        // Example usage
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                queue.enqueue(i);
                System.out.println("Enqueued: " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    int item = queue.dequeue();
                    System.out.println("Dequeued: " + item);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        producer.start();
        consumer.start();
    }
}
