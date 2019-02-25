/**
 * @author David Benitez 
 * @author Juan Esteban Fonseca
 */

 public class EcParSegReg{

    private double x1;
    private double y1;
    private double x2;
    private double y2;

    public EcParSegReg(double x1, double y1, double x2, double y2){

        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    
    }

    static class Punto{
        private double x;
        private double y;

        public Punto(double x, double y){
            this.x = x;
            this.y = y;
        }

        /**
         * @return the x
         */
        public double getX() {
            return x;
        }

        /**
         * @return the y
         */
        public double getY() {
            return y;
        }
    }

    public Punto test (double a, double b, double c, double d, double e, double f) { 
        double y, x;
 
        y = ((f * a) - (d * c)) / ((e * a) - (d * b));
        x = (c - (b * y)) / a;
 
        Punto n = new Punto(x, y);

        return n; 
    }

    /**
     * x1 - x3 = (x3 - x4)u2 - (x2 - x1)u1
     * y1 - y3 = (y3 - y4)u2 - (y2 - y1)u1
     * 100 x1 100 y1
     * 200 x2 100 y2
     * 200 x3 200 y3
     * 150 x4 250 y4
     */

    public void solve(EcParSegReg epsr1,EcParSegReg epsr2){
        double x1 = epsr1.getX1();
        double y1 = epsr1.getY1();
        double x2 = epsr1.getX2();
        double y2 = epsr1.getY2();
        double x3 = epsr2.getX1();
        double y3 = epsr2.getY1();
        double x4 = epsr2.getX2();
        double y4 = epsr2.getY2();
        double u1; 
        double u2;

        Punto n = test(x3 - x4, -(x2 - x1), x1 - x3, y3 - y4, -(y2 - y1), y1 - y3);
        u2 = n.getX();
        u1 = n.getY();

        System.out.println("u1= "+u1);
        System.out.println("u2= "+u2);
    }


    public double getX1(){
        return x1;
    }

    public double getY1(){
        return y1;
    }

    public double getX2(){
        return x2;
    }

    public double getY2(){
        return y2;
    }

 }