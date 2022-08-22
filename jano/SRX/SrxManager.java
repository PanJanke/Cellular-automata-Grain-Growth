package pl.jano.SRX;

import pl.jano.MC.Point;
import pl.jano.CA.Window1.ColorManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SrxManager {

    public int[][] saveInput;
    public Cell[][] tab;
    public List<Point> NonVisitedPoints = new ArrayList<>();
    public ColorManager colorManager;
    public Random random;
    public int XX=-7;
    public int YY=0;

    public SrxManager(int [][]input, ColorManager cm,int xx,int yy,int num){
        this.random=new Random();
        this.colorManager=cm;
        this.saveInput=input;
        this.tab= new Cell[input.length][input.length];
        PreparingList(input);
        PreparingCellsTab(input);
        calulatingH();
        setRandomSeeds(num);
        this.XX=xx;
        this.YY=yy;
    }

    public void printTab(){
        int len= tab.length;
        for(int i=0;i<len;i++) {
            for (int j = 0; j < len; j++){
                System.out.print(this.tab[i][j].H);
            }
            System.out.println();
        }
    }
    public int getNonRecrystalizedNum(){
        int counter=0;
        int len= tab.length;
        for(int i=0;i<len;i++)
            for(int j=0;j<len;j++)
                if(tab[i][j].recrystalized==false)
                    counter++;
        System.out.println(counter);
        return counter;
    }

    public List<Point> setMooreNeighbours(){

        List<Point> Mo = new ArrayList<>();

        Mo.add(new Point(-1,-1));
        Mo.add(new Point(-1,0));
        Mo.add(new Point(-1,1));

        Mo.add(new Point(0,1));
        Mo.add(new Point(0,-1));

        Mo.add(new Point(1,-1));
        Mo.add(new Point(1,0));
        Mo.add(new Point(1,1));


        return Mo;
    }

    public Point RandomMooreNeighbour(Point p){

        List<Point> NeighbourList = setMooreNeighbours();

        int RandomNeigbour;
        Point a;
        int x;
        int y;
        while(NeighbourList.size()!=0) {

            RandomNeigbour = random.nextInt(NeighbourList.size());
            a = NeighbourList.get(RandomNeigbour);
            NeighbourList.remove(RandomNeigbour);
            x = p.x + a.x;
            y = p.y + a.y;

            Point neigh=correctPoint(new Point(x,y));
            x=neigh.x;
            y=neigh.y;


            if (tab[x][y].id > 0 && tab[x][y].id != tab[p.x][p.y].id)
                return new Point(x,y);
        }

        return null;
    }

    public int MooreEnergy(Point p){
        int result=0;
        int x=p.x;
        int y=p.y;
        Point a;
        List<Point> NeighbourList =setMooreNeighbours();
        for(int i=0;i<NeighbourList.size();i++){

            x=p.x;
            y=p.y;
            a=NeighbourList.get(i);
            x=x+a.x;
            y=y+a.y;
            a=correctPoint(new Point(x,y));
            if(tab[p.x][p.y].id == tab[a.x][a.y].id && tab[a.x][a.y].id!=-1)
                result--;
        }
        return result;
    }

    public void oneIteration(){
        while(NonVisitedPoints.size()!=0)
            step();
        PreparingList(this.saveInput);
    }

    public int[][] getIds(){
        int len= tab.length;
        int[][] output = new int[len][len];
        for(int i=0;i<len;i++)
            for(int j=0;j<len;j++)
                output[i][j]=tab[i][j].id;
            return output;
    }

    public void step(){
        int index=random.nextInt(NonVisitedPoints.size());
        Point currentPoint = NonVisitedPoints.get(index);
        Point randomNeigh=RandomMooreNeighbour(currentPoint);
        NonVisitedPoints.remove(index);

        if(randomNeigh==null || !tab[randomNeigh.x][randomNeigh.y].recrystalized )
            return;
        if(tab[currentPoint.x][currentPoint.y].id<0)
            return;


        int saveId=tab[currentPoint.x][currentPoint.y].id;
        int neighborId=tab[randomNeigh.x][randomNeigh.y].id;

        int currentEnergy= ( -1*MooreEnergy(currentPoint) )+tab[currentPoint.x][currentPoint.y].H;

        tab[currentPoint.x][currentPoint.y].id=neighborId;
        int EnergyAfterSwitch= -1 * MooreEnergy(currentPoint);

        int deltaEnergy=currentEnergy-EnergyAfterSwitch;

        if(deltaEnergy<=0) {
            tab[currentPoint.x][currentPoint.y].id = tab[randomNeigh.x][randomNeigh.y].id;
            tab[currentPoint.x][currentPoint.y].recrystalized=true;
            tab[currentPoint.x][currentPoint.y].H=0;
        }
        else
            tab[currentPoint.x][currentPoint.y].id=saveId;
    }


    public void setRandomSeeds(int amount){
        for(int i=0;i<amount;i++) {
            int index= random.nextInt(NonVisitedPoints.size());
            Point p=NonVisitedPoints.get(index);
            while(tab[p.x][p.y].recrystalized) {
                index = random.nextInt(NonVisitedPoints.size());
              p = NonVisitedPoints.get(index);
            }

            NonVisitedPoints.remove(index);

            tab[p.x][p.y].recrystalized = true;
            tab[p.x][p.y].H = 0;

            int key= random.nextInt(1000)+1;
            while(colorManager.colorsMap.get(key)!=null)
                key= random.nextInt(1000)+1;

            Color generatedColor = colorManager.generateColor();
            while(!colorManager.isColorUnique(generatedColor))
                generatedColor=colorManager.generateColor();
            colorManager.colorsMap.put(key,generatedColor);

            tab[p.x][p.y].id = key;
        }
    }

    public void PreparingCellsTab(int[][] input){
        int len=input.length;
        for(int i=0;i<len;i++)
            for(int j=0;j<len;j++)
                this.tab[i][j]=new Cell(input[i][j],false);
    }

    public Point correctPoint(Point p){
        int x=p.x;
        int y=p.y;
        int len= tab.length;
        if (x < 0)
            x = len - 1;
        if (x > len - 1)
            x = 0;
        if (y < 0)
            y = len - 1;
        if (y > len - 1)
            y = 0;
        return new Point(x,y);
    }

    public void calulatingH(){
        int len=tab.length;
        for(int i=0;i<len;i++)
            for(int j=0;j<len;j++){
                SettingH(new Point(i,j),this.XX,this.YY);
            }

    }
    public void SettingH(Point p,int XX,int YY){
        int len=tab.length;
        int x=p.x;
        int y=p.y;
        Point a;
        List<Point> NeighbourList =setMooreNeighbours();

        for(int i=0;i<NeighbourList.size();i++){
            x=p.x;
            y=p.y;
            a=NeighbourList.get(i);
            x=x+a.x;
            y=y+a.y;
            a=correctPoint(new Point(x,y));

            if(tab[p.x][p.y].id != tab[a.x][a.y].id && tab[a.x][a.y].id!=-1){
                this.tab[p.x][p.y].H=YY;
                return;
            }
        }
        this.tab[p.x][p.y].H=XX;
    }

    public void PreparingList(int[][] tab){
        int len=tab.length;
        for(int i=0;i<len;i++)
            for(int j=0;j<len;j++)
                this.NonVisitedPoints.add(new Point(i,j));
    }




}
