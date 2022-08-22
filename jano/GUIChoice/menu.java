package pl.jano.GUIChoice;

import pl.jano.CA.Logic;
import pl.jano.CA.Machine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class menu implements ActionListener {

    private JFrame MenuFrame;
    private JButton start;
    private JButton Bridge;
    private JButton BridgeCorr;

    private boolean Absorb=false;
    private boolean VonNeumann=false;
    private boolean Random=false;


    private String[] BoundaryConditions = {"Periodic","Absorb"};
    private String[] Neighbours = {"Von Neumann","Pentagon Random"};
    private String[] Nucleation = {"Random","Homogenous"};

    private JComboBox comboBox1,comboBox2,comboBox3;
    private JTextField NumberOfSeeds,Width;



    public menu(){
        MenuFrame = new JFrame("Menu");
        MenuFrame.setSize(new Dimension(500,150));
        MenuFrame.setResizable(false);
        MenuFrame.setDefaultCloseOperation(MenuFrame.EXIT_ON_CLOSE);
        MenuFrame.setLayout(new FlowLayout());

        start = new JButton("Start");
        start.addActionListener(this);

        Bridge = new JButton("CA Bridge");
        Bridge.addActionListener(this);

        BridgeCorr = new JButton("CA Bridge Corrupted");
        BridgeCorr.addActionListener(this);
        NumberOfSeeds= new JTextField("20");
        Width = new JTextField("200");
        Width.setToolTipText("Size of Area (1 , 250)");


        comboBox1 = new JComboBox(BoundaryConditions);
        comboBox2 = new JComboBox(Neighbours);
        comboBox3 = new JComboBox(Nucleation);





        MenuFrame.getContentPane().add(comboBox1);
        MenuFrame.getContentPane().add(comboBox2);
        MenuFrame.getContentPane().add(comboBox3);
        MenuFrame.getContentPane().add(NumberOfSeeds);
        MenuFrame.getContentPane().add(Width);
        MenuFrame.getContentPane().add(start);
        MenuFrame.getContentPane().add(Bridge);
        MenuFrame.getContentPane().add(BridgeCorr);
        MenuFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Start")) {

            int aValue;
            int bValue;
            String a = NumberOfSeeds.getText();
            String b = Width.getText();
            try {
                 aValue = Integer.parseInt(a);
                 bValue = Integer.parseInt(b);
            }
            catch (NumberFormatException x) {
                System.out.println("Input String cannot be parsed to Integer.");
                return;
            }

            if(bValue<=0 || bValue > 251){
                System.out.println("Incorrect number of seeds");
                return;
            }


            if(aValue<=0 || aValue > bValue*bValue-20){
                System.out.println("Incorrect size");
                return;
            }



            Absorb=comboBox1.getItemAt(comboBox1.getSelectedIndex()).equals("Absorb");
            VonNeumann=comboBox2.getItemAt(comboBox2.getSelectedIndex()).equals("Von Neumann");
            Random=comboBox3.getItemAt(comboBox3.getSelectedIndex()).equals("Random");



            int width=bValue;
            int height=bValue;
            int numSeeds=aValue;


            int tab[][]= Logic.preparingTab(width,height,numSeeds,Absorb,Random);

            Machine machine = new Machine(tab,VonNeumann);
            machine.run();


        }

        if(e.getActionCommand().equals("CA Bridge")){
            VonNeumann=comboBox2.getItemAt(comboBox2.getSelectedIndex()).equals("Von Neumann");
            pl.jano.Mostki.Bridge.run("D:/mooostek/mostek1a.png",VonNeumann);
        }

        if(e.getActionCommand().equals("CA Bridge Corrupted")){
            VonNeumann=comboBox2.getItemAt(comboBox2.getSelectedIndex()).equals("Von Neumann");
            pl.jano.Mostki.Bridge.run("D:/mooostek/mostek2a.png",VonNeumann);

        }



    }

}
