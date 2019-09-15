import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class EllipticCurve {
    //An elliptic curve defined at characteristic p. q: order of the curve
    //equation: y^2 = (x^3 + ax + b) mod p
    private int a,b,p,q;
    private int privateKey;
    private Point basePoint,publicKey;
    public EllipticCurve(int a, int b,int p){
        this.a = a;
        this.b = b;
        this.p = p;
        this.q = p;
    }

    public double derivativeAt(Point x){
        return (3*x.getX()*x.getX() + a)/(2*x.getY());
    }

    public Signature sign(String mes) throws NoSuchAlgorithmException {
        int k = random(this.q);
        Point T = Operation.multiply(k,this.basePoint,this);
        long r = (long) (Math.abs(T.getX()) % this.q);
        if(r==0){
            return sign(mes);
        }
        else {
            BigInteger e = hash(mes);
            BigInteger num1 = e.add(BigInteger.valueOf(privateKey*r));
            BigInteger num2 = num1.divide(BigInteger.valueOf(k));
            BigInteger s = num2.mod(BigInteger.valueOf(q));
            if(s.compareTo(BigInteger.valueOf(0)) ==0){
                return sign(mes);
            }
            else {
                return new Signature(r,s.longValue());
            }
        }
    }

    public boolean verify(String mes, Signature signature) throws NoSuchAlgorithmException {
        BigInteger r = BigInteger.valueOf(signature.getR());
        BigInteger s = BigInteger.valueOf(signature.getS());
        long longr = signature.getR();
        long longs = signature.getS();
        if( longr < 1 || longr > (q-1) || longs < 1 || longs > (q-1)){
            return false;
        }
        else{
            BigInteger e = hash(mes);
            long u1 = ( e.divide(s) ).mod(BigInteger.valueOf(q)).longValue();
            long u2 = ( r.divide(s) ).mod(BigInteger.valueOf(q)).longValue();
            Point point1 = Operation.multiply(u1,basePoint,this);
            Point point2 = Operation.multiply(u2,basePoint,this);
            Point T = Operation.add(point1,point2,this);
            return longr == (T.getX() % q);
        }
    }

    public void prepare(int x, Point G, Point Y){
        this.privateKey = x;
        this.basePoint = G;
        this.publicKey = Y;
    }

    public void generateKey(){
        Point base = new Point(2,Math.abs(Math.sqrt(2*2*2 + a*2+b)));
        int privateKey = random(q);
        Point publicKey = Operation.multiply(privateKey,base,this);
        this.prepare(privateKey,base,publicKey);
    }

    private int random(int q){
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis());
        return rand.nextInt((int)q-1)+1;
    }

    private BigInteger hash(String message) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(message.getBytes(StandardCharsets.UTF_8));
        return bytesToInt(encodedhash);
    }

    private static BigInteger bytesToInt(byte[] hash){
        StringBuffer intString = new StringBuffer();
        BigInteger bigInteger = new BigInteger(1,hash);
        return  bigInteger;
    }

    public void printEC(){
        System.out.println("Equation: y^2 = x^3 + " + a + "*x" + " + " + b + "(mod " + q + ")");
        System.out.println("Public Key: " + publicKey);
        System.out.println("Private Key: " + privateKey);
        System.out.println("Base Point: "+ basePoint);
    }

}
