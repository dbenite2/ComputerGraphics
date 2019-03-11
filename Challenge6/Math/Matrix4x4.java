/**
 * @author David Benitez 
 * @author Juan Esteban Fonseca
 */
package Math;


public class Matrix4x4{
    private double[][] matrix4x4 = new double [4][4];

    public Matrix4x4(){
        for(int i = 0; i < 4; i++){
            matrix4x4[i][i] = 1d;
        }
    }

    public Matrix4x4(double [][] matrix4x4){
        this.matrix4x4 = matrix4x4;
       
    }

    public int getSizeRow(){
        return this.matrix4x4.length;
    }

    public int getSizeCol(){
        return this.matrix4x4[0].length;
    }
    

    public double getPoint(int x, int y){

        return matrix4x4[x][y];

    }

    public void setPos(int x, int y, double value){
        this.matrix4x4[x][y] = value;
    }

    /**
     * Metodo para la multiplicación de una matriz 3x3 con un punto 
     * @param matrix : una matriz de 3x3 
     * @param puntos : un punto con coordenadas x,y,z
     * @return: el resultado de la operación 
     */

    public static Points3 times1(Matrix4x4 matrix ,Points3 puntos){
        Points3 solucion = new Points3(0, 0, 0, 0);
        for(int i = 0; i < matrix.getSizeRow();i++){
             
            for(int j = 0; j < matrix.getSizeCol(); j++){
                double x = matrix.getPoint(i, j) * puntos.inArr(j);
                solucion.sumArr(i, x);
            }
        }

 
        return solucion;
 
    }

    /**
     * Metodo para la multiplicación entre 2 matrices 4x4 
     */
 
    public static Matrix4x4 times2(Matrix4x4 matrix1, Matrix4x4 matrix2){
 
        if(matrix1.getSizeCol() != matrix2.getSizeRow()){
            System.out.println("Las matrices no se pueden multiplicar");
            return null;
        }else{
             
            double [][] solMatrix = new double[4][4];
 
            for(int i = 0; i < matrix1.getSizeRow(); i++){
                for(int j = 0; j < matrix2.getSizeCol(); j++){
                    double sum = 0;
                    for(int k = 0; k < matrix1.getSizeCol();k++){
                        sum += matrix1.getPoint(i, k) * matrix2.getPoint(k, j);
                    }
                    solMatrix[i][j] = sum;
                }
            }
 
            return new Matrix4x4(solMatrix);
        }
    }
}