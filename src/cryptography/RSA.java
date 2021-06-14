package cryptography;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

public class RSA {
    public static void main(String[] args) {
        PrintStream show = System.out;
        Scanner scanner = new Scanner(System.in);

        BigInteger P;
        BigInteger Q;
        BigInteger N;
        BigInteger M;

        System.out.print("Please enter a P value: ");
        P = scanner.nextBigInteger();
        System.out.println("Your chose P value as: " + P);

        System.out.print("Please enter a Q value: ");
        Q = scanner.nextBigInteger();
        System.out.println("Your chose Q value as: " + Q);

        System.out.print("Your N Value is: ");
//        N = P * Q;
        N = P.multiply(Q);
        System.out.println(N);

        BigInteger PN;
//        PN = (P-1)*(Q-1);
        PN = (P.subtract(BigInteger.valueOf(1))).multiply(Q.subtract(BigInteger.valueOf(1)));
        System.out.println("Your Phi n value is: "+PN);


        System.out.print("Do you want to enter E value or Have it Generated, Yes or No: ");
        String userInput;
        userInput = scanner.nextLine();
        if (scanner.nextLine().toLowerCase().contentEquals("yes") || userInput.toLowerCase().contentEquals("yes") || userInput.equals("yes")){
            BigInteger GCD = new BigInteger(String.valueOf(BigInteger.valueOf(0)));
            BigInteger E;
            while (!GCD.equals(BigInteger.valueOf(1))) {
                System.out.print("Please enter a E value: ");
                E = scanner.nextBigInteger();
                GCD = gcdByEuclidsAlgorithm(E, P.subtract(BigInteger.valueOf(1)));

                System.out.print("GCD(" + E + "," + N + ") = " + GCD);
                if (!GCD.equals(BigInteger.valueOf(1)))
                    System.out.println(" - This GCD(e,"+N+") does not = 1, So another E value is selected");
                else{
                    System.out.println("\nYour E Value is: " + E);

                    System.out.println("Public Key: (N="+N+", "+"E="+ E+")");

                    BigInteger D;

                    D = modInverse(E, PN);  //Use this when not using BigInteger or modInverse Method returns BigInteger
//                    D = BigInteger.valueOf((modInverse(E, PN))); //Use this when using BigIntegers or if modInverse method returns int

                    System.out.println("Private key is d = e^-1 mod phi(n) = " + E + "^-1 mod " + PN);
                    System.out.println("Private Key: D = " + D);

                    show.print("Please enter a Message to encrypt: ");
                    M = scanner.nextBigInteger();

                    BigInteger ciphertext;
                    ciphertext = modularArithmetic(M, E, N);

                    show.println("C = "+ciphertext);

                    show.println("Please Enter a Value to Decrypt: ");
                    BigInteger C;
                    C = scanner.nextBigInteger();
                    M = modularArithmetic(C, D, N);
                    show.println(M);
                }
            }
        } else if (scanner.nextLine().toLowerCase().contentEquals("no") || userInput.toLowerCase().contentEquals("no") || userInput.equals("no")) {
            BigInteger GCD = new BigInteger(String.valueOf(BigInteger.valueOf(0)));
//            int E = 2;
            BigInteger E = new BigInteger(String.valueOf(2));
            while (!GCD.equals(BigInteger.valueOf(1))) {
                E = E.add(BigInteger.valueOf(1));
                GCD = gcdByEuclidsAlgorithm(E, P.subtract(BigInteger.valueOf(1)));

                System.out.print("GCD(" + E + "," + N + ") = " + GCD);
                if (!GCD.equals(BigInteger.valueOf(1)))
                    System.out.println(" - This GCD(e," + N + ") does not = 1, So another E value is selected");
                else {
                    System.out.println("\nYour E Value is: " + E);

                    // Testing Method For Integer Value is Too Big, so change it into String ""
//                    BigInteger x;
//                    BigInteger F = BigInteger.valueOf(3919);
//                    BigInteger G = new BigInteger("4599650820"); -- Here Big value into string, now it works
//                    x = F.modInverse(G);
//                    show.println(x);

                    System.out.println("Public Key: (N="+N+", "+"E="+ E+")");

                    BigInteger D;

                    D = modInverse(E, PN);  //-- Use this when not using BigInteger or modInverse Method returns BigInteger
//                    D = BigInteger.valueOf(modInverse(E, PN)); //Use this when using BigIntegers or if modInverse method returns int

                    System.out.println("Private key is d = e^-1 mod phi(n) = " + E + "^-1 mod " + PN);
                    System.out.println("Private Key: D = " + D);

                    show.print("Please enter a Message to encrypt: ");
                    M = scanner.nextBigInteger();

                    BigInteger ciphertext;
                    ciphertext = modularArithmetic(M, E, N);

                    show.println("C = "+ciphertext);

                    show.println("Please Enter a Value to Decrypt: ");
                    BigInteger C;
                    C = scanner.nextBigInteger();

                    M = modularArithmetic(C, D, N);
                    show.println(M);
                }
            }
        } else {
            show.println("Error");
        }

    }

    static BigInteger modularArithmetic(BigInteger c, BigInteger d, BigInteger n){
        BigInteger c1 = new BigInteger(String.valueOf(c));
        BigInteger n1 = new BigInteger(String.valueOf(n));
        BigInteger power = c1.pow(d.intValue());
        BigInteger y;
        y = power.mod(n1);
        return y;
    }

    // https://www.baeldung.com/java-greatest-common-divisor
    static BigInteger gcdByEuclidsAlgorithm(BigInteger n1, BigInteger n2) {
        if (n2.equals(BigInteger.valueOf(0))) {
            return n1;
        }

        return gcdByEuclidsAlgorithm(n2, n1.mod(n2));
    }

    // https://tutorialspoint.dev/algorithm/mathematical-algorithms/multiplicative-inverse-under-modulo-m
    static BigInteger modInverse(BigInteger a, BigInteger m)
    {
        BigInteger x;
        new BigInteger(String.valueOf(m));
        x = a.modInverse(m);
        return x;

       // Below is the method for modInverse when using normal integers and not BigInteger
//       try {
//            a = a % m;
//            for (int x = 1; x < m; x++)
//                if ((a * x) % m == 1)
//                    return x;
//            return 1;
//        } catch (Exception e){
//            System.out.println("ValueError: base is not invertible for the given modulus");
//        }
//        return 1;

        // This was for converting things to BigInteger inorder for Inverse Mod to work (Didn't Give correct result)

//        BigInteger x = new BigInteger(String.valueOf(BigInteger.valueOf(0)));
//        a = a.mod(m);
//        for (x.equals(1); x.compareTo(BigInteger.valueOf(m.intValue())) < m.intValue(); x.add(BigInteger.valueOf(1)))
//            if ((a.multiply(x)).mod(m).equals(1))
//                return x;
//        return x;


//        https://stackoverflow.com/questions/5171002/rsa-calculating-cd-mod-n
//        How InverseMod or powMod Works:
//        The "powMod" operation can be taken down into smaller step.
//        For example 5 ^ 3 % 6 is equal ((5 * 5) % 6) * 5 % 6 and 5 ^ 4 % 6 is equal to (((5 * 5) % 6) * 5 % 6) * 5 % 6)
    }
}











