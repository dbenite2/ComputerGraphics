/**
 * @author David Benitez 
 * @author Juan Esteban Fonseca
 */


 public  class Matrix3x3{
    private double[][] matrix3x3 = new double [3][3];

    public Matrix3x3(double x1, double x2, double x3, double y1, double y2, double y3, double z1, double z2, double z3){
        this.matrix3x3[0][0] = x1;
        this.matrix3x3[1][0] = y1;
        this.matrix3x3[2][0] = z1;
        this.matrix3x3[0][1] = x2;
        this.matrix3x3[1][1] = y2;
        this.matrix3x3[2][1] = z2;
        this.matrix3x3[0][2] = x3;
        this.matrix3x3[1][2] = y3;
        this.matrix3x3[2][2] = z3;
    }

    public double[][] getMatrix(){
        return this.matrix3x3;
    }

    public int getSizeRow(){
        return this.matrix3x3.length;
    }

    public int getSizeCol(){
        return this.matrix3x3[0].length;
    }
    

    public double getPoint(int x, int y){

        return matrix3x3[x][y];

    }

    public void setPos(int x, int y, double value){
        this.matrix3x3[x][y] = value;
    }

    /**
     * Metodo para la multiplicación de una matriz 3x3 con un punto 
     * @param matrix : una matriz de 3x3 
     * @param puntos : un punto con coordenadas x,y,z
     * @return: el resultado de la operación 
     */

    public static Points2 times1(Matrix3x3 matrix ,Points2 puntos){
        Points2 solucion = new Points2(0, 0, 0);
        for(int i = 0; i < matrix.getSizeRow();i++){
             
            for(int j = 0; j < matrix.getSizeCol(); j++){
                double x = matrix.getPoint(i, j) * puntos.inArr(j);
                solucion.sumArr(i, x);
            }
        }
 
        return solucion;
 
    }

    /**
     * Metodo para la multiplicación entre 2 matrices 3x3 
     */
 
    public static Matrix3x3 times2(Matrix3x3 matrix1, Matrix3x3 matrix2){
 
        if(matrix1.getSizeCol() != matrix2.getSizeRow()){
            System.out.println("Las matrices no se pueden multiplicar");
            return null;
        }else{
             
            Matrix3x3 solucion = new Matrix3x3(0, 0, 0, 0, 0, 0, 0, 0, 0);
 
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