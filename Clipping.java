import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.lang.Math;

public class Clipping extends JPanel implements MouseListener{

    private static final long serialVersionUID = 1L;
    private int xMin;
    private int xMax;
    private int yMin;
    private int yMax;

    private LineClipper clipper;
    Line2D.Double linea1;
    private int mousexMin;
    private int mousexMax;
    private int mouseyMin;
    private int mouseyMax;
    

    private class LineSegment {
        public int x1;
        public int x2;
        public int y1;
        public int y2;

        public LineSegment(int x1, int y1, int x2, int y2){
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
        }
    }

    public interface LineClipper{
        public LineSegment clip(LineSegment clip);
    }

    /**
     * Clase encargada de implementar el metodo de clipping
     */
    public class LiangBarsky implements LineClipper{

        public LineSegment clip(LineSegment line){

        
            double u1 = 0, u2 = 1;
            int x1 = line.x1;
            int x2 = line.x2;
            int y1 = line.y1;
            int y2 = line.y2;
            int dx = x2 - x1, dy = y2 - y1;
            int p[] = {-dx, dx, -dy, dy};
             int [] q = new int[4]; 
             q[0] = x1 - mousexMin; // Left mousexMax - x1, y1 - mouseyMin, mouseyMax - y1
             q[1] = mousexMax - x1; // right
             q[2] = y1 - mouseyMin; // bottom
             q[3] = mouseyMax - y1; // top
            //int q[] = {x1 - xMin, xMax - x1, y1 - yMin, yMax - y1}; //Arreglo con cuadrado fijo
            
            
            for (int i = 0; i < 4; i++){
                if(p[i] == 0) {
                    if(q[i] < 0){
                        return null;
                    }
                }else{
                    double u = (double) q[i] / p[i];
                    if(p[i] < 0){
                        u1 = Math.max(u,u1);
                    }else{
                        u2 = Math.min(u,u2);
                    }
                }
            }
            if(u1 > u2){
                return null;
            }

            int nx1, ny1, nx2, ny2;
            nx1 = (int) (x1 + u1 * dx);
            ny1 = (int) (y1 + u1 * dy);
            nx2 = (int) (x1 + u2 * dx);
            ny2 = (int) (y1 + u2 * dy);
            return new LineSegment(nx1, ny1, nx2, ny2);


            
        }    
    }

    /**
     * Constructor de la clase 
     */
    public Clipping(int xMin, int yMin, int xMax, int yMax){
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
        linea1 = new Line2D.Double();
        this.addMouseListener(this);

        clipper = new LiangBarsky();
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.drawLine(0,250,500,250);
        g2d.setColor(Color.black);
        g2d.drawLine(250,0,250,500);
        //drawRect(g2d,xMin ,yMin ,xMax, yMax);
        if(mousexMin != 0 && mouseyMin != 0 && mousexMax != 0 && mouseyMax != 0 ){
            drawRect(g2d,mousexMin, mouseyMin, mousexMax, mouseyMax);
        }
       

        int x1 = 391;
        int x2 = 77;
        int y1 = 292;
        int y2 = 183;
        LineSegment line,clippedLine;
        line = new LineSegment( x1,  y1,  x2,  y2);
        clippedLine = clipper.clip(line);
        
        if(clippedLine == null) {
            paintUnclipped(g2d,line.x1, line.y1, line.x2, line.y2);
        }else{
            paintUnclipped(g2d,line.x1,line.y1,clippedLine.x1,clippedLine.y1);
            paintUnclipped(g2d,clippedLine.x2,clippedLine.y2,line.x2,line.y2);
            paintClipped(g2d,clippedLine.x1,clippedLine.y1,clippedLine.x2, clippedLine.y2);
        }

    }
    
    /**
     * Graficación de los segmentos de linea no "clipped"
    */
    public void paintUnclipped(Graphics g2d,int x1,int y1, int x2, int y2){
        g2d.setColor(Color.red);
        g2d.drawLine(x1,y1,x2,y2);
    }

    /**
     * Graficación de los segmentos de linea dentro del área clipper 
     */
    public void paintClipped(Graphics g2d,int x1,int y1, int x2, int y2){
        g2d.setColor(Color.GREEN);
        g2d.drawLine(x1,y1,x2,y2);
    }

    /**
     * Metodo para graficar un cuadrado.
     */
    public void drawRect(Graphics g2d,int xMin,int yMin, int xMax, int yMax){
        g2d.setColor(Color.blue);
        g2d.drawLine(xMin, yMin, xMax, yMax);
        g2d.drawLine(xMin, yMin, xMin, yMax);
        g2d.drawLine(xMin, yMin, xMax, yMin);
        g2d.drawLine(xMin, yMax, xMax, yMax);
        g2d.drawLine(xMax, yMin, xMax, yMax);
    }

    @Override
    public void mouseClicked(MouseEvent e){}

    @Override
    public void mouseEntered(MouseEvent e){}

    @Override
    public void mouseExited(MouseEvent e){}

    @Override
    public void mousePressed(MouseEvent e){
        mousexMin = e.getX();
        mouseyMin = e.getY();
        System.out.println("mousepressedX:" + mousexMin);
        System.out.println("mousepressedY:" + mouseyMin);
    }

    @Override
    public void mouseReleased(MouseEvent e){
        if(mousexMin < e.getX()){
            mousexMax = e.getX();
        }else{
            mousexMax = mousexMin;
            mousexMin = e.getX();
        }

        if (mouseyMin < e.getY()) {
            mouseyMax = e.getY();
        } else {
            mouseyMax = mouseyMin;
            mouseyMin = e.getY();
        }
        System.out.println("------------------------------------------------------");
        System.out.println("mousepressedX:" + mousexMin);
        System.out.println("mousepressedY:" + mouseyMin);
        System.out.println("mousereleasedX:" + mousexMax);
        System.out.println("mousereleasedy:" + mouseyMax);
        repaint();
    }

    public static void main(String[] args){
        
        JFrame frame = new JFrame("Clipping");
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Clipping(208,275,288,208));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
    }
}

