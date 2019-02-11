/**
 * @author David Benitez 
 * @author Juan Esteban Fonseca
 */


public class Methods{

    public static void  main(String[] args) {
        Matrix3x3 matrixS = new Matrix3x3(0, -1, 0, 1, 0, 0, 0, 0, 1);
        Points2 puntoS = new Points2(2, 1, 1);
        Vector3 vector1 = new Vector3(3, -5, 4);
        Vector3 vector2 = new Vector3(2, 6, 5);
        
        System.out.println("Dot product: ");
        double dot = Vector3.dotProduct(vector1, vector2);
        System.out.println(dot);
        
        System.out.println("Cross product: ");
        Vector3 cross = Vector3.crossProduct(vector1, vector2);
        for (int i = 0; i < cross.getSize(); i++){
            System.out.println(cross.getVecPos(i));
        }
        
        System.out.println("Times Matrix point: ");
        Points2 sol = Matrix3x3.times1(matrixS,puntoS);
        for (int i = 0; i < matrixS.getSizeRow(); i++){
            System.out.println(sol.inArr(i));
        }
        
    }

}
