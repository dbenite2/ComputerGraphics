/**
 * @author David Benitez 
 * @author Juan Esteban Fonseca
 */
package Math;

import javax.transaction.TransactionRequiredException;

public class Translation extends Matrix4x4 {

    public Translation(){
        super();
    }

    public Translation(double dx, double dy, double dz){
        super();
        Matrix4x4[0][3] = dx;
        Matrix4x4[1][3] = dy;
        Matrix4x4[2][3] = dz;
    }
}