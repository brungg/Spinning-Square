import javax.swing.*;
import java.awt.*;


public class Game extends JPanel {
    double angle = (Math.PI/180) * 0;
    int length = 200;
    int offset = 50;


    public Game() {
        this.setPreferredSize(new Dimension(500, 500));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);


        System.out.println(angle);
        angle += (Math.PI/180) * 5;


        double[][] rotate = {
                {Math.cos(angle), -Math.sin(angle)},
                {Math.sin(angle),  Math.cos(angle)}
        };


        invokeSquare(g, rotate);


        try { Thread.sleep(100); } catch (InterruptedException e) {}
    }


    private void drawSquare(Graphics g, int xa, int ya, int xb, int yb, double[][] rotate) {
        int midx = 0;
        int midy = 0;
        int x1 = (int) Math.round(rotate[0][0] * (xa - midx) + rotate[0][1] * (ya - midy) + midx);
        int y1 = (int) Math.round(rotate[1][0] * (xa - midx) + rotate[1][1] * (ya - midy) + midy);
        int x2 = (int) Math.round(rotate[0][0] * (xb - midx) + rotate[0][1] * (yb - midy) + midx);
        int y2 = (int) Math.round(rotate[1][0] * (xb - midx) + rotate[1][1] * (yb - midy) + midy);


        // System.out.println("[" + x1 + " " + x2 + "]");
        // System.out.println("[" + y1 + " " + y2 + "]");


        g.drawLine(x1+250, y1+250, x2+250, y2+250);


        repaint();
    }


    private void invokeSquare(Graphics g, double[][] rotate) {
        drawSquare(g, -(length/2), -(length/2), (length/2), -(length/2), rotate);
        drawSquare(g, (length/2), -(length/2), (length/2), (length/2), rotate);
        drawSquare(g, (length/2), (length/2), -(length/2), (length/2), rotate);
        drawSquare(g, -(length/2), (length/2), -(length/2), -(length/2), rotate);


        drawSquare(g, -(length/2) + offset, -(length/2) + offset, (length/2) + offset, -(length/2) + offset, rotate);
        drawSquare(g, (length/2) + offset, -(length/2) + offset, (length/2) + offset, (length/2) + offset, rotate);
        drawSquare(g, (length/2) + offset, (length/2) + offset, -(length/2) + offset, (length/2) + offset, rotate);
        drawSquare(g, -(length/2) + offset, (length/2) + offset, -(length/2) + offset, -(length/2) + offset, rotate);
    }
}
