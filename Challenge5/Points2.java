/**
 * @author David Benitez 
 * @author Juan Esteban Fonseca
 */



public class Points2{


   private double x;
   private double y;
   private double w;
   double arr[] = {x,y,w}; 

    public Points2(double x , double y, double w){

        this.x = x;
        this.y = y;
        this.w = w;
        arr[0] = this.x;
        arr[1] = this.y;
        arr[2] = this.w;
        
    }

    public double getX(){
        return x;
    } 
    
    public double getY(){
        return y;
    }

    public double getW(){
        return w;
    }

    public void setX(double x){

        this.x = x;
        
    }

    public void setY(double y){

        this.y = y;
        
    }

    public void setW(double w){

        this.w = w;
        
    }

    public double inArr(int i){

        
        return this.arr[i];

    }

    public void sumArr(int i,double j){
        
        arr[i] = arr[i] + j;
        //System.out.println(arr[i]);
    
    }
}