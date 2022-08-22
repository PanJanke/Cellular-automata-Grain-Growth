package pl.jano.MC.Window2;

import pl.jano.CA.Window1.ColorManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Visualisation2 {


    public static int[][] changeTabs(int tab[][]){
        int result[][]=new int[tab.length][tab.length];
        for(int i=0;i<tab.length;i++)
            for(int j=0;j<tab.length;j++)
                result[i][j]=tab[i][j];
        return result;
    }

    public static void run(int[][] tab, ColorManager cm,double kt,int numOfIteration){

        int width=tab.length;
        int height=tab.length;

        final GUI2 window = new GUI2(changeTabs(tab),cm, kt, numOfIteration);
        JFrame frame = new JFrame("MC");
        frame.getContentPane().add(window);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        frame.setSize(width*4,height*4);
        frame.setResizable(true);


        new Timer(0, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                window.updateTab();
                window.repaint();
                frame.setTitle("MC GROWTH,  " + Integer.toString( window.getCounter() )+" iteration");
            }
        }
        ).start();



    }

}
