package pl.jano.CA;

import pl.jano.GUIChoice.menu2;
import pl.jano.Other.SaveStructure;
import pl.jano.CA.Window1.GUI;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class Machine {

    public int[][] result;
    public int[][] tabA;
    public int[][] tabB;
    boolean flag;
    public int counterIteration;
    boolean change;
    boolean VN;
    GUI window;
    JFrame frame;




    public Machine(int[][] inputTab,boolean vn){
        this.flag=true;
        this.change=true;
        this.tabA=inputTab;
        int width=inputTab.length;
        this.tabB=new int[width][width];
        this.result=tabA;
        counterIteration=0;
        VN=vn;

        setWindow(width,width);
    }



    public void setWindow(int width,int height){
        window = new GUI(width,height);
        frame = new JFrame("CA");
        frame.getContentPane().add(window);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        frame.setSize(width*4,height*4);
        frame.setResizable(false);


    }




    public void run(){

        while(change){
            if(VN)
                result=working();
            else
                result=working2();

            counterIteration++;
            window.updateTab(result);
            window.validate();
            frame.validate();
            window.repaint();
            frame.setTitle("GROWTH,  " + Integer.toString( counterIteration)+" iteration");

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        SaveStructure.saveToTxt(0,counterIteration,tabA);
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new menu2(tabA, window.colorManager);
            }
        });
    }

    public int[][] working(){

        if(this.flag){
            change= updateVonNeumann(tabA,tabB);
            this.flag=!this.flag;
            return tabB;
        }
        else{
            change = updateVonNeumann(tabB,tabA);
            this.flag=!this.flag;
            return tabA;
        }
    }

    public int[][] working2(){

        if(this.flag){
            change=updateBarrierPentagonRandom(tabA,tabB);
            this.flag=!this.flag;
            return tabB;
        }
        else{
            change = updateBarrierPentagonRandom(tabB,tabA);
            this.flag=!this.flag;
            return tabA;
        }

    }



   boolean updateVonNeumann(int[][] tabA, int[][] tabB){ //A - input  B - output

        boolean change=false;

        int width = tabA.length;
        int height = tabA[0].length;
        Map<Integer,Integer> neighbours = new HashMap<Integer,Integer>();
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {

                //KOLOR
                if(tabA[i][j]!=0)
                    tabB[i][j] = tabA[i][j];
                    //PUSTE
                else {

                    //gora
                    if(j==0){
                        if (tabA[i][height-1] > 0)
                            neighbours.put(tabA[i][height-1], 0);
                    }

                    else if (tabA[i][j - 1] > 0)
                        neighbours.put(tabA[i][j - 1], 0);

                    //DOL
                    if(j==height-1){
                        if (tabA[i][0] > 0)
                            neighbours.put(tabA[i][0], 0);
                    }

                    else if (tabA[i][j + 1] > 0) {
                        if (neighbours.get(tabA[i][j + 1]) == null)
                            neighbours.put(tabA[i][j + 1], 0);
                        else
                            neighbours.put(tabA[i][j + 1], neighbours.get(tabA[i][j + 1]) + 1);
                    }


                    if(i==0){
                        if (tabA[width-1][j] > 0)
                            neighbours.put(tabA[width-1][j], 0);
                    }

                    else if (tabA[i - 1][j] > 0) { //lewo
                        if (neighbours.get(tabA[i - 1][j]) == null)
                            neighbours.put(tabA[i - 1][j], 0);
                        else
                            neighbours.put(tabA[i - 1][j], neighbours.get(tabA[i - 1][j]) + 1);
                    }

                    if(i==width-1){
                        if (tabA[0][j] > 0)
                            neighbours.put(tabA[0][j], 0);
                    }

                    else if (tabA[i + 1][j] > 0) { //prawo
                        if (neighbours.get(tabA[i + 1][j]) == null)
                            neighbours.put(tabA[i + 1][j], 0);
                        else
                            neighbours.put(tabA[i + 1][j], neighbours.get(tabA[i + 1][j]) + 1);
                    }

                    Integer maxKey = null;
                    int maxValue = -1;
                    for (Map.Entry<Integer, Integer> entry : neighbours.entrySet()) {
                        if (entry.getValue() > maxValue) {
                            maxValue = entry.getValue();
                            maxKey = entry.getKey();
                        }
                    }

                    if(maxKey!=null) {
                        tabB[i][j] = maxKey;
                        change = true;
                    }
                    else
                        tabB[i][j]=0;

                    neighbours.clear();
                }



            }
        return change;
    }

    boolean updateBarrierPentagonRandom(int[][] tabA,int[][] tabB){ //A - input  B - output

        boolean change=false;

        int width = tabA.length;
        int height = tabA[0].length;

        int[][] pentagonNeigh;

        Map<Integer,Integer> neighbours = new HashMap<Integer,Integer>();
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {

                //KOLOR
                if(tabA[i][j]!=0)
                    tabB[i][j] = tabA[i][j];
                    //PUSTE
                else {

                    //losuje sasiedztwo
                    pentagonNeigh=PentagonRandNeighbours.getNeighbours();
                    int x,y;
                    for(int a=0;a<5;a++) {
                        x = i + pentagonNeigh[0][a];
                        y = j + pentagonNeigh[1][a];

                        if(x<0)
                            x=width-1;
                        else if(x>width-1)
                            x=0;

                        if(y<0)
                            y=width-1;
                        else if(y>width-1)
                            y=0;


                        if (tabA[x][y] > 0)
                            neighbours.put(tabA[x][y], 0);
                    }

                    Integer maxKey = null;
                    int maxValue = -1;
                    for (Map.Entry<Integer, Integer> entry : neighbours.entrySet()) {
                        if (entry.getValue() > maxValue) {
                            maxValue = entry.getValue();
                            maxKey = entry.getKey();
                        }
                    }

                    if(maxKey!=null) {
                        tabB[i][j] = maxKey;
                        change = true;
                    }
                    else
                        tabB[i][j]=0;

                    neighbours.clear();
                }



            }
        return change;
    }





}


