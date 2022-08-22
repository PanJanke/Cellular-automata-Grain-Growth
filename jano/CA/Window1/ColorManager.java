package pl.jano.CA.Window1;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ColorManager {

    public Map<Integer, Color> colorsMap = new HashMap<Integer,Color>();

    public Color getOrGenerate(int key){

        if(colorsMap.get(key)==null){
            Color generatedColor = generateColor();
            while(!isColorUnique(generatedColor))
                generatedColor=generateColor();
            colorsMap.put(key,generatedColor);
        }


        return colorsMap.get(key);
    }

    public Color generateColor(){
        Random random = new Random();
        Color result = new Color(random.nextInt(256),random.nextInt(256),random.nextInt(256));
        return result;
    }

    public boolean isColorUnique(Color color){
        for (Map.Entry<Integer, Color> entry : colorsMap.entrySet()) {
            if(color.equals(entry.getValue()))
                return false;
        }

        return true;
    }

}
