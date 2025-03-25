package pl.edu.pwr.dg.jp.lab05;

public class Car extends MyThread {

    private String carName;

    private int status;
    private int washStep;

    public Car(String name, int time, String carName, CarQueue carQueue, Washer washer) {
        super(name, time, carQueue, washer);
        this.carName = carName;
    }

    @Override
    public void run() {
            while (!end) {
                if(status == 0) {
                    carWait();
                    carQueue.addCar(this);
                    status = 1;
                    washStep = 0;
                } else if (status == 2) {
                    washStep += washer.wash(carName);

                    if(washStep == 3) {status = 3;}

                } else if (status == 3) {
                    carWait();
                    washer.removeCar();
                    status = 0;
                }


                // Update
                updateStatus.update();
            }
    }

    public void carWait() {
        try {
            int waitTime = time + (int)(Math.random()*1000);
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            System.err.println("Car " + carName + " interrupted.");
        }
    }

    public String getCarName() {
        return carName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getWashStep() {
        return washStep;
    }

    public void setWashStep(int washStep) {
        this.washStep = washStep;
    }

    public void setUpdateStatus(UpdateStatus updateStatus) {
        this.updateStatus = updateStatus;
    }
}
