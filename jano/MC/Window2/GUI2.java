package pl.jano.MC.Window2;

import pl.jano.CA.Window1.ColorManager;
import pl.jano.MC.growthMC;
import pl.jano.Other.SaveStructure;

import javax.swing.*;
import java.awt.*;
import java.beans.Transient;

public class GUI2 extends JPanel {

    ColorManager colorManager;

    boolean flag=true;
    public int tab[][];
    public growthMC Mc;
    public int num=40;
    public int counter=0;


    public GUI2(int input[][],ColorManager cm, double kt,int numOfIteration){
        this.colorManager=cm;
        int width=input.length;
        int height=input.length;
        Mc=new growthMC(input, kt);
        this.tab = new int[width/4][height/4];
        this.num=numOfIteration;
    }



    public void updateTab(){
        if(counter<num) {
            Mc.oneIteration();
            this.tab=Mc.tab;
            counter++;
        }
        if(flag){
            flag=!flag;
            SaveStructure.saveToTxt(1,num, this.tab);


        }
    }

    public int getCounter(){
        return counter;
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
