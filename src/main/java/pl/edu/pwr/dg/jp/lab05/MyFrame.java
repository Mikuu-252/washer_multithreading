package pl.edu.pwr.dg.jp.lab05;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Queue;

public class MyFrame extends JFrame {

    private JLabel[] carsLabel;
    private JLabel carQueue1Label, carQueue2Label;
    private JLabel controllerLabel1, controllerLabel2;
    private JLabel waterLabel, upperStationlabel, lowerStationlabel, liquidLabel;

    private int carNumber;
    private int washerNumber;

    private Car[] cars;
    private Controller controller;
    private CarQueue carQueue;
    private Washer washer;


    MyFrame getThis() {
        return this;
    }

    public void utworzWatki(){


        //Queue
        carQueue = new CarQueue(6);
        carQueue.setUpdateStatus(
                new UpdateStatus(){
                    public void update(){
                        StringBuffer s1 = new StringBuffer();
                        StringBuffer s2 = new StringBuffer();

                        Queue<Car> q1 = carQueue.getQueue();
                        Queue<Car> q2 = carQueue.getQueue2();

                        int maxLen = carQueue.getMaxLen();
                        int len1 = q1.size();
                        int len2 = q2.size();

                        for(Car e : q1) {
                            s1.append(e.getCarName());
                        }

                        for(Car e : q2) {
                            s2.append(e.getCarName());
                        }

                        for (int i = 0; i < maxLen-len1; i++) {
                            s1.append(".");
                        }

                        for (int i = 0; i < maxLen-len2; i++) {
                            s2.append(".");
                        }

                        carQueue1Label.setText(s1.reverse().toString());
                        carQueue2Label.setText(s2.reverse().toString());
                    }
                }
        );

        //Washer
        washer = new Washer(washerNumber);

        StringBuffer s1 = new StringBuffer();
        StringBuffer s2 = new StringBuffer();
        for (int i = 0; i < washerNumber-1; i++) {
            s1.append("     W");
            s2.append("     L");
        }
        waterLabel.setText(s1.toString());
        liquidLabel.setText(s2.toString());

        washer.setUpdateStatus(
                new UpdateStatus(){
                    public void update(){
                        StringBuffer s1 = new StringBuffer();
                        StringBuffer s2 = new StringBuffer();

                        int stationMaxLen = washer.getStationsNumber();
                        Device[] waterDevices = washer.getWaterDevices();
                        Device[] liquidDevices = washer.getLiquidDevices();
                        Car[] stations = washer.getStations();

                        //Station 0
                        s1.append("| -");
                        s2.append("| ");
                        if (stations[0] != null) {s2.append(stations[0].getCarName());} else {s2.append("-");}

                        //Stations middle
                        for (int i = 0; i < stationMaxLen-1; i++) {
                            if(waterDevices[i].getDirection() == -1){s1.append(" <");} else {s1.append("  ");}
                            if(liquidDevices[i].getDirection() == -1){s2.append(" <");} else {s2.append("  ");}

                            s1.append("|");
                            s2.append("|");

                            if(waterDevices[i].getDirection() == 1){s1.append("> ");} else {s1.append("  ");}
                            if(liquidDevices[i].getDirection() == 1){s2.append("> ");} else {s2.append("  ");}

                            s1.append("-");
                            if (stations[i+1] != null) {s2.append(stations[i+1].getCarName());} else {s2.append("-");}
                        }

                        //Station max
                        s1.append(" |");
                        s2.append(" |");

                        upperStationlabel.setText(s1.toString());
                        lowerStationlabel.setText(s2.toString());
                    }
                }
        );

        // Controller
        controller = new Controller("Controller", 1500, carQueue, washer);

        controller.setUpdateStatus(
                new UpdateStatus(){
                    public void update(){
                        int lastQueue = controller.getLastQueue();
                        if(lastQueue == 1) {
                            controllerLabel1.setText("    >    ");
                            controllerLabel2.setText("    -    ");
                        } else {
                            controllerLabel1.setText("    -    ");
                            controllerLabel2.setText("    >    ");
                        }
                    }
                }
        );

        // Cars
        cars = new Car[carNumber];

        for (int i = 0; i < carNumber; i++) {
            // Name
            char letter = (char) ('A' + i);
            // Time
            int time = (int)(Math.random() * 901 + 100) + 1000;
            cars[i] = new Car("Car \"" + letter + "\"", time, Character.toString(letter), carQueue, washer);

            // Update
            int finalI = i;
            cars[i].setUpdateStatus(
                    new UpdateStatus(){
                        public void update() {
                            StringBuffer s = new StringBuffer();
                            String name = cars[finalI].getCarName();

                            s.append(name); s.append(" ");

                            carsLabel[finalI].setText(s.toString());
                        }
                    }
            );
        }
    }

    public void uruchomWatki(){
        MyThread.setEnd(false);

        for(MyThread car : cars) {
            car.start();
        }

        controller.start();

    }

    public void zatrzymajWatki() {
        MyThread.setEnd(true);
    }
    public void createComponents() {

        JButton buttonT = new JButton("Start");
        buttonT.setMnemonic(KeyEvent.VK_T);
        JButton buttonP = new JButton("Stop");
        buttonP.setMnemonic(KeyEvent.VK_P);

        carsLabel = new JLabel[carNumber];
        for (int i = 0; i < carNumber; i++) {
            carsLabel[i] = new JLabel("-");
        }


        carQueue1Label = new JLabel(".......");
        carQueue2Label = new JLabel(".......");

        controllerLabel1 = new JLabel("    -    ");
        controllerLabel2 = new JLabel("    -    ");

        waterLabel = new JLabel("      W     W");
        upperStationlabel = new JLabel("|  -  |  -  |  -  |");
        lowerStationlabel = new JLabel("|  -  |  -  |  -  |");
        liquidLabel = new JLabel("      P     P");

        JLabel title1 = new JLabel("Pojazdy ");
        JLabel title2 = new JLabel("Wjazd      ");
        JLabel title3 = new JLabel("Porządkowy ");
        JLabel title4 = new JLabel("Stanowiska ");


        JLabel e1 = (carsLabel.length > 0 && carsLabel[0] != null) ? carsLabel[0] : new JLabel("-");
        JLabel e2 = (carsLabel.length > 1 && carsLabel[1] != null) ? carsLabel[1] : new JLabel("-");
        JLabel e3 = (carsLabel.length > 2 && carsLabel[2] != null) ? carsLabel[2] : new JLabel("-");
        JLabel e4 = (carsLabel.length > 3 && carsLabel[3] != null) ? carsLabel[3] : new JLabel("-");
        JLabel e5 = (carsLabel.length > 4 && carsLabel[4] != null) ? carsLabel[4] : new JLabel("-");


        JLabel e6 = new JLabel("           ");
        JLabel e8 = new JLabel("           ");
        JLabel e10 = new JLabel("           ");

        JLabel controller = new JLabel("    P    ");


        JLabel e11 = new JLabel("           ");
        JLabel e15 = new JLabel("           ");
        JLabel e20 = new JLabel("           ");

        JPanel p = new JPanel();

        int offset = 0;
        if(carNumber > 5) offset = carNumber - 5;

        p.setLayout(new GridLayout(6 + offset, 4, 10, 10));


        p.add(title1);  p.add(title2);	        p.add(title3);              p.add(title4);
        p.add(e1);	    p.add(e6);  	        p.add(e11);                 p.add(waterLabel);
        p.add(e2);	    p.add(carQueue1Label);	p.add(controllerLabel1);    p.add(upperStationlabel);
        p.add(e3); 	    p.add(e8);              p.add(controller);          p.add(lowerStationlabel);
        p.add(e4);		p.add(carQueue2Label);  p.add(controllerLabel2);    p.add(liquidLabel);
        p.add(e5);		p.add(e10);		        p.add(e15);                 p.add(e20);

        JLabel[] newSpace = new JLabel[offset*3];

        for (int i = 5; i < carNumber; i++) {
            newSpace[(i-5) % 3] = new JLabel("           ");
            newSpace[(i-4) % 3] = new JLabel("           ");
            newSpace[(i-3) % 3] = new JLabel("           ");

            p.add(carsLabel[i]);
            p.add(newSpace[(i-5) % 3]);
            p.add(newSpace[(i-4) % 3]);
            p.add(newSpace[(i-3) % 3]);
        }

        ActionListener a = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(	((JButton)e.getSource()).getText().compareTo("Start")==0){
                    System.out.println("start");
                    utworzWatki();
                    uruchomWatki();
                }

                if(	((JButton)e.getSource()).getText().compareTo("Stop")==0) {
                    System.out.println("stop");
                    zatrzymajWatki();
                }
            }
        };

        buttonT.addActionListener(a);
        buttonP.addActionListener(a);

        JPanel pb = new JPanel();
        pb.add(buttonT);
        pb.add(buttonP);

        this.getContentPane().setLayout(new GridLayout(2, 1));


        this.getContentPane().add(p);
        this.getContentPane().add(pb);
    }

    public MyFrame(String arg0, int carNumber, int washerNumber) {
        super(arg0);
        this.carNumber = carNumber;
        this.washerNumber = washerNumber;
        createComponents();
        pack();
    }
}

