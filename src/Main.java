import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        EllipticCurve EC = new EllipticCurve(-3,5,125);
        Point P = new Point(2,2.65);
        EC.generateKey();

        EC.printEC();



        Signature signature = EC.sign("aad515177");
        System.out.println(signature);
        System.out.println(EC.verify("aad515177",signature));
    }
}
