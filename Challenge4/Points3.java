/**
 * @author David Benitez 
 * @author Juan Esteban Fonseca
 */



public class Points3{
    
    
    private double x;
    private double y;
    private double w;
    private double z;
    private double arr[] = {x,y,w,z};
    

    public Points3(double x , double y, double w, double z){

        this.x = x;
        this.y = y;
        this.w = w;
        this.z = z;
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

    public double getZ(){
        return z;
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

    public void setZ(double z){

        this.z = z;
    }

    public double inArr(int i){

        
        return this.arr[i];

    }

    public void sumArr(int index,double value){
        
        arr[index] = arr[index] + value;
    
    }
}