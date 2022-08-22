package pl.jano.MC;

import java.awt.dnd.DragSourceMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class growthMC {

    public List<Point> NonVisitedPoints = new ArrayList<>();
    public int[][] tab;
    public Random random;
    double kt= 1.4;




    public int len;

    public growthMC(int input[][],double kt){
        this.random=new Random();
        this.len=input.length;
        this.tab=input;
        PreparingList(input);
        this.kt=kt;

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

    public void PreparingList(int[][] tab){
        int len=tab.length;
        for(int i=0;i<len;i++)
            for(int j=0;j<len;j++)
                this.NonVisitedPoints.add(new Point(i,j));
    }

    public void oneIteration(){
        while(NonVisitedPoints.size()!=0)
            step();
        PreparingList(this.tab);
    }

    public void step(){
        int index=random.nextInt(NonVisitedPoints.size());
        Point currentPoint = NonVisitedPoints.get(index);
        Point randomNeigh=RandomMooreNeighbour(currentPoint);
        NonVisitedPoints.remove(index);
        if(randomNeigh==null)
            return;


        if(currentPoint.x==-1 || currentPoint.y==-1)
            return;

        int saveId=tab[currentPoint.x][currentPoint.y];
        int neighborId=tab[randomNeigh.x][randomNeigh.y];

        int currentEnergy=MooreEnergy(currentPoint);

        tab[currentPoint.x][currentPoint.y]=neighborId;

        int EnergyAfterSwitch=MooreEnergy(currentPoint);
        int deltaEnergy=EnergyAfterSwitch-currentEnergy;



        if(deltaEnergy>0) {
            double co =Math.exp(-deltaEnergy/kt);
            if(random.nextDouble()>co) {
                tab[currentPoint.x][currentPoint.y] = saveId;
            }
        }

    }

    public Point correctPoint(Point p){
        int x=p.x;
        int y=p.y;

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


            if (tab[x][y] > 0 && tab[x][y] != tab[p.x][p.y])
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
            if(tab[p.x][p.y] != tab[a.x][a.y] && tab[a.x][a.y]!=-1)
                result++;
        }
        return result;
    }




}
