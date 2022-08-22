package pl.jano.SRX.Window3;

import pl.jano.CA.Window1.ColorManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Visualisation3 {

    public static void run(int[][] tab, ColorManager cm,int XX,int YY,int num){

        int width=tab.length;
        int height=tab.length;

        final GUI3 window = new GUI3(tab,cm, XX, YY, num);
        JFrame frame = new JFrame("SRX");
        frame.getContentPane().add(window);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        frame.setSize(width*4,height*4);
        frame.setResizable(true);


        new Timer(900, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                window.updateTab();
                window.repaint();
                frame.setTitle("SRX,  " + Integer.toString( window.getCounter() )+" iteration");
            }
        }
        ).start();
}}
