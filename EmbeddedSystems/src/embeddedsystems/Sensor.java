package embeddedsystems;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ConnieZalo
 */
public class Sensor {

    private double max_width = 5000 / 5;
    private double max_height = 3000 / 5;
    int mx, my;
    private static int pixel_ratio = 5;
    private double prange;
    private int sensorID;

    public int getSensorID() {
        return sensorID;
    }

    public void setSensorID(int sensorID) {
        this.sensorID = sensorID;
    }
    public int mrange = 120;

    double px, py;

    public Sensor(int x, int y) {
        this.mx = x;
        this.my = y;

        this.prange = (mrange / pixel_ratio);
        this.px = x / pixel_ratio;
        this.py = y / pixel_ratio;
    }

    public Sensor(double x, double y, int id) {
        this.px = x;
        this.py = y;
        this.mx = (int) x * pixel_ratio;
        this.my = (int) y * pixel_ratio;

        this.prange = (mrange / pixel_ratio);
        this.sensorID = id;
    }

    public void setMx(int mx) {
        this.mx = mx;
    }

    public void setMy(int my) {
        this.my = my;
    }

    public static void setPixel_ratio(int pixel_ratio) {
        Sensor.pixel_ratio = pixel_ratio;
    }

    public void setPx(double px) {
        this.px = px;
    }

    public void setPy(double py) {
        this.py = py;
    }

    public int getMx() {
        return mx;
    }

    public int getMy() {
        return my;
    }

    public static int getPixel_ratio() {
        return pixel_ratio;
    }

    public double getPx() {
        return px;
    }

    public double getPy() {
        return py;
    }

    public int isSharkClose(Shark s) {
        double distance = calculateDistance(s);
        // System.out.println(" I am Sensor " + prange + "detect shark at distance "+ distance );

        if (prange >= distance) {
            System.out.println(" I am Sensor " + this.sensorID + "detect shark at distance " + distance);
            return sensorID;
        } else {

            return -1;
        }
    }

    private double calculateDistance(Shark s) {
        double dx = px - s.getPx();
        double dy = py - s.getPy();
        return Math.sqrt(dx * dx + dy * dy);
    }
}


/*
 public boolean isNear() {
 for (int i = 0; i < sensorList.length; i++) {
 double distance = calcDistance(sensorList[i]);
 //            System.out.println("ALERT. Sensor[" + i + "]: " + sensorList[i].getX() + "," + sensorList[i].getY() + "Distance: " + distance);
 if (distance < range/pixel_ratio) {
 if (temp==null){
 temp = sensorList[i];
 System.out.println("Shark: " + shark.getX() + "," + shark.getY());
 //                MyPanel.g2d.fill(new Ellipse2D.Double(shark.getX(), shark.getX(), 15,15));
 //                MyPanel.g2d.fill(new Ellipse2D.Double(temp.getX(), temp.getY(), 15,15));
 System.out.println("ALERT. Sensor[" + i + "]: " + sensorList[i].getX() + "," + sensorList[i].getY() + "Distance: " + distance);
 //notify Server
 }
 return true;
 }
 }
 return false;
 }

 */
