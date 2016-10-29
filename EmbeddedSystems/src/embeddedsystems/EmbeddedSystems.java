package embeddedsystems;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
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
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new MyPanel());
        f.pack();
        f.setVisible(true);
        f.setResizable(false);

    }

}

class MyPanel extends JPanel {

    public int win_width = 1325;
    public int win_height = 800; //window dimensions

    public int numberSensors = 50;
    public int range = 70; //meters
    public int gap = 700;
    int model_width = 5000;
    int model_height = 3000;
    private final int pixel_ratio = 5; //5 meter per pixel
    int beach_width = 900; //meters
    int gap_circle_radius = 2900;
    int swi_circle_radius = 1100;

    int sensor_range = 100;
    ArrayList<Double[]> sensors;

    //To DO
    //Rename to GAP Area
    //Change the colors
    public MyPanel() {
        sensors = new ArrayList<Double[]>();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(model_width / pixel_ratio, model_height / pixel_ratio);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setFont(new Font("Segoe UI", Font.ITALIC, 20));
        g2d.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        //anti-aliasing

        g2d.setColor(Color.YELLOW);
        g2d.drawRect(0, 0, beach_width / pixel_ratio, model_height / pixel_ratio);
        g2d.fillRect(0, 0, beach_width / pixel_ratio, model_height / pixel_ratio);
        //Beach Rectangle

        g2d.setColor(new Color(0, 230, 118));
        g2d.fillArc((-((gap_circle_radius / pixel_ratio) / 2) + (beach_width / pixel_ratio)), ((model_height / pixel_ratio) - (gap_circle_radius / pixel_ratio)) / 2, (gap_circle_radius / pixel_ratio), (gap_circle_radius / pixel_ratio), -90, 180);
        //Gap Area

        g2d.setColor(new Color(3, 155, 229));
        g2d.fillArc((-((swi_circle_radius / pixel_ratio) / 2) + (beach_width / pixel_ratio)), ((model_height / pixel_ratio) - (swi_circle_radius / pixel_ratio)) / 2, (swi_circle_radius / pixel_ratio), (swi_circle_radius / pixel_ratio), -90, 180);
        //Swimming Area

        g2d.setColor(Color.BLACK);
        g2d.drawString("Beach", 30, (model_height / pixel_ratio) / 2);
        //Deployment Area Text
        g2d.drawString("Swimming Area", 190, (model_height / pixel_ratio) / 2);
        //Swimming Area Text
        g2d.drawString("Gap", 370, (model_height / pixel_ratio) / 2);
        //Gap Text
        g2d.drawString("Deployment Area", 500, (model_height / pixel_ratio) / 2);
        //Deployment Area Text

        g2d.drawString("Swimming Area Radius: " + swi_circle_radius + "m", win_width - 620, 20);
        g2d.drawString("Gap Darius: " + gap_circle_radius + "m", win_width - 620, 40);
        g2d.drawString("Number of Sensors: " + numberSensors, win_width - 620, 60);
        g2d.drawString("Sensor Range: " + sensor_range + "m", win_width - 620, 80);

        Point center = new Point(beach_width / pixel_ratio, (model_height / pixel_ratio) / 2 - 5); //-5 is a fix

        int radius = ((gap_circle_radius / pixel_ratio) / 2);
        for (int i = 0; i < (numberSensors * 2) + 1; i++) {
            double fi = 2 * Math.PI * i / (numberSensors * 2) + 1;
            double x = radius * Math.sin(fi + Math.PI) + center.getX();
            double y = radius * Math.cos(fi + Math.PI) + center.getY();
            if ((x >= beach_width / pixel_ratio - 4)) { //-4 is a fix. Moving to y axis
                g2d.fill(new Ellipse2D.Double(x, y, 8, 8));
                sensors.add(new Double[]{x, y});
            }
        }
        //placing sensors on deployment circle
        for (Double[] sensor : sensors) {
            g2d.setColor(new Color(224, 64, 251, 200));
            g2d.fill(new Ellipse2D.Double(sensor[0]-6, sensor[1]-6, sensor_range/pixel_ratio, sensor_range/pixel_ratio));
        }
        //drawing sensors' range

        g2d.setColor(Color.BLACK);
        g2d.fill(new Ellipse2D.Double((beach_width / pixel_ratio) + (swi_circle_radius / pixel_ratio) / 2 - 5, ((model_height / pixel_ratio) / 2), 8, 8));
        //Swimmers point 1

        g2d.fill(new Ellipse2D.Double((beach_width / pixel_ratio), ((model_height / pixel_ratio) / 2) - (swi_circle_radius / pixel_ratio) / 2 - 5, 8, 8));
        //Swimmers point 2
    }
}
