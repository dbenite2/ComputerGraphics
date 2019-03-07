import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import javax.swing.JPanel;
import java.util.*; 
import java.nio.file.*; 
import java.io.*; 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import javax.swing.JFrame;
import java.util.Random; 

public class Darwing extends JPanel implements KeyListener{

    private Point [] arrPoints;
    private Point [] arrDirs;
    private Points2 [] arrDirs3d;
    private Points2 [] arrPoints3d;
    private Double DISTANCE = -1100d;

    public Darwing (Points2 [] arrPoints3d, Point [] arrDirs){
        this.arrPoints3d = arrPoints3d;
        this.arrDirs = arrDirs;
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
    }

      @Override
    public void keyReleased(KeyEvent e) {}
      @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        persp(DISTANCE);
        paintG(g2d);
        axis(g2d);
    }

    public void axis(Graphics2D g2d) {
        g2d.setColor(Color.black);
        g2d.drawLine(0, 250, 500, 250);
        g2d.setColor(Color.red);
        g2d.drawLine(250, 0, 250, 500);
    }

    public void paintG(Graphics2D g2d){
        System.out.println("Puntos Actuales");
        for(int i = 0; i < arrDirs.length; i++){
            Point s = arrDirs[i];
            int init = s.getX();
            int end = s.getY();
            g2d.setColor(Color.BLACK);
            g2d.drawLine((int) Math.round(this.arrPoints3d[init].getX() + 250)  , (int) Math.round(250 - this.arrPoints3d[init].getY()) , (int) Math.round(this.arrPoints3d[end].getX() + 250)  , (int) Math.round(250 - this.arrPoints3d[end].getY()));
            System.out.println(this.arrPoints3d[i].inArr(0)+" "+this.arrPoints3d[i].inArr(1)+" "+this.arrPoints3d[i].inArr(2));
        } 

    }

    public void traslation(double dx, double dy, double dz){
        
        Matrix4x4 newMatrix = new Matrix4x4(1, 0, 0, dx, 0, 1, 0, dy, 0, 0, 1, dz, 0, 0, 0, 1);
        System.out.println("Translación");
        for (int i = 0; i < this.arrPoints3d.length; i++){
            Points3 temp = new Points3(this.arrPoints3d[i].inArr(0),this.arrPoints3d[i].inArr(1),this.arrPoints3d[i].inArr(2) , 1);
            Points3 res = Matrix4x4.times1(newMatrix, temp);
            this.arrPoints3d[i] = new Points2((int) Math.round(res.inArr(0)), (int) Math.round(res.inArr(1)), (int) Math.round(res.inArr(2)));
            //System.out.println(this.arrPoints3d[i].inArr(0)+" "+this.arrPoints3d[i].inArr(1)+" "+this.arrPoints3d[i].inArr(2));
        }
    }

    public void scaling(double sx, double sy, double sz){
        Points2 tempT = new Points2(this.arrPoints3d[0].inArr(0),this.arrPoints3d[0].inArr(1),this.arrPoints3d[0].inArr(2));
        reset();
        Matrix4x4 newMatrix = new Matrix4x4(sx, 0, 0, 0, 0, sy, 0, 0, 0, 0, sz, 0, 0, 0, 0, 1);
        for (int i = 0; i < this.arrPoints3d.length; i++){
            Points3 temp = new Points3(this.arrPoints3d[i].inArr(0),this.arrPoints3d[i].inArr(1),this.arrPoints3d[i].inArr(2) , 1);
            Points3 res = Matrix4x4.times1(newMatrix, temp);
            this.arrPoints3d[i] = new Points2((int) Math.round(res.inArr(0)), (int) Math.round(res.inArr(1)), (int) Math.round(res.inArr(2)));
        }
        traslation(tempT.inArr(0), tempT.inArr(1), tempT.inArr(2));
    }

    public void persp (double d){
        Matrix4x4 newMatrix = new Matrix4x4(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1/d, 0);
        for (int i = 0; i < this.arrPoints3d.length; i++){
            Points3 temp = new Points3(this.arrPoints3d[i].inArr(0),this.arrPoints3d[i].inArr(1),this.arrPoints3d[i].inArr(2) , this.arrPoints3d[i].inArr(2)/d);
            Points3 res = Matrix4x4.times1(newMatrix, temp);
            //System.out.println();
            this.arrPoints3d[i] = new Points2((int) Math.round(res.inArr(0)), (int) Math.round(res.inArr(1)), (int) Math.round(res.inArr(2)));
        }
        for (int j = 0; j < this.arrPoints3d.length; j++){
            this.arrPoints3d[j].setX(this.arrPoints3d[j].inArr(0)/(this.arrPoints3d[j].inArr(2)/d));
            this.arrPoints3d[j].setY(this.arrPoints3d[j].inArr(1)/(this.arrPoints3d[j].inArr(2)/d));
            //this.arrPoints3d[i].setZ(this.arrPoints3d[i].inArr(2)/(this.arrPoints3d[i].inArr(2)/d));
            this.arrPoints3d[j].setW(this.arrPoints3d[j].inArr(2)/(this.arrPoints3d[j].inArr(2)/d));
        }
    }
    /**
     * Rotacion a en torno al eje z
     */
    public void rotationZ(double x){
        Points2 tempT = new Points2(this.arrPoints3d[0].inArr(0),this.arrPoints3d[0].inArr(1),this.arrPoints3d[0].inArr(2));
        reset();
        double radians = Math.toRadians(x);
        Matrix4x4 newMatrix = new Matrix4x4(Math.cos(radians), -Math.sin(radians), 0, 0, Math.sin(radians), Math.cos(radians), 0, 0, 0, 0, 1, 0, 0, 0, 0, 1);
        for (int i = 0; i < this.arrPoints3d.length; i++){
            Points3 temp = new Points3(this.arrPoints3d[i].inArr(0),this.arrPoints3d[i].inArr(1),this.arrPoints3d[i].inArr(2) , 1);
            Points3 res = Matrix4x4.times1(newMatrix, temp);
            this.arrPoints3d[i] = new Points2((int) Math.round(res.inArr(0)), (int) Math.round(res.inArr(1)), (int) Math.round(res.inArr(2)));
        }
        traslation(tempT.inArr(0), tempT.inArr(1), tempT.inArr(2));
    }

    /**
     * Rotacion a en torno al eje y
     */
    public void rotationY(double x){
        Points2 tempT = new Points2(this.arrPoints3d[0].inArr(0),this.arrPoints3d[0].inArr(1),this.arrPoints3d[0].inArr(2));
        reset();
        double radians = Math.toRadians(x);
        Matrix4x4 newMatrix = new Matrix4x4(Math.cos(radians), 0, Math.sin(radians), 0, 0, 1, 0, 0, -Math.sin(radians), 0, Math.cos(radians), 0, 0, 0, 0, 1);
        for (int i = 0; i < this.arrPoints3d.length; i++){
            Points3 temp = new Points3(this.arrPoints3d[i].inArr(0),this.arrPoints3d[i].inArr(1),this.arrPoints3d[i].inArr(2) , 1);
            Points3 res = Matrix4x4.times1(newMatrix, temp);
            this.arrPoints3d[i] = new Points2((int) Math.round(res.inArr(0)), (int) Math.round(res.inArr(1)), (int) Math.round(res.inArr(2)));
        }
        traslation(tempT.inArr(0), tempT.inArr(1), tempT.inArr(2));
    }

    public void reset(){
        Matrix4x4 newMatrix = new Matrix4x4(1, 0, 0, -this.arrPoints3d[0].inArr(0), 0, 1, 0, -this.arrPoints3d[0].inArr(1), 0, 0, 1, -this.arrPoints3d[0].inArr(2), 0, 0, 0, 1);
        for (int i = 0; i < this.arrPoints3d.length; i++){
            Points3 temp = new Points3(this.arrPoints3d[i].inArr(0),this.arrPoints3d[i].inArr(1),this.arrPoints3d[i].inArr(2) , 1);
            Points3 res = Matrix4x4.times1(newMatrix, temp);
            this.arrPoints3d[i] = new Points2((int) Math.round(res.inArr(0)), (int) Math.round(res.inArr(1)), (int) Math.round(res.inArr(2)));
        }
    }

    /**
     * Rotacion a en torno al eje x
     */
    public void rotationX(double x){
        double radians = Math.toRadians(x);
        Points2 tempT = new Points2(this.arrPoints3d[0].inArr(0),this.arrPoints3d[0].inArr(1),this.arrPoints3d[0].inArr(2));
        reset();
        Matrix4x4 newMatrix = new Matrix4x4(1, 0, 0, 0, 0, Math.cos(radians), -Math.sin(radians), 0, 0, Math.sin(radians), Math.cos(radians), 0, 0, 0, 0, 1);
        for (int i = 0; i < this.arrPoints3d.length; i++){
            Points3 temp = new Points3(this.arrPoints3d[i].inArr(0),this.arrPoints3d[i].inArr(1),this.arrPoints3d[i].inArr(2) , 1);
            Points3 res = Matrix4x4.times1(newMatrix, temp);
            this.arrPoints3d[i] = new Points2((int) Math.round(res.inArr(0)), (int) Math.round(res.inArr(1)), (int) Math.round(res.inArr(2)));
        }
        traslation(tempT.inArr(0), tempT.inArr(1), tempT.inArr(2));
    }
    
    public void setArrs(Point[] arrPoints, Point[] arrDirs){
        this.arrPuntos = arrPoints;
        this.arrDirs = arrDirs;
    }

    public void arr (){
        System.out.println("Arr: ");
        for(int i = 0; i<this.arrPoints.length; i++){
            System.out.println(this.arrPoints[i].getX()+" "+this.arrPoints[i].getY());
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int tecla = e.getKeyCode();
        switch (tecla) {
            case KeyEvent.VK_UP: 
                traslation(0, 5, 0);
                repaint();
                break;

            case KeyEvent.VK_DOWN: 
                traslation(0, -5, 0);
                repaint();
                break;

            case KeyEvent.VK_LEFT: 
                traslation(-20, 0, 0);
                repaint();
                break;

            case KeyEvent.VK_RIGHT: 
                traslation(20, 0, 0);
                repaint();
                break;

            case KeyEvent.VK_M: 
                traslation(0, 0, 200);
                repaint();
                break;

            case KeyEvent.VK_N: 
                traslation(0, 0, -200);
                repaint();
                break;
            case KeyEvent.VK_A: 
                rotationX(-5);
                repaint();
                break;
            case KeyEvent.VK_J: 
                scaling(0.5, 0.5, 0.5);
                repaint();
                break;
            case KeyEvent.VK_L: 
                scaling(2, 2, 2);   
                repaint();
                break;
            case KeyEvent.VK_D: 
                rotationX(5);
                repaint();
                break;
            case KeyEvent.VK_Q: 
                rotationZ(5);
                repaint();
                break;
            case KeyEvent.VK_E: 
                rotationZ(-5);;
                repaint();
                break;
            case KeyEvent.VK_W: 
                rotationY(5);
                repaint();
                break;
            case KeyEvent.VK_S: 
                rotationY(-5);
                repaint();
                break;
            case KeyEvent.VK_H: 
                persp(DISTANCE += 30);
                repaint();
                break;
            case KeyEvent.VK_G: 
                persp(DISTANCE -= 30);
                repaint();
                break;   
        } if (tecla != KeyEvent.VK_H && tecla != KeyEvent.VK_G){
            persp(DISTANCE);
        }
        repaint();    
    }

}