import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;

public class TaskProducer2 implements Runnable {

    BlockingQueue<Task2> bq;
    AtomicInteger index;
    List<Task2> tasks;
    ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();
    int priority;

    public TaskProducer2(BlockingQueue<Task2> bq, AtomicInteger index, int priority){
        this.bq = bq;
        this.index = index;
        this.tasks = new ArrayList<Task2>();
        this.priority = priority;
    }

    private int getDelay() {
        switch (this.priority) {
            case 0:
                return 13;
            case 1:
                return 7;
            case 2:
                return 2;
            default:
                return 0;
        }
    }

    public void calculateMetrics() {
        List<Task2> filteredTasks = tasks
        .stream()
        .filter((task) -> task.isFinished)
        .toList();

        if (filteredTasks.size() == 0) return;

        long total = 0;
        String metrics = "";

        for (Task2 task : filteredTasks) {
            metrics += "Executado: " + task.id + " Duração: " + task.execDuration + "\n";
            total += task.execDuration;
        }

        metrics += "Tempo Médio: " + (total / filteredTasks.size());

        System.out.println(metrics + "\n------------------------------------");
    }

    @Override
    public void run() {
        this.es.scheduleAtFixedRate(() -> {
            Task2 task = new Task2(index.getAndIncrement(), this.priority);
            tasks.add(task);
            try {
                this.bq.put(task);
            } catch (Exception e) {
                System.out.println("Erro no taskProducer");
            }
        }, 0, this.getDelay(), TimeUnit.SECONDS);

        this.es.scheduleAtFixedRate(() -> {
            this.calculateMetrics();
        }, 0, 5, TimeUnit.SECONDS);
    }
}