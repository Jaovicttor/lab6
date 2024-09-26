import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Node implements Runnable {

    BlockingQueue<Task> bq;

    public Node(BlockingQueue<Task> bq){
      this.bq = bq;
    }

    @Override
    public void run() {
      while (true) {
        try {
          Task task = bq.take();
          task.execute();
        } catch (Exception e) {
          System.out.println("Erro no node");
        }
      }
    }
}
