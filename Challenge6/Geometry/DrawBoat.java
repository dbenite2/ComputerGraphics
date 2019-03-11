package Geometry;

import java.awt.Color;
import java.awt.Component;
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
import Math.Matrix4x4;
import Math.Vector4;
import Math.Projection;
import Math.Translation;
import Math.Uvn;

public class DrawBoat extends JPanel implements KeyListener {

    PolygonObject po;
    PolygonObject transformed;

    Matrix4x4 actualTrans = new Matrix4x4();

    public static int FRAME_WIDTH = 600;
    public static int FRAME_HEIGHT = 600;
    public static int AXIS_SIZE = 20;

    Dimension size;
    Graphics2D g2d;

    int proyectionPlaneDistance;

    // Center of the object

    double maxX;
    double minX;
    double minY;
    double maxY;
    double maxZ;
    double minZ;
    double centerX;
    double centerY;
    double centerZ;

    // Camera position

    double theta = 0;
    double phi = 0;
    double radius = 500;

    // Increments

    public static final double THETA_INCREMENT = Math.PI / 18d;
    public static final double PHI_INCREMENT = Math.PI / 18d;
    public static final double RADIUS_INCREMENT = 18d;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        size = getSize();

        g2d.setColor(Color.RED);
        drawOneLine(-DrawBoat.AXIS_SIZE, 0, DrawBoat.AXIS_SIZE, 0);

        g2d.setColor(Color.GREEN);
        drawOneLine(0, -DrawBoat.AXIS_SIZE, 0, DrawBoat.AXIS_SIZE);

        g2d.setColor(Color.BLUE);

        transformObject();

        applyUVN();

        applyProjection();

        transformed.drawObject(this);

    }

    private void transformObject() {
        transformed = PolygonObject.transformObject(po, actualTrans);
    }

    private void applyUVN() {
        double yCamera = radius * Math.sin(phi) + centerY;
        double projectedR = radius * Math.cos(phi);
        double xCamera = projectedR * Math.sin(theta) + centerX;
        double zCamera = projectedR * Math.cos(theta) + centerZ;

        Vector4 cameraPos = new Vector4(xCamera, yCamera, zCamera);
        Vector4 objectCenter = new Vector4(centerX, centerY, centerZ);
        Vector4 V = new Vector4(0, 1, 0);

        Uvn uvnMat = new Uvn(cameraPos, objectCenter, V);

        transformed = PolygonObject.transformObject(transformed, uvnMat);
    }

    private void applyProjection() {
        Projection proj = new Projection(-proyectionPlaneDistance);
        transformed = PolygonObject.transformObject(transformed, proj);
    }

    public void drawOneLine(int x1, int y1, int x2, int y2) {

        x1 = x1 + size.width / 2;
        x2 = x2 + size.width / 2;

        y1 = size.height / 2 - y1;
        y2 = size.height / 2 - y2;

        g2d.drawLine(x1, y1, x2, y2);
    }

    public void readObjectDescription(String fileName) {
        Scanner in;
        po = new PolygonObject();
        try {
            in = new Scanner(new File(fileName));
            // Read the number of vertices
            int numVertices = in.nextInt();
            Vector4[] vertexArray = new Vector4[numVertices];
            // Read the vertices
            for (int i = 0; i < numVertices; i++) {
                // Read a vertex
                int x = in.nextInt();
                System.out.println(x);
                int y = in.nextInt();
                int z = in.nextInt();
                vertexArray[i] = new Vector4(x, y, z);
                if (i == 0) {
                    initializeMaxMin(vertexArray[i]);
                } else {
                    updateMaxMin(vertexArray[i]);
                }
            }
            computeCenter();
            int numEdges = in.nextInt();
            // Read the edges
            for (int i = 0; i < numEdges; i++) {
                // Read an edge
                int start = in.nextInt();
                int end = in.nextInt();
                Edge edge = new Edge(vertexArray[start], vertexArray[end]);
                po.addEdge(edge);
            }
            // Read the Project Plane Distance to the virtual camera
            proyectionPlaneDistance = in.nextInt();
            radius = proyectionPlaneDistance;
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

    }

    private void initializeMaxMin(Vector4 v) {
        minX = v.getX();
        maxX = v.getX();
        minY = v.getY();
        maxY = v.getY();
        minZ = v.getZ();
        maxZ = v.getZ();
    }

    private void updateMaxMin(Vector4 v) {
        if (v.getX() > maxX)
            maxX = v.getX();
        if (v.getY() > maxY)
            maxY = v.getY();
        if (v.getZ() > maxZ)
            maxZ = v.getZ();
        if (v.getX() < minX)
            minX = v.getX();
        if (v.getY() < minY)
            minY = v.getY();
        if (v.getZ() < minZ)
            minZ = v.getZ();
    }

    private void computeCenter() {
        centerX = (minX + maxX) / 2;
        centerY = (minY + maxY) / 2;
        centerZ = (minZ + maxZ) / 2;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_A) {        // Left
            Translation trans = new Translation(-10, 0, 0);
            actualTrans = Matrix4x4.times2(actualTrans, trans);
          } else if(e.getKeyCode() == KeyEvent.VK_D) { // Right
            Translation trans = new Translation(10, 0, 0);
            actualTrans = Matrix4x4.times2(actualTrans, trans);
          } else if(e.getKeyCode() == KeyEvent.VK_W) { // Up
            Translation trans = new Translation(0, 10, 0);
            actualTrans = Matrix4x4.times2(actualTrans, trans);
          } else if(e.getKeyCode() == KeyEvent.VK_S) { // Down
            Translation trans = new Translation(0, -10, 0);
            actualTrans = Matrix4x4.times2(actualTrans, trans);
          } else if(e.getKeyCode() == KeyEvent.VK_R) { // Reset
            actualTrans = new Matrix4x4();
          } else if(e.getKeyCode() == KeyEvent.VK_J) { // change longitude
            theta -= THETA_INCREMENT;
            if(theta <= - Math.PI) theta = - Math.PI;
          } else if(e.getKeyCode() == KeyEvent.VK_L) { // change longitude
            theta += THETA_INCREMENT;
            if(theta >= Math.PI) theta = Math.PI;
          } else if(e.getKeyCode() == KeyEvent.VK_I) { // change latitude
            phi += PHI_INCREMENT;
            if(phi >= Math.PI / 2) phi =  Math.PI / 2 - PHI_INCREMENT;
          } else if(e.getKeyCode() == KeyEvent.VK_K) { // change latitude
            phi -= PHI_INCREMENT;
            if(phi <= - Math.PI / 2) phi = - Math.PI / 2 + PHI_INCREMENT;
          } else if(e.getKeyCode() == KeyEvent.VK_DOWN) { // change latitude
            radius += RADIUS_INCREMENT;
           
          } else if(e.getKeyCode() == KeyEvent.VK_UP) { // change latitude
            radius -= RADIUS_INCREMENT;
            
          } else if(e.getKeyCode() == KeyEvent.VK_RIGHT) { // change latitude
            centerX += RADIUS_INCREMENT;
            
          } else if(e.getKeyCode() == KeyEvent.VK_LEFT) { // change latitude
            centerX -= RADIUS_INCREMENT;
            
          }
    }

    @Override
    public void keyTyped(KeyEvent e) {
      System.out.println("Key typed");
    }
  
    @Override
    public void keyReleased(KeyEvent e) {
      System.out.println("key released");
      repaint();
    }

    
    public static void main(String[] args) {
        DrawBoat db = new DrawBoat();

        db.readObjectDescription("../test3d.txt");

        JFrame frame = new JFrame("Points");
        // Al cerrar el frame, termina la ejecución de este programa
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Agregar un JPanel que se llama Points (esta clase)
        //frame.add(new Points());
        frame.add(db);
        frame.addKeyListener(db);
        // Asignarle tamaño
        frame.setSize(600, 600);
        // Poner el frame en el centro de la pantalla
        frame.setLocationRelativeTo(null);
        // Mostrar el frame
        frame.setVisible(true);
    }

 
}