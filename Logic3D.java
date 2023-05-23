// https://en.wikipedia.org/wiki/3D_projection

import javax.swing.*;
import java.awt.*;

public class Spin extends JPanel {
    double angleX = (Math.PI/180) * 0;
	double angleY = (Math.PI/180) * 0;
	double angleZ = (Math.PI/180) * 0;
    int length = 200;
    int offset = 100;

    public Spin() {
        this.setPreferredSize(new Dimension(500, 500));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        System.out.println(angleX + ", " + angleY + ", " + angleZ);
        angleX += (Math.PI/180) * 1;
		angleY += (Math.PI/180) * 1;
		angleZ += (Math.PI/180) * 1;

        double[][] rotateX = {
				{1, 0, 0},
                {0, Math.cos(angleX), Math.sin(angleX)},
                {0, -Math.sin(angleX),  Math.cos(angleX)}
        };
		double[][] rotateY = {
			{Math.cos(angleY), 0, -Math.sin(angleY)},
			{0, 1, 0},
			{Math.sin(angleY), 0,  Math.cos(angleY)}
		};
		double[][] rotateZ = {
			{Math.cos(angleZ), Math.sin(angleZ), 0},
			{-Math.sin(angleZ),  Math.cos(angleZ), 0},
			{0, 0, 1}
		};

        invokeSquare(g, rotateX, rotateY, rotateZ);

        try { Thread.sleep(10); } catch (InterruptedException e) {}
    }

    private int[] drawSquare(Graphics g, int midx, int midy, int xa, int ya, int xb, int yb, double[][] rotateX, double[][] rotateY, double[][] rotateZ) {
        // int x1 = (int) Math.round(rotate[0][0] * (xa - midx) + rotate[0][1] * (ya - midy) + midx);
        // int y1 = (int) Math.round(rotate[1][0] * (xa - midx) + rotate[1][1] * (ya - midy) + midy);
        // int x2 = (int) Math.round(rotate[0][0] * (xb - midx) + rotate[0][1] * (yb - midy) + midx);
        // int y2 = (int) Math.round(rotate[1][0] * (xb - midx) + rotate[1][1] * (yb - midy) + midy);


		g.setColor(Color.WHITE);
		g.drawLine(x1+250, y1+250, x2+250, y2+250);

        repaint();
		int[] test = {x2, y2};

		return test;
    }

	private void drawLines(Graphics g, int[][] pos) {
		g.setColor(Color.WHITE);
		g.drawLine(pos[0][0]+250, pos[0][1]+250, pos[1][0]+250, pos[1][1]+250);
		g.drawLine(pos[2][0]+250, pos[2][1]+250, pos[3][0]+250, pos[3][1]+250);
		g.drawLine(pos[4][0]+250, pos[4][1]+250, pos[5][0]+250, pos[5][1]+250);
		g.drawLine(pos[6][0]+250, pos[6][1]+250, pos[7][0]+250, pos[7][1]+250);

	}

    private void invokeSquare(Graphics g, double[][] rotateX, double[][] rotateY, double[][] rotateZ) {
		int difference = 30;
        int[] a1 = drawSquare(g, 0, 0, -(length/2), -(length/2), (length/2), -(length/2), rotateX, rotateY, rotateZ);
        int[] b1 = drawSquare(g, 0, 0, (length/2), -(length/2), (length/2), (length/2), rotateX, rotateY, rotateZ);
        int[] c1 = drawSquare(g, 0, 0, (length/2), (length/2), -(length/2), (length/2), rotateX, rotateY, rotateZ);
        int[] d1 = drawSquare(g, 0, 0, -(length/2), (length/2), -(length/2), -(length/2), rotateX, rotateY, rotateZ);

        int[] a2 = drawSquare(g, offset,  offset, -((length/2) - difference) + offset, -((length/2) - difference) + offset, 
			((length/2) - difference) + offset, -((length/2) - difference) + offset, rotateX, rotateY, rotateZ);
        int[] b2 = drawSquare(g, offset,  offset, ((length/2) - difference) + offset, -((length/2) - difference) + offset, 
			((length/2) - difference) + offset, ((length/2) - difference) + offset, rotateX, rotateY, rotateZ);
        int[] c2 = drawSquare(g, offset,  offset, ((length/2) - difference) + offset, ((length/2) - difference) + offset, 
			-((length/2) - difference) + offset, ((length/2) - difference) + offset, rotateX, rotateY, rotateZ);
        int[] d2 = drawSquare(g, offset,  offset, -((length/2) - difference) + offset, ((length/2) - difference) + offset, 
			-((length/2) - difference) + offset, -((length/2) - difference) + offset, rotateX, rotateY, rotateZ);

		int[][] pointPos = {a1, a2, b1, b2, c1, c2, d1, d2};

		drawLines(g, pointPos);
    }
}
