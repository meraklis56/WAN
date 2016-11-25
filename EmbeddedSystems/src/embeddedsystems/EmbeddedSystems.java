package embeddedsystems;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class EmbeddedSystems {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();

            }
        });
    }

    private static void createAndShowGUI() {
        JFrame f = new JFrame("WSFN Simulation");
        f.setUndecorated(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new MyPanel());
        f.pack();
        f.setVisible(true);
        f.setResizable(false);

        f.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("mouse: " + e.getX() + "," + e.getY());
            }
        });
    }

}

class MyPanel extends JPanel {

    public int numberSensors = 45;
//    public int range = 120; //meters
    public int gap = 700;
    int model_width = 5000;
    int model_height = 3000;
    private final int pixel_ratio = 5; //5 meter per pixel
    int beach_width = 900; //meters -
    int gap_circle_radius = 1500;
    int swi_circle_radius = 600;
    int server_communication_radius = 1500; //zigbee limitations

    public int win_width = model_width / pixel_ratio;
    public int win_height = model_height / pixel_ratio; //window dimensions

    int[] shark_pos1 = new int[]{3000, 100};
    int[] shark_pos2 = new int[]{3000, 1500};
    int[] shark_pos3 = new int[]{2500, 1500};
    double[] shark_pos1d = new double[]{3000, 100};
    
    // double init_pos[] = new double[(double)shark_pos1[0], (double)shark_pos1[1]]();

    int[] swim_pos1 = new int[]{(beach_width + swi_circle_radius), model_height / 2}; //real coordinates
    int[] swim_pos2 = new int[]{beach_width, model_height / 2 - swi_circle_radius}; //real coordinates

    static Image img;
    static Graphics2D g2d;

    Shark shark;

    int serverNumber = 0;

    int sensor_range = 120;
    int sharkSpeed = 70; //70 meters per seconds
    Sensor[] sensors;
    Sensors mysensors;
    boolean detected = false;
    int scenario = 1;
    double distanceSharkSwam = 0; //in meters

    double seconds = 0; //time parameter, milliseconds
    double sensordetection = 0; //when the shark is detected by sensors, milliseconds

    public MyPanel() {
        sensors = new Sensor[numberSensors];
        shark = new Shark(3000, 500);
        System.out.println("---------------Scenario " + scenario + "-------------");
        System.out.println("Shark Initial Position(" + shark.getMx() + "," + shark.getMy() + ")");
        System.out.println("Shark Speed: " + sharkSpeed);
        mysensors = new Sensors(numberSensors, gap_circle_radius, beach_width, model_height, shark);

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(model_width / pixel_ratio, model_height / pixel_ratio);
    }

    private double calculateDistance(double[] pos1, double[] pos2) {
        double dx = pos1[0] - pos2[0];
        double dy = pos1[1] - pos2[1];
        return Math.sqrt(dx * dx + dy * dy);
    } //meters

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d = (Graphics2D) g.create();
        g2d.setFont(new Font("Segoe UI", Font.ITALIC, 20));
        g2d.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        //anti-aliasing

        g2d.setColor(new Color(244, 164, 96));
        g2d.drawRect(60, 0, beach_width / (pixel_ratio), model_height / pixel_ratio);
        g2d.fillRect(60, 0, beach_width / (pixel_ratio), model_height / pixel_ratio);
        //Beach Rectangle

        g2d.setColor(new Color(159, 207, 251));
        g2d.fillArc((-((gap_circle_radius / pixel_ratio)) + (beach_width / pixel_ratio)),
                (model_height / pixel_ratio - (gap_circle_radius / pixel_ratio * 2)) / 2,
                (gap_circle_radius * 2 / pixel_ratio), (gap_circle_radius * 2 / pixel_ratio),
                -90, 180);
        //Gap Area

        g2d.setColor(new Color(190, 224, 255));
        g2d.fillArc((-((swi_circle_radius / pixel_ratio)) + (beach_width / pixel_ratio)),
                ((model_height / pixel_ratio) - (swi_circle_radius / pixel_ratio * 2)) / 2,
                (swi_circle_radius * 2 / pixel_ratio), (swi_circle_radius * 2 / pixel_ratio),
                -90, 180);
        //Swimming Area

        g2d.setColor(Color.BLACK);
        g2d.drawString("Beach", 70, (model_height / pixel_ratio) / 2);
        //Deployment Area Text
        g2d.drawString("Swimming", 190, (model_height / pixel_ratio) / 2);
        //Swimming Area Text
        g2d.drawString("Gap", 370, (model_height / pixel_ratio) / 2);
        //Gap Text
        g2d.drawString("Deployment", 540, (model_height / pixel_ratio) / 2);
        //Deployment Area Text

        g2d.drawString("Swimming Area Radius: " + swi_circle_radius + "m", win_width - 260, 20);
        g2d.drawString("Gap Radius: " + gap_circle_radius + "m", win_width - 260, 40);
        g2d.drawString("Number of Sensors: " + numberSensors, win_width - 260, 60);
        g2d.drawString("Sensor Range: " + sensor_range + "m", win_width - 260, 80);

        for (int i = 0; i < mysensors.getSensorList().size(); i++) {
            g2d.setColor(Color.BLACK);
            double x = mysensors.getSensorList().get(i).getPx();
            double y = mysensors.getSensorList().get(i).getPy();
            g2d.fill(new Ellipse2D.Double(x, y, 8, 8)); //painting the sensor dots
//            g2d.drawString("Sensor: " + mysensors.getSensorList().get(i).getSensorID(), ((int) x + 25), ((int) y));
            g2d.setColor(new Color(255, 128, 128, 210));
            g2d.fill(new Ellipse2D.Double(x - ((sensor_range / pixel_ratio) / 2.5), y - ((sensor_range / pixel_ratio) / 2.5), sensor_range / pixel_ratio, sensor_range / pixel_ratio)); //painting the range of each sensor
        }

        g2d.setColor(Color.BLACK);
//        g2d.fill(new Ellipse2D.Double(swim_pos1[0] / pixel_ratio - 4, swim_pos1[0] / pixel_ratio, 8, 8));
//        //Swimmers point 1
//
//        g2d.fill(new Ellipse2D.Double(swim_pos2[0] / pixel_ratio - 4, swim_pos2[1] / pixel_ratio, 8, 8));
//        //Swimmers point 2

        int[] server = new int[]{beach_width - 50, model_height / 2};

        double distancex = mysensors.getSensorList().get(numberSensors - 1).getMx() - server[0];
        double distancey = mysensors.getSensorList().get(numberSensors - 1).getMx() - server[1];
        double distance = Math.hypot(distancex, distancey);
        if (distance < server_communication_radius) {
            g2d.fill(new Ellipse2D.Double(server[0] / pixel_ratio, server[1] / pixel_ratio, 8, 8));
            g2d.drawString("Server", server[0] / pixel_ratio, server[1] / pixel_ratio - 10);
        } else {
            serverNumber = (gap_circle_radius * 2) / server_communication_radius;
            int distance_server = (gap_circle_radius * 2) / (serverNumber + 1);
            for (int i = 1; i < serverNumber + 1; i++) {
                g2d.fill(new Ellipse2D.Double(server[0] / pixel_ratio,
                        ((model_height - 2 * gap_circle_radius) / 2) / pixel_ratio + (i * gap_circle_radius * 2 / 3) / pixel_ratio,
                        8, 8));
                g2d.drawString("Server" + i, server[0] / pixel_ratio, (((model_height - 2 * gap_circle_radius) / 2) / pixel_ratio + (i * gap_circle_radius * 2 / 3) / pixel_ratio) - 10);
            }
        }

        try {
            img = ImageIO.read(new File("shark.png"));
            g2d.drawImage(img, (int) shark.getPx(), (int) shark.getPy(), null);
        } catch (IOException ex) {
            Logger.getLogger(MyPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Scenario 1
        if (scenario == 1) {
            int tempx = (int) (Math.random() * 150);
            int tempy = (int) (Math.random() * 150);
            double[] a = new double[]{shark.getMx(), shark.getMy()};
            System.out.println("Shark Position1: " + (1000 + tempx) + "," + (1500 + tempy));
            int sensordetector = -1;
            for (int i = 0; i < 200; i++) {
                shark.moveShark(new int[]{1000 + tempx, 1500 + tempy});// west x--
                g2d.drawImage(img, (int) shark.getPx(), (int) shark.getPy(), null);
                if (!detected) {
                    for (int j = 0; j < mysensors.getSensorList().size(); j++) {
                        if (mysensors.getSensorList().get(j).isSharkClose(shark)>=0){
                            sensordetector = j;
                            detected = true;
                            break;
                        }
                    }
                }
            }
            shark.setLocation(1000 + tempx, 1500 + tempy);
            double[] temp = new double[]{1000 + tempx, 1500 + tempy};
            distanceSharkSwam += calculateDistance(shark_pos1d, temp);
            double[] temp2 = new double[]{mysensors.getSensorList().get(sensordetector).getMx(),  mysensors.getSensorList().get(sensordetector).getMy()};
            double distancesensordetected = calculateDistance(shark_pos1d, temp2);
            System.out.println("Step1, distance covered: " + distanceSharkSwam);
            seconds = calculateDistance(shark_pos1d, temp) / shark.shark_speed;
            System.out.println("Step1, seconds of travel: " + seconds);
            System.out.println("Step1, distance from shark to sensor: " + distancesensordetected);
            sensordetection = distancesensordetected / shark.shark_speed;
            System.out.println("Step1, seconds to be detected: " + sensordetection);

            double[] b = new double[]{1000 + tempx, 1500 + tempy};
            distanceSharkSwam += calculateDistance(a, b);
            a = new double[]{shark.getMx(), shark.getMy()};
            for (int i = 0; i < 200; i++) {
                System.out.println("in?");
                shark.moveShark(new int[]{3000 + tempx, 2500 + tempy});// west x--
                g2d.drawImage(img, (int) shark.getPx(), (int) shark.getPy(), null);
                if (detected) {
                    for (int j = 0; j < mysensors.getSensorList().size(); j++) {
//                        detected = mysensors.getSensorList().get(j).isSharkClose(shark);
                    }
                }
            }
            
            b = new double[]{3000 + tempx, 2500 + tempy};
            distanceSharkSwam += calculateDistance(a, b);
        } else if (scenario == 2) {
            int tempx = (int) (Math.random() * 150);
            int tempy = (int) (Math.random() * 150);
            double[] a = new double[]{shark.getMx(), shark.getMy()};
            for (int i = 0; i < 200; i++) {
                shark.moveShark(new int[]{2000 + tempx, 1000 + tempy});// west x--
                g2d.drawImage(img, (int) shark.getPx(), (int) shark.getPy(), null);
                if (!detected) {
                    for (int j = 0; j < mysensors.getSensorList().size(); j++) {
//                        detected = mysensors.getSensorList().get(j).isSharkClose(shark);
                    }
                }
            }
            double[] b = new double[]{2000 + tempx, 1000 + tempy};
            distanceSharkSwam += calculateDistance(a, b);
            a = new double[]{shark.getMx(), shark.getMy()};
            tempx = (int) (Math.random() * 150);
            tempy = (int) (Math.random() * 150);
            for (int i = 0; i < 200; i++) {
                shark.moveShark(new int[]{2500 + tempx, 1100 + tempy});// west x--
                g2d.drawImage(img, (int) shark.getPx(), (int) shark.getPy(), null);
                if (detected) {
                    for (int j = 0; j < mysensors.getSensorList().size(); j++) {
//                        detected = mysensors.getSensorList().get(j).isSharkClose(shark);
                    }
                }
            }
            b = new double[]{2000 + tempx, 1000 + tempy};
            distanceSharkSwam += calculateDistance(a, b);
            a = new double[]{shark.getMx(), shark.getMy()};
            tempx = (int) (Math.random() * 150);
            tempy = (int) (Math.random() * 150);
            for (int i = 0; i < 200; i++) {
                shark.moveShark(new int[]{2000 + tempx, 1800 + tempy});// west x--
                g2d.drawImage(img, (int) shark.getPx(), (int) shark.getPy(), null);
                if (detected) {
                    for (int j = 0; j < mysensors.getSensorList().size(); j++) {
//                        detected = mysensors.getSensorList().get(j).isSharkClose(shark);
                    }
                }
            }
            b = new double[]{2000 + tempx, 1000 + tempy};
            distanceSharkSwam += calculateDistance(a, b);
            a = new double[]{shark.getMx(), shark.getMy()};
            tempx = (int) (Math.random() * 150);
            tempy = (int) (Math.random() * 150);
            for (int i = 0; i < 200; i++) {
                shark.moveShark(new int[]{3200 + tempx, 2500 + tempy});// west x--
                g2d.drawImage(img, (int) shark.getPx(), (int) shark.getPy(), null);
                if (detected) {
                    for (int j = 0; j < mysensors.getSensorList().size(); j++) {
//                        detected = mysensors.getSensorList().get(j).isSharkClose(shark);
                    }
                }
            }
            b = new double[]{2000 + tempx, 1000 + tempy};
            distanceSharkSwam += calculateDistance(a, b);
            //calculate time
            //profit
        } else if (scenario == 3) {

        }

    }

    /*
     class Sensors {

     Sensor[] sensorList;
     Shark shark;
     int range, pixel_ratio;
     int[] position;
     static double time;
     int sharkSpeed = 70;
     double sharkTraveltime;
     boolean detected;
     Sensor temp;

     public Sensors(Sensor[] sensorList, Shark shark, int range, int pixel_ratio, int[] position) {
     this.sensorList = sensorList;
     this.shark = shark;
     this.range = range;
     this.pixel_ratio = pixel_ratio;
     this.position = position;
     }

   
     public void moveShark() throws InterruptedException {
     double distancex = (position[0] - shark.getX());
     double distancey = (position[1] - shark.getY());
     double distance = Math.hypot(distancex, distancey); //in meters
     sharkTraveltime += distance/sharkSpeed;
     int x, y, k;
     double dx, dy, p;
     int x1 = position[0], y1 = position[1] , x2 = shark.getX() , y2 = shark.getY();
     dx = Math.abs(x2 - x1);
     dy = Math.abs(y2 - y1);
     x = x1;
     y = y1;
     p = 2 * dy - dx;
     for (k = 0; k < dx; k++) {
     if (p < 0) {
     MyPanel.g2d.drawImage(MyPanel.img, x/pixel_ratio, y/pixel_ratio, null);
     //                MyPanel.g2d.fillOval(x++, y, 5, 5);
     x++;
     p = p + (2 * dy);
     shark.setLocation(x/pixel_ratio, y/pixel_ratio);
     //                System.out.println("Shark[" + x +", " + y +"]");
     } else {
     MyPanel.g2d.drawImage(MyPanel.img, x/pixel_ratio, y/pixel_ratio, null);
     x++;
     y++;
     //                MyPanel.g2d.fillOval(x++, y++, 5, 5);
     p = p + (2 * (dy - dx));
     shark.setLocation(x/pixel_ratio, y/pixel_ratio);
     //                System.out.println("Shark[" + x +", " + y +"]");
     }
     if (isNear() && !detected){
     detected = true;
     System.out.println("Sensor: " + temp.getX() + "," + temp.getY());
     MyPanel.g2d.fill(new Ellipse2D.Double(temp.getX(), temp.getY(), 5,5));
     //find time to take shark to go to the position
     }
     System.out.println("Shark loc" + k + ": " + shark.getX() + "," + shark.getY());
     }
     }

     */
}
