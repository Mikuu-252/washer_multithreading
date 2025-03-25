package pl.edu.pwr.dg.jp.lab05;

import java.util.LinkedList;
import java.util.Queue;

public class CarQueue {
    private int maxLen = 0;

    Queue<Car> queue = new LinkedList<>();
    Queue<Car> queue2 = new LinkedList<>();

    protected UpdateStatus updateStatus = null;

    public CarQueue(int maxLen) {
        this.maxLen = maxLen;
    }

    public Queue<Car> getQueue() {
        return queue;
    }

    public Queue<Car> getQueue2() {
        return queue2;
    }

    public synchronized void addCar(Car car) {
        while (queue.size() == maxLen || queue2.size() == maxLen)
            try {
                wait();
            } catch (InterruptedException e) {
                System.err.println("Przerwano watek");
            }

        if (queue.size() < queue2.size()) {
            if (queue.size() < maxLen) {
                queue.add(car);
            }
        } else {
            if (queue2.size() < maxLen) {
                queue2.add(car);
            }
        }

        if (queue.size() == 1) notify();
        if (queue2.size() == 1) notify();

        if (updateStatus != null) {
            updateStatus.update();
        }
    }

    public synchronized Car pollCar(int number) {
        Car car = null;

        while (queue.size() == 0 && queue2.size() == 0)
            try {
                wait();
            } catch (InterruptedException e) {
                System.err.println("Przerwano watek");
            }

        if (!queue.isEmpty() && number == 1) {
            car = queue.poll();
        }

        if (!queue2.isEmpty() && number == 2) {
            car = queue2.poll();
        }

        if (queue.size() == maxLen-1) notify();
        if (queue2.size() == maxLen-1) notify();

        if (updateStatus != null) {
            updateStatus.update();
        }

        return car;
    }

    public void setUpdateStatus(UpdateStatus updateStatus) {
        this.updateStatus = updateStatus;
    }

    public int getMaxLen() {
        return maxLen;
    }
}
