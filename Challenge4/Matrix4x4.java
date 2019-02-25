/**
 * @author David Benitez 
 * @author Juan Esteban Fonseca
 */


public class Matrix4x4{
    private double[][] matrix4x4 = new double [4][4];

    public Matrix4x4(double x1, double x2, double x3, double x4 , double y1, double y2, double y3, double y4, double z1, double z2, double z3, double z4, double w1, double w2, double w3, double w4){
        this.matrix4x4[0][0] = x1;
        this.matrix4x4[1][0] = y1;
        this.matrix4x4[2][0] = z1;
        this.matrix4x4[3][0] = w1;
        this.matrix4x4[0][1] = x2;
        this.matrix4x4[1][1] = y2;
        this.matrix4x4[2][1] = z2;
        this.matrix4x4[3][1] = w2;
        this.matrix4x4[0][2] = x3;
        this.matrix4x4[1][2] = y3;
        this.matrix4x4[2][2] = z3;
        this.matrix4x4[3][2] = w3;
        this.matrix4x4[0][3] = x4;
        this.matrix4x4[1][3] = y4;
        this.matrix4x4[2][3] = z4;
        this.matrix4x4[3][3] = w4;
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

    public static Points3 times1(Matrix4x4 matrix ,Points2 puntos){
        
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
             
            Matrix4x4 solucion = new Matrix4x4(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
 
            for(int i = 0; i < matrix1.getSizeRow(); i++){
                for(int j = 0; j < matrix2.getSizeCol(); j++){
                    double sum = 0;
                    for(int k = 0; k < matrix1.getSizeCol();k++){
                        sum += matrix1.getPoint(i, k) * matrix2.getPoint(k, j);
                    }
                    solucion.setPos(i, j, sum);
                }
            }
 
            return solucion;
        }
    }
}