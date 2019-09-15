public class Signature {
    long r;
    long s;
    public Signature(long r, long s){
        this.r = r;
        this.s = s;
    }

    public long getR(){
        return this.r;
    }

    public long getS() {
        return s;
    }

    public String toString(){
        return "(r,s) = (" + r + "," + s + ")";
    }
}
