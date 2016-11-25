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

    private double max_width = 5000 / 5;
    private double max_height = 3000 / 5;
    int mx, my;
    double px, py;
    private static int pixel_ratio = 5;
    public double shark_speed = 19.4444; //meters per second

    Shark() {
    }

    Shark(int x, int y) {
        this.mx = x;
        this.my = y;
        this.px = x / pixel_ratio;
        this.py = y / pixel_ratio;

    }

    public void setLocation(int x, int y) { // in meters
        this.px = x / pixel_ratio;
        this.py = y / pixel_ratio;
        this.mx = x;
        this.my = y;
    }

    public double[] getLocation() { //in meters
        return new double[]{mx, my};
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

    private void chooseDirection(int direction) {
        switch (direction) {
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

    public void moveShark(int[] position) {

//        double angle = Math.atan2(10/pixel_ratio-getPx(), 10/pixel_ratio-getPy()); //Math.atan2(25000/pixel_ratio-getPx(), 8500/pixel_ratio-getPy());
//        double xVel = 1000/pixel_ratio * Math.cos(angle);
//        double yVel = 1000/pixel_ratio * Math.sin(angle);
//   
//            setPx(getPx() + xVel);
//            setPy(getPy() + yVel);
        double xVel = position[0] / pixel_ratio - getPx();
        double yVel = position[1] / pixel_ratio - getPy();
        double mag = Math.sqrt(xVel * xVel + yVel * yVel);
        xVel = xVel * (70 / pixel_ratio) / mag;
        yVel = yVel * (70 / pixel_ratio) / mag;
        setPx(getPx() + xVel);
        setPy(getPy() + yVel);

//        chooseDirection(direction);
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
