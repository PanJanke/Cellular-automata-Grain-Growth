package pl.jano.Other;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SaveStructure {


    public static void saveToTxt(int choice, int numOfIterations, int board[][]){

        try {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    builder.append(board[i][j] + "");
                    if (j < board.length - 1)
                        builder.append(",");
                }
                builder.append("\n");
            }
            BufferedWriter writer;
            if(choice==0)
                 writer = new BufferedWriter(new FileWriter("CA Structure " + Integer.toString(numOfIterations) + "iterations .txt"));
            else if(choice==1)
                writer = new BufferedWriter(new FileWriter("CM Structure " + Integer.toString(numOfIterations) +"iterations .txt"));
            else
                writer = new BufferedWriter(new FileWriter("SRX Structure " + Integer.toString(numOfIterations) +"iterations .txt"));
            writer.write(builder.toString());
            writer.close();
        }
        catch(IOException e ){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        System.out.println("FILE CREATED");
    }

}
