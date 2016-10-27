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

    public int numberSensors = 2;
    public int range = 70;

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

        Ellipse2D.Double safe_circle = new Ellipse2D.Double(140, 50, 365, 365);
        g2d.setColor(new Color(0, 230, 118));
        g2d.fill(safe_circle);
        //Safety Area Circle

        Ellipse2D.Double swimming_dot = new Ellipse2D.Double(120, 200, 8, 8);
        g2d.setColor(Color.BLACK);
        g2d.fill(swimming_dot);
        g2d.drawString("Swimming Area", 130, 170);
        //Swimming Area dot & Text

        g2d.setColor(new Color(0, 230, 118));
        g2d.drawRect(300, 50, 400, 364);
        g2d.fillRect(300, 50, 400, 364);
        //Safety Area rectangle

        g2d.setColor(new Color(255, 109, 0));
        //Deployment Area Circle

        g2d.drawRect(700, 50, 900, 364);
        g2d.fillRect(700, 50, 900, 364);
        //Deployment Area Rect

        g2d.setColor(Color.BLACK);
        g2d.drawString("Deployment Area", 1080, 170);
        //Safety Area Text   

        g2d.setColor(new Color(0, 230, 118));
        Ellipse2D.Double safety_circle = new Ellipse2D.Double(500, 50, 365, 365);
        g2d.fill(safety_circle);
        //Safety Area circle 

        g2d.setColor(Color.BLACK);
        g2d.drawString("Safety Area", 530, 170);
        //Safety Area Text

    }
}
