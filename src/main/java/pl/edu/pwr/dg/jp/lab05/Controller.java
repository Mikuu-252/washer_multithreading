package pl.edu.pwr.dg.jp.lab05;

public class Controller extends MyThread{

    private int lastQueue = 1;

    public Controller(String name, int time, CarQueue carQueue, Washer washer) {
        super(name, time, carQueue, washer);
    }

    public void run() {
        try {
            while (!end) {
                Thread.sleep(time);

                if (washer.checkFreeStation() != -1) {
                    Car pooledCar = carQueue.pollCar(lastQueue);
                    if (pooledCar != null) {
                        washer.addCar(pooledCar);
                        pooledCar.setStatus(2);
                    }

                    if(lastQueue == 2) {
                        lastQueue = 1;
                    } else {
                        lastQueue = 2;
                    }
                }


                //System.out.println("Controller poll car "+ pooledCar.getCarName() +" form: " + lastQueue + " queue.");



                // Update
                updateStatus.update();
            }
        } catch (InterruptedException e) {
            System.err.println("Controller interrupted.");
        }
    }

    public int getLastQueue() {
        return lastQueue;
    }
}
