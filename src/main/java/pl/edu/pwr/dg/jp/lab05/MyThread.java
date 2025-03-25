package pl.edu.pwr.dg.jp.lab05;

abstract public class MyThread extends Thread {
    protected int time;
    static protected boolean end =false;

    protected UpdateStatus updateStatus;
    protected CarQueue carQueue;
    protected Washer washer;

    public static void setEnd(boolean e) {
        end = e;
    }

    public static boolean getEnd() {
        return end;
    }

    public MyThread(String name, int time, CarQueue carQueue, Washer washer)  {
        super(name);
        this.time = time;
        this.carQueue = carQueue;
        this.washer = washer;
    }

    public void setUpdateStatus(UpdateStatus updateStatus) {
        this.updateStatus = updateStatus;
    }
}
