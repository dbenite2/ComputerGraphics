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
import javax.swing.JFrame;
import java.util.Random; 


public class Run {

    // static class Punto{
    //     private int x;
    //     private int y;

    //     public Punto(int x, int y){
    //         this.x = x;
    //         this.y = y;
    //     }

    //     /**
    //      * @return the x
    //      */
    //     public int getX() {
    //         return x;
    //     }

    //     /**
    //      * @return the y
    //      */
    //     public int getY() {
    //         return y;
    //     }
    // }

    public static List<String> readFileInList(String fileName) { 
        List<String> lines = Collections.emptyList(); 
    try
    { 
      lines = 
       Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8); 
    } 
  
    catch (IOException e) { 
  
      // do something 
        e.printStackTrace(); 
        } 
        return lines; 
    } 

    public static void main(String[] args) {
        File currentDirFile = new File(".");
        String helper = currentDirFile.getAbsolutePath();
        String currentDir = helper.substring(0, helper.length() - 1);
        String fileName = currentDir + "/test3d.txt";
        List l = readFileInList(fileName);
        Iterator<String> itr = l.iterator();
        int n = Integer.parseInt(itr.next());
        Point [] arrPuntos = new Point[n];
        Points2 [] arrPunt3d = new Points2[n];
        String line;
        for(int i = 0; i < n; i++){
            line = itr.next();
            //Point punto = new Point(Integer.parseInt(line.split(" ")[0]),Integer.parseInt(line.split(" ")[1]));
            Points2 punto = new Points2(Double.parseDouble(line.split(" ")[0]),Double.parseDouble(line.split(" ")[1]),Double.parseDouble(line.split(" ")[2]));
            arrPunt3d[i] = punto;
        }
        int k = Integer.parseInt(itr.next());
        //Points2 [] arrDirs = new Points2[k];
        Point [] arrDirs = new Point[k];
        for(int i = 0; i < k; i++){
            line = itr.next();
            Point dir = new Point(Integer.parseInt(line.split(" ")[0]),Integer.parseInt(line.split(" ")[1]));
            arrDirs[i] = dir;
        }
        EcParSegReg test = new EcParSegReg(100, 100, 200, 100);
        EcParSegReg test2 = new EcParSegReg(200, 200, 150, 250);
        test.solve(test, test2);
        JFrame frame = new JFrame("Points");
        // Al cerrar el frame, termina la ejecución de este programa
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Agregar un JPanel que se llama Points (esta clase)
        //frame.add(new Points());
        frame.add(new Darwing(arrPunt3d, arrDirs));
        // Asignarle tamaño
        frame.setSize(500, 500);
        // Poner el frame en el centro de la pantalla
        frame.setLocationRelativeTo(null);
        // Mostrar el frame
        frame.setVisible(true);
    }
}