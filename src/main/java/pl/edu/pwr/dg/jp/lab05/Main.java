package pl.edu.pwr.dg.jp.lab05;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) { }

        Font defaultFont = new Font("Monospaced", Font.PLAIN, 14);
        UIManager.put("Label.font", defaultFont);

        MyFrame frame = new MyFrame("MyApplication",5,3);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
