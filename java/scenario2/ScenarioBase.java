import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ScenarioBase {

    public static void main(String[] args) {
        
        AtomicInteger index = new AtomicInteger(0);
        ExecutorService producerExecutorService = Executors.newFixedThreadPool(3);
        ExecutorService consumerExecutorService = Executors.newFixedThreadPool(3);
        PriorityBlockingQueue<Task> bq = new PriorityBlockingQueue<Task>(10);

        producerExecutorService.execute(new TaskProducer(bq, index, 0));
        producerExecutorService.execute(new TaskProducer(bq, index, 1));
        producerExecutorService.execute(new TaskProducer(bq, index, 2));

        for (int i = 0; i < 3; i++) {
            consumerExecutorService.execute(new Node(bq));
        }
    }
}
