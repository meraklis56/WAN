package embeddedsystems;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
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

    public int numberSensors = 3;
    public int range = 70; //meters
    public int gap = 1000;

    public MyPanel() {
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1325, 500);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setFont(new Font("Segoe UI", Font.ITALIC, 20));
        g2d.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));
        //anti-aliasing

        Ellipse2D.Double server_dot = new Ellipse2D.Double(50, 200, 8, 8);
        g2d.fill(server_dot);
        g2d.drawString("Server", 20, 170);
        //Server dot & text

        Ellipse2D.Double swimming_circle = new Ellipse2D.Double(90, 50, 365, 365);
        g2d.setColor(new Color(3, 155, 229));
        g2d.fill(swimming_circle);
        //Swimming Area Circle

        g2d.drawRect(260, 50, 150, 364);
        g2d.fillRect(260, 50, 150, 364);
        //Swimming Area Rectangle

        Ellipse2D.Double gap_circle = new Ellipse2D.Double(190, 50, 365, 365);
        g2d.setColor(new Color(0, 230, 118));
        g2d.fill(gap_circle);
        //Gap left circle

        Ellipse2D.Double swimming_dot = new Ellipse2D.Double(120, 200, 8, 8);
        g2d.setColor(Color.BLACK);
        g2d.fill(swimming_dot);
        g2d.drawString("Swimming Area", 130, 170);
        //Swimming Area dot & Text

        g2d.setColor(new Color(0, 230, 118));
        g2d.drawRect(372, 50, (gap - 372), 364);
        g2d.fillRect(372, 50, (gap - 372), 364);
        //Gap rectangle

        g2d.setColor(new Color(255, 109, 0));
        //Deployment Area Circle

        g2d.drawRect(gap, 50, 1200, 364);
        g2d.fillRect(gap, 50, 1200, 364);
        //Deployment Area Rect

        g2d.setColor(new Color(0, 230, 118));
        Ellipse2D.Double safety_circle = new Ellipse2D.Double((gap - 190), 50, 365, 365); //depending the gap size
        g2d.fill(safety_circle);
        //Gap right circle 

        g2d.setColor(Color.BLACK);
        g2d.drawString("Gap", 530, 170);
        //Gap Text

        g2d.setColor(Color.RED);
        Ellipse2D.Double swimmers = new Ellipse2D.Double(372, 50, 8, 8);
        g2d.drawString("Swimmers", 372, 35);
        g2d.fill(swimmers);
        //swimmers position 

        Ellipse2D.Double swimmers2 = new Ellipse2D.Double(190, 200, 8, 8);
        g2d.fill(swimmers2);
        g2d.drawString("Swimmers2", 190, 235);
        //swimmers position 2

        g2d.setColor(Color.BLACK);
        g2d.drawString("Deployment Area", (gap + 150), 170);
        //Deployment Text 
    }
}
