/**
 * @author David Benitez 
 * @author Juan Esteban Fonseca
 */

 public class Vector3{

    private double[] vector3 = new double[3];

    public Vector3(double x, double y, double z){
        this.vector3[0] = x;
        this.vector3[1] = y;
        this.vector3[2] = z;
    }

    public double getVecPos(int index){
        return this.vector3[index];
    }

    public int getSize(){
        return this.vector3.length;
    }

    public void setVec(int index, double value){
        this.vector3[index] = value;
    }

    /**
     * Metodo para el producto cruz entre dos vectores 
     */
    public static Vector3 crossProduct(Vector3 vector1, Vector3 vector2){
        
        Vector3 solution = new Vector3(0, 0, 0);

        solution.setVec(0, vector1.getVecPos(1) * vector2.getVecPos(2) - vector1.getVecPos(2) * vector2.getVecPos(1));
        solution.setVec(1, vector1.getVecPos(0) * vector2.getVecPos(2) - vector1.getVecPos(2) * vector2.getVecPos(0));
        solution.setVec(2, vector1.getVecPos(0) * vector2.getVecPos(1) - vector1.getVecPos(1) * vector2.getVecPos(0));

        return solution;
    }

    /**
     * Metodo para el producto punto entre 2 dos vectores 
     */

    public static double dotProduct(Vector3 vector1, Vector3 vector2){
        
        double solution = 0;
        final int n = 3;

        for (int i = 0; i < n; i++){
            solution = solution + (vector1.getVecPos(i) * vector2.getVecPos(i));
        }
        
        return solution;
    }

    /**
     * Obtención de la magnitud del vector
     */
    public double magnitude(){
        
        double x = Math.pow(this.vector3[0], 2);
        double y = Math.pow(this.vector3[1], 2);
        double z = Math.pow(this.vector3[2], 2);
        double sum = x + y + z;
        double magnitude = Math.sqrt(sum);

        return magnitude;
    }

    /**
     * Normalización del vector actual
     */
    public void normalize(){

        double magnitude = magnitude();
        this.vector3[0] = this.vector3[0] / magnitude;
        this.vector3[1] = this.vector3[1] / magnitude;
        this.vector3[2] = this.vector3[2] / magnitude;
    }
 }