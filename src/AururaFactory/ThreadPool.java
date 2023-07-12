package AururaFactory;

import com.girlkun.server.Manager;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Stole By Arriety
 */
public class ThreadPool {

    static ThreadPool pool_reciever;
    static ThreadPool pool_sender;

    ExecutorService pool;

    public void execute(Runnable task) {
        pool.submit(task);
    }
    
    public static ThreadPool reciever() {
        if (pool_reciever == null) {
            pool_reciever = new ThreadPool();
            pool_reciever.pool = Executors.newFixedThreadPool(Manager.MAX_POOL);
        }
        return pool_reciever;
    }

    public static ThreadPool sender() {
        if (pool_sender == null) {
            pool_sender = new ThreadPool();
            pool_sender.pool = Executors.newFixedThreadPool(Manager.MAX_POOL);
        }
        return pool_sender;
    }

    public static void main(String[] args) {

    }

}
