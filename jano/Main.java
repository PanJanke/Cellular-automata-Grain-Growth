package pl.jano;

import pl.jano.CA.Logic;
import pl.jano.CA.Machine;
import pl.jano.GUIChoice.menu;
import pl.jano.Mostki.GetPixels;

import javax.swing.*;

public class Main {





    public static void main(String[] args) {
/*
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new menu();
            }
        });
    }}


/*
        int tab[][] = Logic.preparingTab(250, 250, 30, false, true);

        Machine machine = new Machine(tab, true);
        machine.run();
*/





        String path = "D:/mooostek/mostek1a.png";
        int tab[][] = GetPixels.run(path);
        tab = Logic.setTabRandomBridge(tab, 20);
        Machine machine = new Machine(tab, false);
        machine.run();
    }}

