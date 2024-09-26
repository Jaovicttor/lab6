import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;

public class TaskProducer implements Runnable {

    BlockingQueue<Task> bq;
    AtomicInteger index;
    List<Task> tasks;
    ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();

    public TaskProducer(BlockingQueue<Task> bq, AtomicInteger index){
        this.bq = bq;
        this.index = index;
        this.tasks = new ArrayList<Task>();
    }

    public void calculateMetrics() {
        List<Task> filteredTasks = tasks
        .stream()
        .filter((task) -> task.isFinished)
        .toList();

        if (filteredTasks.size() == 0) return;

        long total = 0;
        String metrics = "";

        for (Task task : filteredTasks) {
            metrics += "Executado: " + task.id + " Duração: " + task.execDuration + "\n";
            total += task.execDuration;
        }

        metrics += "Tempo Médio: " + (total / filteredTasks.size());

        System.out.println(metrics + "\n------------------------------------");
    }

    @Override
    public void run() {
        this.es.scheduleAtFixedRate(() -> {
            Task task = new Task(index.getAndIncrement());
            tasks.add(task);
            try {
                this.bq.put(task);
            } catch (Exception e) {
                System.out.println("Erro no taskProducer");
            }
        }, 0, 5, TimeUnit.SECONDS);

        this.es.scheduleAtFixedRate(() -> {
            this.calculateMetrics();
        }, 0, 5, TimeUnit.SECONDS);
    }
}