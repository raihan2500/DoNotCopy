import java.util.ArrayList;
import java.util.List;

public class Thread1 {
    private static final List<Integer> list = new ArrayList<>();

    public static void main(String[] args) {
        Thread addThread = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                list.add(i);
            }
        });

        Thread removeThread = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                if (!list.isEmpty()) {
                    list.remove(0);
                }
            }
        });

        addThread.start();
        removeThread.start();

        try {
            addThread.join();
            removeThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final list size: " + list.size());
    }
}
