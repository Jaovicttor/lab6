import java.sql.Date;
import java.util.Random;

public class Task {
    long id;
    boolean isFinished;
    long execDuration;

    public Task(long id) {
        this.id = id;
        this.isFinished = false;
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
