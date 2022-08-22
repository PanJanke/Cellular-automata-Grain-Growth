package pl.jano.Mostki;

import java.io.File;
import java.io.FileWriter;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
public class GetPixels {
    public static int[][]  run(String path) {
        int tab[][]=new int[250][250];

        File file= new File(path);
        BufferedImage img = null;
        try {
            img = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int pixel = img.getRGB(x,y);
                if(pixel==-1)
                    tab[x][y]=-1;
                else
                    tab[x][y]=0;

            }
        }

        return tab;
    }
}
