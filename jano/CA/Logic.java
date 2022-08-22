package pl.jano.CA;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Logic {

    public static int[][] preparingTab(int width, int height, int NumSeeds, boolean absorb, boolean random){
        int[][] result = new int[width][height];

        if(absorb)
            result=Logic.setBorders(result);
        if(random)
            result=Logic.setTabRandom(result,NumSeeds);
        else
            result=Logic.setTabHomogenous(result,NumSeeds);

        return result;
    }

    public static int [][] setBorders(int[][] tab){
        int height = tab.length;
        for(int i=0;i<height;i++){//borders
            tab[i][0] = -1;
            tab[i][height-1] = -1;
            tab[0][i] = -1;
            tab[height-1][i] = -1;
        }
        return tab;
    }

    public static int[][] setTabRandom(int[][] tab,int numSeed){

        int width=tab.length;
        Random generator = new Random();
        int x,y;
        for(int i=0;i<numSeed;i++){// set Seeds
            x=generator.nextInt(width-1)+1;
            y=generator.nextInt(width-1)+1;
            if(tab[x][y]==0)
                tab[x][y]=i+1;
            else
                i--;
        }
        return tab;
    }


    public static int[][] setTabHomogenous(int[][] tab,int numSeed){

        int width=tab.length;
        int sqrtNumSeed=(int)Math.sqrt(numSeed);
        System.out.println(sqrtNumSeed);
        int jump= (int) (   (double)width/ (double) sqrtNumSeed );
        System.out.println(jump);
        int counter=1;
        for(int i=1;i<width; i=i+jump)
            for(int j=1;j<width; j=j+jump){
                tab[i][j]=counter;
                counter++;
            }
        return tab;
    }


    public static int[][] setTabRandomBridge(int[][] tab,int numSeed){
        int width=tab.length;
        Random generator = new Random();
        int x,y;
        for(int i=0;i<numSeed;i++){// set Seeds
            x=generator.nextInt(width-1)+1;
            y=generator.nextInt(width-1)+1;
            if(tab[x][y]==0)
                tab[x][y]=i+1;
            else
                i--;
        }
        return tab;
    }

}
