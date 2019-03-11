/**
 * @author David Benitez 
 * @author Juan Esteban Fonseca
 */
package Math;

public class Vector4 {
    double [] vector;
    
    /**
     * Constructor
     */
    public Vector4() {
        vector = new double[4];
        vector[3] = 1d;
    }
    
    public Vector4(double x, double y, double z) {
        vector = new double[4];
        vector[0] = x;
        vector[1] = y;
        vector[2] = z;
        vector[3] = 1d;
    }

    
    public Vector4(double x1, double y1, double z1, double x2, double y2, double z2) {
        vector = new double[4];
        vector[0] = x2 - x1;
        vector[1] = y2 - y1;
        vector[2] = z2 - z1;
        vector[3] = 1d;
    }
    
   
    public static Vector4 crossProduct(Vector4 v1, Vector4 v2) {
        double x = v1.getY() * v2.getZ() - v1.getZ() * v2.getY();
        double y = - (v1.getX() * v2.getZ() - v1.getZ() * v2.getX());
        double z = v1.getX() * v2.getY() - v1.getY() * v2.getX();
        return new Vector4(x, y, z);
    }
    
   
    public static double dotProduct(Vector4 v1, Vector4 v2) {
        return  v1.getX() * v2.getX() + v1.getY() * v2.getY() + 
                v1.getZ() * v2.getZ();
    }
    
  
    public static Vector4 subtract(Vector4 v1, Vector4 v2) {
        double x = v1.getX() - v2.getX();
        double y = v1.getY() - v2.getY();
        double z = v1.getZ() - v2.getZ();
        return new Vector4(x, y, z);
    }
    
    public static Vector4 minus(Vector4 v) {
        double x = - v.getX();
        double y = - v.getY();
        double z = - v.getZ();
        return new Vector4(x, y, z);
    }
    
    
    public double magnitude() {
        return Math.sqrt(vector[0]*vector[0] + vector[1]*vector[1] + vector[2]*vector[2]);
    }
    
    public void normalize() {
        double mag = this.magnitude();
        vector[0] /= mag;
        vector[1] /= mag;
        vector[2] /= mag;
    }
    
    
    public Vector4(double [] vector) {
        this.vector = vector;
    }
    
    
    public void normalizeW() {
        if (vector[3] == 0) {
            return;
        }
        for(int i = 0; i < 4; i++) {
            vector[i] /= vector[3];
        }
    }
    
    
    public double get(int pos) {
        return vector[pos];
    }
    
   
    public double getX() {
        return vector[0];
    }
    
    
    public double getY() {
        return vector[1];
    }
     
    public double getZ() {
        return vector[2];
    }
    
    
    public double getW() {
        return vector[3];
    }

}