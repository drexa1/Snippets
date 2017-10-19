package me;

import java.util.ArrayList;
import java.util.HashMap;

public class WorkerDispatcher {

    private HashMap<Integer, Worker> workers = new HashMap<Integer, Worker>();

    public static void main(String[] args) {

        WorkerDispatcher d = new WorkerDispatcher();

        d.acquireWorker(1).performTask("Task11");
        d.acquireWorker(2).performTask("Task21");
        System.out.println(String.join(", ", d.acquireWorker(2).getTasks()));
        d.releaseWorker(2);
        d.acquireWorker(1).performTask("Task12");
        System.out.println(String.join(", ", d.acquireWorker(1).getTasks()));
        d.releaseWorker(1);
    }

    public Iterable<Worker> getWorkers() {
        return this.workers.values();
    }

    public Worker acquireWorker(int id) {
        Worker w = this.workers.getOrDefault(id, null);
        if (w == null) {
            w = new Worker(id);
            this.workers.put(id, w);
        }
        return w;
    }

    public void releaseWorker(int id) {
        Worker w = this.workers.getOrDefault(id, null);
        if (w == null)
            throw new IllegalArgumentException();

        //FIXME: w.dispose();
        this.workers.remove(id);
    }

    class Worker {

        private int id;
        private ArrayList<String> tasks = new ArrayList<String>();

        public Worker(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }
        public Iterable<String> getTasks() {
            return this.tasks;
        }

        public void performTask(String task) {
            if (this.tasks == null)
                throw new IllegalStateException(this.getClass().getName());

            this.tasks.add(task);
        }

        public void dispose() {
            this.tasks = null;
        }
    }
}