import java.util.Random;

public class Task implements Comparable<Task> {
    long id;
    boolean isFinished;
    long execDuration;
    int priority;

    public Task(long id, int priority) {
        this.id = id;
        this.isFinished = false;
        this.priority = priority;
    }

    @Override
    public int compareTo(Task arg0) {
        return this.priority - arg0.priority;
    }

    public void execute() {
        try {
            // generating a number between 1000 and 15000
            this.execDuration = 1000 + (long) (new Random().nextFloat() * (15000 - 1000));
            Thread.sleep(execDuration);
            this.isFinished = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
