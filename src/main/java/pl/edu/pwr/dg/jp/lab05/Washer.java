package pl.edu.pwr.dg.jp.lab05;

import java.util.ArrayList;
import java.util.Arrays;

public class Washer {

    private int stationsNumber = 0;
    private int stationsInUse = 0;
    private int index = 0;
    private Car stations[];

    private Device waterDevices[];
    private Device liquidDevices[];

    protected UpdateStatus updateStatus = null;

    public Washer(int stationsNumber) {
        this.stationsNumber = stationsNumber;
        stations = new Car[stationsNumber];
        waterDevices = new Device[stationsNumber - 1];
        liquidDevices = new Device[stationsNumber - 1];

        for (int i = 0; i < stationsNumber-1; i++) {
            waterDevices[i] = new Device('W');
            liquidDevices[i] = new Device('L');
        }
    }

    public void showStatus() {
        for (int i = 0; i < stations.length; i++) {
            System.out.println("S " + i + ": " + stations[i]);
        }

        for (int i = 0; i < stations.length-1; i++) {
            int next = i+1;
            System.out.println("S" + i + "-" + next + ": " + waterDevices[i].getType() + " " + waterDevices[i].getDirection() + " " + waterDevices[i].isInUse());
            System.out.println("S" + i + "-" + next + ": " + liquidDevices[i].getType() + " " + waterDevices[i].getDirection() + " " + waterDevices[i].isInUse());
        }
    }

    public synchronized void addCar(Car car) {
        while (stationsInUse == stationsNumber)
            try {
                wait();
            } catch (InterruptedException e) {
                System.err.println("Przerwano watek");
            }

        int freeStation = checkFreeStation();
        stations[freeStation] = car;
        stationsInUse++;

        if (stationsInUse == 1) notify();

        if (updateStatus != null) {
            updateStatus.update();
        }
    }

    public synchronized Car removeCar() {
        Car car;
        while (stationsInUse == 0)
            try {
                wait();
            } catch (InterruptedException e) {
                System.err.println("Przerwano watek");
            }

        car = stations[index];
        stations[index] = null;
        index = (index + 1) % stationsNumber;

        stationsInUse--;

        if (stationsInUse == stationsNumber-1) notify();

        if (updateStatus != null) {
            updateStatus.update();
        }

        return car;
    }

    public int wash(String carName) {
        Car car = null;
        int carIdx = -1;
        int deviceIdx1 = -1;
        int deviceIdx2 = -1;

        //Find car in stations
        for (int i = 0; i < stations.length; i++) {
            if (stations[i] != null && stations[i].getCarName().equals(carName)) {
                car = stations[i];
                carIdx = i;
            }
        }
        if (carIdx < 0) { return 0; }

        // Find device can be used
        if (carIdx == 0) { deviceIdx1=0;}
        else if (carIdx == stationsNumber - 1) { deviceIdx2=stationsNumber-2; }
        else {
            deviceIdx1 = carIdx - 1;
            deviceIdx2 = carIdx;
        }

        // Try wash
        if(car.getWashStep() == 0 || car.getWashStep() == 2) {
            if(deviceIdx1 >= 0) {
                if(!waterDevices[deviceIdx1].isInUse()) {
                    waterDevices[deviceIdx1].setInUse(true);

                    if(carIdx == deviceIdx1) {
                        waterDevices[deviceIdx1].setDirection(-1);
                    } else {
                        waterDevices[deviceIdx1].setDirection(1);
                    }

                    if (updateStatus != null) {
                        updateStatus.update();
                    }

                    car.carWait();

                    waterDevices[deviceIdx1].setInUse(false);
                    waterDevices[deviceIdx1].setDirection(0);

                    if (updateStatus != null) {
                        updateStatus.update();
                    }

                    return 1;
                } else {
                    return 0;
                }
            } else if (deviceIdx2 >= 0) {
                if(!waterDevices[deviceIdx2].isInUse()) {
                    waterDevices[deviceIdx2].setInUse(true);

                    if(carIdx == deviceIdx2) {
                        waterDevices[deviceIdx2].setDirection(-1);
                    } else {
                        waterDevices[deviceIdx2].setDirection(1);
                    }

                    if (updateStatus != null) {
                        updateStatus.update();
                    }

                    car.carWait();

                    waterDevices[deviceIdx2].setInUse(false);
                    waterDevices[deviceIdx2].setDirection(0);

                    if (updateStatus != null) {
                        updateStatus.update();
                    }

                    return 1;
                }
            } else {
                return 0;
            }
        } else if(car.getWashStep() == 1) {
            if(deviceIdx1 >= 0) {
                if(!liquidDevices[deviceIdx1].isInUse()) {
                    liquidDevices[deviceIdx1].setInUse(true);

                    if(carIdx == deviceIdx1) {
                        liquidDevices[deviceIdx1].setDirection(-1);
                    } else {
                        liquidDevices[deviceIdx1].setDirection(1);
                    }

                    if (updateStatus != null) {
                        updateStatus.update();
                    }

                    car.carWait();

                    liquidDevices[deviceIdx1].setInUse(false);
                    liquidDevices[deviceIdx1].setDirection(0);

                    if (updateStatus != null) {
                        updateStatus.update();
                    }

                    return 1;
                } else {
                    return 0;
                }
            } else if (deviceIdx2 >= 0) {
                if(!liquidDevices[deviceIdx2].isInUse()) {
                    liquidDevices[deviceIdx2].setInUse(true);

                    if(carIdx == deviceIdx2) {
                        liquidDevices[deviceIdx2].setDirection(-1);
                    } else {
                        liquidDevices[deviceIdx2].setDirection(1);
                    }

                    if (updateStatus != null) {
                        updateStatus.update();
                    }

                    car.carWait();

                    liquidDevices[deviceIdx2].setInUse(false);
                    liquidDevices[deviceIdx2].setDirection(0);

                    if (updateStatus != null) {
                        updateStatus.update();
                    }

                    return 1;
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        }
        return 0;
    }

    public int checkFreeStation() {
        for (int i = 0; i < stations.length; i++) {
            if(stations[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public int getStationsNumber() {
        return stationsNumber;
    }

    public Device[] getWaterDevices() {
        return waterDevices;
    }

    public Device[] getLiquidDevices() {
        return liquidDevices;
    }

    public Car[] getStations() {
        return stations;
    }

    public void setUpdateStatus(UpdateStatus updateStatus) {
        this.updateStatus = updateStatus;
    }

}
