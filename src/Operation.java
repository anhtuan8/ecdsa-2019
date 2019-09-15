public class Operation {
    public static Point multiply(long n,Point a, EllipticCurve ec){
        Point result = new Point();
        for(int i=0; i<n-1 ; i++){
            double m = ec.derivativeAt(a);
            double x1 = a.getX(),y1 = a.getY();
            double newX = m*m - 2*x1;
            double newY = m*(x1-newX) - y1;
            result.setX(newX);
            result.setY(newY);
            a.copy(result);
        }
        return result;
    }

    public static Point add(Point a, Point b, EllipticCurve ec){
        double x1 = a.getX();
        double x2 = b.getX();
        double y1 = a.getY();
        double y2 = b.getY();

        double m = (y2-y1) / (x2-x1);

        double x3 = m*m - x1 - x2;
        double y3 = m*(x1-x2) - y1;

        return new Point(x3,y3);
    }

//    public static Point add(Point a, Point b, EllipticCurve ec){
//        Point result = new Point();
//        for()
//    }
}
