package embeddedsystems;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
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

    public int numberSensors = 7;
    public int range = 70; //meters
    public int gap = 700;
    int model_width = 5000;
    int model_height = 3000;
    private final int pixel_ratio = 5; //5 meter per pixel
    int beach_width = 500; //meters
    int dep_circle_radius = 2900;
    int swi_circle_radius = 1100;

    public MyPanel() {
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

        g2d.setColor(new Color(255, 109, 0));
        g2d.fillArc((-((dep_circle_radius / pixel_ratio) / 2) + (beach_width / pixel_ratio)), ((model_height / pixel_ratio) - (dep_circle_radius / pixel_ratio)) / 2, (dep_circle_radius / pixel_ratio), (dep_circle_radius / pixel_ratio), -90, 180);
        //Deployment Area

        g2d.setColor(new Color(3, 155, 229));
        g2d.fillArc((-((swi_circle_radius / pixel_ratio) / 2) + (beach_width / pixel_ratio)), ((model_height / pixel_ratio) - (swi_circle_radius / pixel_ratio)) / 2, (swi_circle_radius / pixel_ratio), (swi_circle_radius / pixel_ratio), -90, 180);
        //Swimming Area

        g2d.setColor(Color.BLACK);
        g2d.drawString("Swimming Area", 110, (model_height / pixel_ratio) / 2);
        //Swimming Area Text
        g2d.drawString("Deployment Area", 280, (model_height / pixel_ratio) / 2);
         //Deployment Area Text

        g2d.drawString("Swimming Area: " + swi_circle_radius + "m", win_width - 540, 20);
        g2d.drawString("Deployment Area: " + dep_circle_radius + "m", win_width - 540, 40);
        g2d.drawString("Number of Sensors: " + numberSensors, win_width - 540, 60);

//        deployment circle [beach_width / pixel_ratio,(model_height / pixel_ratio)/2]
//        Ellipse2D.Double swimming_dot = new Ellipse2D.Double(beach_width / pixel_ratio, (model_height / pixel_ratio) / 2, 8, 8);
//        g2d.draw(swimming_dot);

        Point center = new Point(beach_width / pixel_ratio, (model_height / pixel_ratio) / 2 - 5); //-5 is a fix

        int radius = ((dep_circle_radius / pixel_ratio)/2);
        System.out.println(beach_width / pixel_ratio);
        for (int i = 0; i < (numberSensors*2)+1; i++) {
            double fi = 2 * Math.PI * i / (numberSensors*2)+1;
            double x = radius * Math.sin(fi + Math.PI) + center.getX();
            double y = radius * Math.cos(fi + Math.PI) + center.getY();
            if ((x >= beach_width / pixel_ratio - 4)) //-4 is a fix. Moving to y axis
                g2d.draw(new Ellipse2D.Double(x, y, 8, 8));
        }
        //placing sensors on deployment circle
        
        g2d.draw(new Ellipse2D.Double((beach_width / pixel_ratio) + (swi_circle_radius / pixel_ratio) / 2  - 5, ((model_height / pixel_ratio) / 2), 8 , 8));
        //Swimmers point 1
        
        g2d.draw(new Ellipse2D.Double((beach_width / pixel_ratio), ((model_height / pixel_ratio) / 2) - (swi_circle_radius / pixel_ratio) / 2  - 5, 8 , 8));
        //Swimmers point 2
    }
}
