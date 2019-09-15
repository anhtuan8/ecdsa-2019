public class Point {
        private double x;
        private double y;

        public Point(double x, double y){
            this.x = x;
            this.y = y;
        }

        public Point(){

        }

        public String toString(){
            return "("+ String.valueOf(x) +"," + String.valueOf(y) + ")" ;
        }

        public Point copy(Point p){
            return new Point(p.getX(),p.getY());
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public void setX(double x) {
            this.x = x;
        }

        public void setY(double y) {
            this.y = y;
        }
}
