package pl.edu.pwr.dg.jp.lab05;

public class Device {
    private char type;
    private boolean inUse = false;
    private int direction = 0;

    public Device(char type) {
        this.type = type;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
