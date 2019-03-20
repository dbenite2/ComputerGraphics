/**
 * @author David Benitez 
 * @author Juan Esteban Fonseca
 */


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D; 
import java.awt.shape;
import java.awt.geom.Line2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BezierCurves extends JPanel{

    public static int FRAME_WIDTH  = 500;
    public static int FRAME_HEIGHT = 500;
    public static int AXIS_SIZE    = 20;
    Dimension size;
    Graphics2D g2d;
    
    public static double[] xPoints = {-100, -100, 100, 100};
    public static double[] yPoints = {-100, 100, -100, 100};


    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g2d = (Graphics2D) g;
        size = getSize();
        g2d.setColor(Color.blue);

        double x1, x0, y1, y0;
        for(double n = 0; n < 1; n += 0.01d){
            x0 = sumP(n,xPoints);
            y0 = sumP(n,yPoints);
            x1 = sumP(n+0.01d,xPoints);
            y1 = sumP(n+0.01d,yPoints);
            drawCurveLine(x1,y1,x0,y0);
            x0 = x1;
            y0 = y1;
            
        }
    }

    public void drawCurveLine(double x0, double y0, double x1, double y1){

        x0 = x0 + size.width / 2.0;
        x1 = x1 + size.width / 2.0;

        y0 = size.height / 2.0 - y0;
        y1 = size.height / 2.0 - y1;

        g2d.drawLine((int)x0, (int)y0, (int)x1,(int)y1);
    }

    public double sumP(double u, double[] arr){
        double n = (double) arr.length;
        double result = 0.0;
        for(int k = 0; k < n; k++){
            result = result + (arr[k] * Bezier(u, n-1, (double)k));
        }
        return result;
    }

    public double Bezier(double u, double n, double k){
        double result1 = c(n, k);
        double result2 = Math.pow(u, k);
        double result3 = Math.pow((1-u),(n-k));
        return result1 * result2 * result3;
    }

    public double c (double n, double k){
        double result  = factorial(n);
        double result1 = factorial(k) * factorial(n-k);
        return (result)/(result1);
    }

    public double factorial (double n){
        if(n == 0.0){
            return 1.0;
        }else{
            return (n * factorial(n-1.0));
        }
    }

    public static void main (String[] args){
        BezierCurves bz = new BezierCurves();

        JFrame frame = new JFrame("Curvas de Bezier");
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.add(bz);

        frame.setSize(BezierCurves.FRAME_WIDTH, BezierCurves.FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}