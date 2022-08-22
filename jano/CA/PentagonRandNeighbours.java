package pl.jano.CA;

import java.util.Random;

public class PentagonRandNeighbours {


    static int [][] neighbours1={
        {0, 0, -1, -1, -1},
        {1, -1, 1, 0, -1 }
    };

    static int [][] neighbours2={
            {0, 0, 1, 1, 1},
            {1, -1, 1, 0, -1}
    };

    static int [][] neighbours3={
            {1, 1, 0, 1, 1},
            {0, -1, -1, 0, -1}
    };
    static int [][] neighbours4={
            {1, 1, 0, 1, 1},
            {0, 1, 1, 0, 1}
    };

    public static int[][] getNeighbours(){
        Random random= new Random();
        int number = random.nextInt(4)+1;
        if(number==1)
            return neighbours1;

        if(number==2)
            return neighbours2;

        if(number==3)
            return neighbours3;

        if(number==4)
            return neighbours4;


        return null;
    }

}
