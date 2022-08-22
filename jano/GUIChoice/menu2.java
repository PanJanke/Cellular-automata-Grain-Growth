package pl.jano.GUIChoice;

import pl.jano.SRX.Window3.Visualisation3;
import pl.jano.CA.Window1.ColorManager;
import pl.jano.MC.Window2.Visualisation2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menu2 implements ActionListener {
    private JFrame MenuFrame;
    private JButton start;
    private JButton startSRX;

    private JTextField NumberOfIterations, Kt;
    private JTextField XX, YY,num;

    public int[][] result;
    public ColorManager colorManager;

    public menu2(int[][] input, ColorManager cm) {

        result=input;
        colorManager=cm;
        MenuFrame = new JFrame("Menu");
        MenuFrame.setSize(new Dimension(170, 150));
        MenuFrame.setResizable(false);
        MenuFrame.setDefaultCloseOperation(MenuFrame.EXIT_ON_CLOSE);
        MenuFrame.setLayout(new FlowLayout());

        start = new JButton("Start MC");
        start.addActionListener(this);

        startSRX = new JButton("Start SRX");
        startSRX.addActionListener(this);

        XX = new JTextField("1");
        XX.setToolTipText("XX  (1,6)");

        YY = new JTextField("4");
        YY.setToolTipText("YY  (1,6)");

        num = new JTextField("100");
        num.setToolTipText("num of seeds (1,500)");

        NumberOfIterations = new JTextField("100");
        NumberOfIterations.setToolTipText("Number Of Iterations  (0,200) ");

        Kt = new JTextField("1.5");
        Kt.setToolTipText("Kt parameter (0.1 - 6)");


        MenuFrame.getContentPane().add(NumberOfIterations);
        MenuFrame.getContentPane().add(Kt);
        MenuFrame.getContentPane().add(start);


        MenuFrame.getContentPane().add(XX);
        MenuFrame.getContentPane().add(YY);
        MenuFrame.getContentPane().add(num);
        MenuFrame.getContentPane().add(startSRX);



        MenuFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Start MC")) {

            int aValue;
            double bValue;
            String a = NumberOfIterations.getText();
            String b = Kt.getText();
            try {
                aValue = Integer.parseInt(a);
                bValue = Double.parseDouble(b);
            } catch (NumberFormatException x) {
                System.out.println("Input String cannot be parsed!.");
                return;
            }

            if (aValue <= 0 || aValue > 200) {
                System.out.println("Incorrect number of Iterations");
                return;
            }


            if (bValue < 0.1 || bValue > 6) {
                System.out.println("Incorrect kT parameter");
                return;
            }


            double kt = bValue;
            int numOfIteration = aValue;

            Visualisation2.run(result, colorManager,kt,numOfIteration);




        }

        if (e.getActionCommand().equals("Start SRX")){
            System.out.println("jo ni");
            int aValue;
            int bValue;
            int cValue;
            String a = XX.getText();
            String b =YY.getText();
            String c =num.getText();

            try {
                aValue = Integer.parseInt(a);
                bValue = Integer.parseInt(b);
                cValue = Integer.parseInt(c);

            } catch (NumberFormatException x) {
                System.out.println("Input String cannot be parsed!.");
                return;
            }

            if (aValue <= 0 || aValue > 6) {
                System.out.println("Incorrect number for XX");
                return;
            }


            if (bValue <=0 || bValue > 6) {
                System.out.println("Incorrect number for YY");
                return;
            }

            if (cValue <=0 || cValue >500 ) {
                System.out.println("Incorrect number of seeds");
                return;
            }
            System.out.println(aValue);
            System.out.println(bValue);
            System.out.println(cValue);

            Visualisation3.run(result,colorManager,aValue,bValue,cValue);

        }
    }
}
