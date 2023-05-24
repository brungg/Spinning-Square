import javax.swing.*;
import java.awt.*;

public class Game extends JPanel {
    double angleX = (Math.PI/180) * 0;
	double angleY = (Math.PI/180) * 0;
	double angleZ = (Math.PI/180) * 0;
    double length = 1;
    int offset = 100;

    public Game() {
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

    private void drawSquare(Graphics g, int midx, int midy, double[][][] points, double[][] rotateX, double[][] rotateY, double[][] rotateZ) {
        g.setColor(Color.WHITE);
        double[][] projected = new double[points.length][2];
        for(int i = 0; i < points.length; i++) {
            double[][] rotated = multiplyMatrices(rotateY, points[i]);
            rotated = multiplyMatrices(rotateX, rotated);
            rotated = multiplyMatrices(rotateZ, rotated);
            int distance = 2;
            double z = 1/(distance - rotated[2][0]);
            double[][] projection = {
                {z, 0, 0},
                {0,z, 0}
            };

            double[][] projected2d = multiplyMatrices(projection, rotated);

            projected2d = mult(200, projected2d);
            int x = (int) projected2d[0][0];
            int y = (int) projected2d[1][0];
            g.drawLine(x+250, y+250, x+250, y+250);
            projected[i][0] = x;
            projected[i][1] = y;
        }
        drawLines(g, projected);
        repaint();
    }

	private void drawLines(Graphics g, double[][] pos) {
		g.setColor(Color.WHITE);
		g.drawLine((int)pos[0][0]+250, (int)pos[0][1]+250, (int)pos[1][0]+250, (int)pos[1][1]+250);
		g.drawLine((int)pos[2][0]+250, (int)pos[2][1]+250, (int)pos[3][0]+250, (int)pos[3][1]+250);
		g.drawLine((int)pos[4][0]+250, (int)pos[4][1]+250, (int)pos[5][0]+250, (int)pos[5][1]+250);
		g.drawLine((int)pos[6][0]+250, (int)pos[6][1]+250, (int)pos[7][0]+250, (int)pos[7][1]+250);

        g.drawLine((int)pos[0][0]+250, (int)pos[0][1]+250, (int)pos[3][0]+250, (int)pos[3][1]+250);
		g.drawLine((int)pos[2][0]+250, (int)pos[2][1]+250, (int)pos[1][0]+250, (int)pos[1][1]+250);
		g.drawLine((int)pos[4][0]+250, (int)pos[4][1]+250, (int)pos[7][0]+250, (int)pos[7][1]+250);
		g.drawLine((int)pos[6][0]+250, (int)pos[6][1]+250, (int)pos[5][0]+250, (int)pos[5][1]+250);

        g.drawLine((int)pos[0][0]+250, (int)pos[0][1]+250, (int)pos[4][0]+250, (int)pos[4][1]+250);
		g.drawLine((int)pos[2][0]+250, (int)pos[2][1]+250, (int)pos[6][0]+250, (int)pos[6][1]+250);
		g.drawLine((int)pos[1][0]+250, (int)pos[1][1]+250, (int)pos[5][0]+250, (int)pos[5][1]+250);
		g.drawLine((int)pos[3][0]+250, (int)pos[3][1]+250, (int)pos[7][0]+250, (int)pos[7][1]+250);
	}

    private void invokeSquare(Graphics g, double[][] rotateX, double[][] rotateY, double[][] rotateZ) {
        double[][][] points = {
            { 
                {-(length/2)},
                {-(length/2)},
                {-(length/2)}
            },
            {
                {(length/2)}, 
                {-(length/2)},
                {-(length/2)}
            },
            {
                {(length/2)},
                {(length/2)},
                {-(length/2)}
            },
            {
                {-(length/2)},
                {(length/2)},
                {-(length/2)},
            },
            {
                {-(length/2)},
                {-(length/2)},
                {(length/2)}
            },
            {
                {(length/2)},
                {-(length/2)},
                {(length/2)}
            },
            {
                {(length/2)},
                {(length/2)},
                {(length/2)}
            },
            {
                {-(length/2)},
                {(length/2)},
                {(length/2)}
            }
        };
        drawSquare(g, 0, 0, points, rotateX, rotateY, rotateZ);
    }

    private double[][] multiplyMatrices(double[][] firstMatrix, double[][] secondMatrix) {
        double[][] result = new double[firstMatrix.length][secondMatrix[0].length];
    
        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = multiplyMatricesCell(firstMatrix, secondMatrix, row, col);
            }
        }
    
        return result;
    }

    private double multiplyMatricesCell(double[][] firstMatrix, double[][] secondMatrix, int row, int col) {
        double cell = 0;
        for (int i = 0; i < secondMatrix.length; i++) {
            cell += firstMatrix[row][i] * secondMatrix[i][col];
        }
        return cell;
    }

    private double[][] mult(double factor, double[][] matrix) {
        double[][] result = new double[matrix.length][matrix[0].length];
        for(int i = 0; i < result.length; i++) {
            for(int j = 0; j < result[0].length; j++) {
                result[i][j] = matrix[i][j] * factor;
            }
        }
        return result;
    }
}
