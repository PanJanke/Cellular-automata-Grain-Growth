package pl.jano.SRX.Window3;

import pl.jano.CA.Window1.ColorManager;
import pl.jano.Other.SaveStructure;
import pl.jano.SRX.SrxManager;

import javax.swing.*;
import java.awt.*;
import java.beans.Transient;

public class GUI3 extends JPanel {

    ColorManager colorManager;

    boolean flag=true;
    public int tab[][];
    public SrxManager Srx;

    public GUI3(int input[][],ColorManager cm,int XX,int YY, int num){
        System.out.println(XX);
        System.out.println(YY);
        System.out.println(num);
        int width=input.length;
        int height=input.length;
        Srx=new SrxManager(input,cm,XX,YY,num);
        this.tab = new int[width/4][height/4];
        colorManager=Srx.colorManager;
    }

    int counter=0;

    public void updateTab(){
        if(Srx.getNonRecrystalizedNum()!=0) {
            Srx.oneIteration();
            this.tab=Srx.getIds();
            counter++;
            if(counter==5){
                Srx.setRandomSeeds(100);
            }
        }
        else if(flag){
            flag=!flag;
            SaveStructure.saveToTxt(3,counter, this.tab);


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
