package pl.jano.CA.Window1;

import javax.swing.*;

import java.awt.*;
import java.beans.Transient;


public class GUI extends JPanel {

    public int[][] tab;
    public ColorManager colorManager = new ColorManager();


    public GUI(int width, int height ){
        this.tab= new int[width/4][height/4];
    }

    public void updateTab(int[][] input){
        this.tab=input;
    }

    @Override
    @Transient
    public Dimension getPreferredSize() {
        return new Dimension(tab.length * 4, tab[0].length * 4);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color gColor = g.getColor();


        int value=-1;

        for (int i = 0; i < tab.length; i++) {
            for (int j = 0; j < tab[i].length; j++) {
                value=tab[i][j];
                if(value>0){
                    g.setColor(colorManager.getOrGenerate(value));
                    g.fillRect(j * 4, i * 4, 4, 4);
                }



            }
        }

        g.setColor(gColor);

    }

}
