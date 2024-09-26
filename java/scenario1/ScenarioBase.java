import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class ScenarioBase {

    public static void main(String[] args) {
        
        AtomicInteger index = new AtomicInteger(0);
        ExecutorService producerExecutorService = Executors.newFixedThreadPool(5);
        ExecutorService consumerExecutorService = Executors.newFixedThreadPool(3);
        BlockingQueue<Task> bq = new LinkedBlockingDeque<Task>(10);
        
        for (int i = 0; i < 5; i++) {
            producerExecutorService.execute(new TaskProducer(bq, index));
        }

        for (int i = 0; i < 3; i++) {
            consumerExecutorService.execute(new Node(bq));
        }
    }
}
