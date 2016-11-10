package embeddedsystems;


import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ConnieZalo
 */
public class Shark {

    private double max_width = 5000/5;
    private double max_height = 3000/5;
    int mx, my;
    double px,py;
    private static int pixel_ratio = 5;
  
    Shark() {
    }

    Shark(int x, int y) {
        this.mx = x;
        this.my = y;
        this.px = x/pixel_ratio;
        this.py = y/ pixel_ratio;
        
    }

    public void setLocation(double x, double y) {
        this.px = x;
        this.py = y;
    }
    public  double[] getLocation() {
        return new double[] {px,py};
    }

    public int getMx() {
        return mx;
    }

    public void setMx(int mx) {
        this.mx = mx;
    }

    public int getMy() {
        return my;
    }

    public void setMy(int my) {
        this.my = my;
    }

    public double getPx() {
        return px;
    }

    public void setPx(double px) {
        this.px = px;
    }

    public double getPy() {
        return py;
    }

    public void setPy(double py) {
        this.py = py;
    }
 
    private void chooseDirection(int direction){
        switch(direction){
            case 0: 
                //move west
                this.px--;
                break;
            case 1: 
                //move east
                this.px++;
                break;
            case 2: 
                //move south
                this.py++;
                break;
            case 3: 
                //move north
                this.py--;
                break;
        }
          
    }
    public void moveShark(int direction){
      
        chooseDirection(direction);
    /*   if(py<=0){
            py=py;
        } 
        if(py>=max_height/2){
            py=py;
        }
        
        if(px>= max_width)
            px =px;
        if(px<0){
            px=px;
        }
        */
        
    }
}