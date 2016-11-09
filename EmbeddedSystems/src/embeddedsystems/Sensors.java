package embeddedsystems;


import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
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
public class Sensors {
     ArrayList<Sensor> sensorList;
     int numSensors; // number of sensors
     int beach_width,modelheight; 
     double pbeach_width,pmodelheight;
     Point center;
     private static int pixel_ratio = 5;
     int mradius;
     double pradius;
     Shark s; 

    public Sensors(int numSensors, int radius, int beach_width, int modelheight) {
        this.numSensors = numSensors;
        this.pbeach_width = beach_width/pixel_ratio;
        this.beach_width = beach_width;
        this.modelheight = modelheight;
        this.pmodelheight = modelheight/pixel_ratio;
        this.mradius = radius;
        this.pradius = mradius/pixel_ratio;  
        center= new Point((int)pbeach_width, (int)(pmodelheight / 2) - 5); //-5 is a fix
      
        this.sensorList=  new ArrayList<Sensor>();
        generateSensors();
    }

    public ArrayList<Sensor> getSensorList() {
        return sensorList;
    }
    
    private void generateSensors(){
         for (int i = 0; i < (numSensors * 2) + 1; i++) {
 
            double fi = 2 * Math.PI * i / (numSensors * 2) + 1;
            double x = pradius * Math.sin(fi + Math.PI) + center.getX();
            double y = pradius * Math.cos(fi + Math.PI) + center.getY();
           if ((x >= pbeach_width)) {
                Sensor e = new Sensor(x,y);
                sensorList.add(e);
           }
        }
    }
}
