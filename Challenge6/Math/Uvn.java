/**
 * @author David Benitez 
 * @author Juan Esteban Fonseca
 */
package Math;

public class Uvn extends Matrix4x4 {
    Vector4 from;
    Vector4 lookAt;
    Vector4 up;

    public Uvn(){
        super();
    }

    public Uvn(Vector4 from, Vector4 lookAt, Vector4 up){
        super();
        Vector4 n,u,v = new Vector4();
        n = Vector4.minus(Vector4.subtract(lookAt, from));
        n.normalize();
        u = Vector4.crossProduct(up, n);
        u.normalize();
        v = Vector4.crossProduct(n, u);
        double x, y, z;
        x = Vector4.dotProduct(Vector4.minus(u), from);
        y = Vector4.dotProduct(Vector4.minus(v), from);
        z = Vector4.dotProduct(Vector4.minus(n), from);

        matrix4x4[0][0] = u.getX();
        matrix4x4[0][1] = u.getY();
        matrix4x4[0][2] = u.getZ();
        matrix4x4[0][3] = x;
        matrix4x4[1][0] = v.getX();
        matrix4x4[1][1] = v.getY();
        matrix4x4[1][2] = v.getZ();
        matrix4x4[1][3] = y;
        matrix4x4[2][0] = n.getX();
        matrix4x4[2][1] = n.getY();
        matrix4x4[2][2] = n.getZ();
        matrix4x4[2][3] = z;
    }
}