package pl.jano.Mostki;

import pl.jano.CA.Logic;
import pl.jano.CA.Machine;

public class Bridge {

    public static void run(String path,boolean vn){

        int tab[][]=GetPixels.run(path);
        tab= Logic.setTabRandomBridge(tab,20);
        Machine machine = new Machine(tab,vn);
        machine.run();

    }
}
