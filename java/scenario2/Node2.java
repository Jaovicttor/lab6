import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Node2 implements Runnable {

    BlockingQueue<Task2> bq;

    public Node2(BlockingQueue<Task2> bq){
      this.bq = bq;
    }

    @Override
    public void run() {
      while (true) {
        try {
          Task2 task = bq.take();
          task.execute();
        } catch (Exception e) {
          System.out.println("Erro no node");
        }
      }
    }
}
