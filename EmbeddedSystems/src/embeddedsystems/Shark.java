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
 
}