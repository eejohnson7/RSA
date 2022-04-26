import java.util.Arrays;

public class RSA {
    public static boolean ifPrime(double x){
        for (double i = 2; i <= x / 2; i++) {
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static int thisPrime(int x, int count, int target){
        for(int i = x; i < 10000; i++){
            if(ifPrime(i)){
                count++;
                if(count == target){
                    return i;
                }
            }
        }
        return -1;
    }

    public static int findE(int product){
        for(int i = 2; i < product; i++){
            if(product%i == 0){
                return i;
            }
        }
        return -1;
    }

    public static int[] toValues(String msg){
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        msg = msg.toUpperCase();
        int newMsg[] = new int[msg.length()];

        for(int i = 0; i < msg.length(); i++){
            newMsg[i] = letters.indexOf(msg.charAt(i));
        }

        return newMsg;
    }

    public static String toLetters(int[] msg){
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String p = "";
        
        for(int i = 0; i < msg.length; i++){
            if(msg[i] > 25){
                msg[i] = msg[i]%25;
            }
            for(int j = 0; j < 26; j++){
                if(j == msg[i]){
                    p += letters.charAt(j);
                    break;
                }
            }
        }
        return p.toLowerCase();
    }

    public static double[] encipher(String msg, int[] publicKey){
        System.out.println();
        System.out.println("Enciphering \"" + msg + "\"...");

        int[] msgVals = toValues(msg);
        int n = publicKey[0];
        int e = publicKey[1];
        double c[] = new double[msgVals.length];

        for(int i = 0; i < msgVals.length; i++){
            double x = Math.pow(msgVals[i], e);
            System.out.println(msgVals[i] + "^" + e + " = " + x);
            c[i] = x%n;
        }
        System.out.println("C: " + Arrays.toString(c));
        return c;
    }

    public static void decipher(double[] cipherText, int[] privateKey){
        System.out.println();
        System.out.println("Deciphering " + Arrays.toString(cipherText) + "...");
        
        int n = privateKey[0];
        int d = privateKey[1];
        int p[] = new int[cipherText.length];

        for(int i = 0; i < cipherText.length; i++){
            double x = Math.pow(cipherText[i], d);
            System.out.println(cipherText[i] + "^" + d + " = " + x);
            p[i] = (int)(x%n);
        }
        System.out.println("P: " + Arrays.toString(p));
        System.out.println();
        System.out.println("Message Received: " + toLetters(p));
    }
    public static void main(String[] args) {
        int count = 0;
        int p = thisPrime(1000, count, 10);
        int q = thisPrime(p+1, 10, 19);

        int n = p * q;
        int m = (p - 1) * (q - 1);
        System.out.println("m = " + m);
        
        int product = 0;
        for(int i = m + 2; i < 2147483647; i++){
            if(i%m == 1 && !ifPrime(i)){
                product = i;
                break;
            }
        }
        System.out.println("e * d = " + product);

        int e = findE(product);
        int d = product/e;
        
        int publicKey[] = new int[]{n, e};
        int privateKey[] = new int[]{n, d};

        double cipherText[] = encipher("rsa", publicKey);
        decipher(cipherText, privateKey);
        
    }
}
