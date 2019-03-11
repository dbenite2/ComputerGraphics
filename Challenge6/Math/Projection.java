/**
 * @author David Benitez 
 * @author Juan Esteban Fonseca
 */
package Math;


public class Projection extends Matrix4x4 {

    public Projection(){
        super();
    }

    public Projection(double d){
        super();
        Matrix[3][2] = 1/d;
        Matrix4x4[3][3] = 0;
    }
}

