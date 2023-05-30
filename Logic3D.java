import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Spin extends JPanel {
    double angleX = (Math.PI/180) * 0;
	double angleY = (Math.PI/180) * 0;
	double angleZ = (Math.PI/180) * 0;
    double size = 300;
    double length = 1;
    int centerX = 250;
    int centerY = 250;
    int offset = 100;

    boolean xCheck = false;
    boolean yCheck = false;
    boolean zCheck = false;

    public Spin() {
        this.setPreferredSize(new Dimension(500, 500));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
    }

    private void checkbox() {
        JCheckBox cb1 = new JCheckBox("X");
	    JCheckBox cb2 = new JCheckBox("Y");
	    JCheckBox cb3 = new JCheckBox("Z");
        cb1.setBackground(Color.BLACK);
        cb2.setBackground(Color.BLACK);
        cb3.setBackground(Color.BLACK);
        cb1.setForeground(Color.WHITE);
        cb2.setForeground(Color.WHITE);
        cb3.setForeground(Color.WHITE);

		this.add(cb1);
		this.add(cb2);
		this.add(cb3);

        JButton b = new JButton("Reset");
        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                cb1.setSelected(false);
                cb2.setSelected(false);
                cb3.setSelected(false);

                xCheck = false;
                yCheck = false;
                zCheck = false;

                angleX = 0;
                angleY = 0;
                angleZ = 0;
            }  
        });
        this.add(b);

        cb1.addItemListener(new ItemListener() {    
            public void itemStateChanged(ItemEvent e) {        
                if(xCheck)         
                    xCheck = false;
                else
                    xCheck = true;
            }
        });
        cb2.addItemListener(new ItemListener() {    
            public void itemStateChanged(ItemEvent e) {                 
                if(yCheck)         
                    yCheck = false;
                else
                    yCheck = true;
            }
        });
        cb3.addItemListener(new ItemListener() {    
            public void itemStateChanged(ItemEvent e) {                 
                if(zCheck)         
                    zCheck = false;
                else
                    zCheck = true;
            }
        });
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        System.out.println(angleX + ", " + angleY + ", " + angleZ);
        checkbox();

        if(xCheck)
            angleX += (Math.PI/180) * 1;
        if(yCheck)
		    angleY += (Math.PI/180) * 1;
        if(zCheck)
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

    private void draw(Graphics g, int midx, int midy, double[][][] points, double[][] rotateX, double[][] rotateY, double[][] rotateZ) {
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

            projected2d = mult(size, projected2d);
            int x = (int) projected2d[0][0];
            int y = (int) projected2d[1][0];
            g.drawLine(x+centerX, y+centerY, x+centerX, y+centerY);
            projected[i][0] = x;
            projected[i][1] = y;
        }
        if(points.length == 5)
            drawLinesTri(g, projected);
        else
            drawLines(g, projected);
        // g.setColor(Color.WHITE);
        // g.drawLine(250, 250, 250, 250);
        repaint();
    }

	private void drawLines(Graphics g, double[][] pos) {
		g.setColor(Color.GREEN);
		g.drawLine((int)pos[0][0]+centerX, (int)pos[0][1]+centerY, (int)pos[1][0]+centerX, (int)pos[1][1]+centerY);
		g.drawLine((int)pos[2][0]+centerX, (int)pos[2][1]+centerY, (int)pos[3][0]+centerX, (int)pos[3][1]+centerY);
		g.drawLine((int)pos[4][0]+centerX, (int)pos[4][1]+centerY, (int)pos[5][0]+centerX, (int)pos[5][1]+centerY);
		g.drawLine((int)pos[6][0]+centerX, (int)pos[6][1]+centerY, (int)pos[7][0]+centerX, (int)pos[7][1]+centerY);

        g.drawLine((int)pos[0][0]+centerX, (int)pos[0][1]+centerY, (int)pos[3][0]+centerX, (int)pos[3][1]+centerY);
		g.drawLine((int)pos[2][0]+centerX, (int)pos[2][1]+centerY, (int)pos[1][0]+centerX, (int)pos[1][1]+centerY);
		g.drawLine((int)pos[4][0]+centerX, (int)pos[4][1]+centerY, (int)pos[7][0]+centerX, (int)pos[7][1]+centerY);
		g.drawLine((int)pos[6][0]+centerX, (int)pos[6][1]+centerY, (int)pos[5][0]+centerX, (int)pos[5][1]+centerY);

        g.drawLine((int)pos[0][0]+centerX, (int)pos[0][1]+centerY, (int)pos[4][0]+centerX, (int)pos[4][1]+centerY);
		g.drawLine((int)pos[2][0]+centerX, (int)pos[2][1]+centerY, (int)pos[6][0]+centerX, (int)pos[6][1]+centerY);
		g.drawLine((int)pos[1][0]+centerX, (int)pos[1][1]+centerY, (int)pos[5][0]+centerX, (int)pos[5][1]+centerY);
		g.drawLine((int)pos[3][0]+centerX, (int)pos[3][1]+centerY, (int)pos[7][0]+centerX, (int)pos[7][1]+centerY);
	}

    private void drawLinesTri(Graphics g, double[][] pos) {
		g.setColor(Color.GREEN);
		g.drawLine((int)pos[0][0]+centerX, (int)pos[0][1]+centerY, (int)pos[1][0]+centerX, (int)pos[1][1]+centerY);
		g.drawLine((int)pos[2][0]+centerX, (int)pos[2][1]+centerY, (int)pos[3][0]+centerX, (int)pos[3][1]+centerY);
        g.drawLine((int)pos[0][0]+centerX, (int)pos[0][1]+centerY, (int)pos[3][0]+centerX, (int)pos[3][1]+centerY); 
        g.drawLine((int)pos[2][0]+centerX, (int)pos[2][1]+centerY, (int)pos[1][0]+centerX, (int)pos[1][1]+centerY);

        g.drawLine((int)pos[0][0]+centerX, (int)pos[0][1]+centerY, (int)pos[4][0]+centerX, (int)pos[4][1]+centerY);
        g.drawLine((int)pos[1][0]+centerX, (int)pos[1][1]+centerY, (int)pos[4][0]+centerX, (int)pos[4][1]+centerY);
        g.drawLine((int)pos[2][0]+centerX, (int)pos[2][1]+centerY, (int)pos[4][0]+centerX, (int)pos[4][1]+centerY);
        g.drawLine((int)pos[3][0]+centerX, (int)pos[3][1]+centerY, (int)pos[4][0]+centerX, (int)pos[4][1]+centerY);
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

        double[][][] pointstri = {
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
                {-(length/2)},
                {-(length/2)}
            },
            {
                {-(length/2)},
                {-(length/2)},
                {-(length/2)},
            },
            {
                {0},
                {(length/2)},
                {0},
            },
        };
        //draw(g, 0, 0, pointstri, rotateX, rotateY, rotateZ);
        draw(g, 0, 0, points, rotateX, rotateY, rotateZ);
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
