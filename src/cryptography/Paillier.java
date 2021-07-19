package cryptography;

import java.math.BigInteger;
import java.util.Scanner;

public class Paillier {

    public void Paillier() {
        Scanner scanner = new Scanner(System.in);

        BigInteger P = null, Q = null, PQ = null, PQ1, G, λ, P1, Q1, LU, µ, K, R, M, C, DM = null, DC;
        BigInteger GCD;

        boolean loop = true;
        boolean again = true;
        while (again) {
            while (loop == true) {
                System.out.println();
                loop = false;
                System.out.print("Please enter a Prime Number P: ");
                P = scanner.nextBigInteger();
                System.out.println("You chose P value as: " + P + "\n");

                System.out.print("Please enter a Prime Number Q: ");
                Q = scanner.nextBigInteger();
                System.out.println("You chose Q value as: " + Q + "\n");

                PQ = P.multiply(Q);
                PQ1 = P.subtract(BigInteger.ONE).multiply(Q.subtract(BigInteger.ONE));
                GCD = PQ.gcd(PQ1);

                System.out.println("GCD(" + PQ + ", " + PQ1 + ") = " + GCD + "\n");

                if (!GCD.equals(BigInteger.ONE)) {
                    System.out.println("Please Enter P and Q values again as GCD does not equal 1");
                    loop = true;
                }
            }
            System.out.print("Please enter an Integer G: ");
            G = scanner.nextBigInteger();
            System.out.println("You chose G value as: " + G + "\n");

            System.out.println("Public Key Values: (N = " + PQ + ", G = " + G + ")\n");

            P1 = P.subtract(BigInteger.ONE);
            Q1 = Q.subtract(BigInteger.ONE);
            λ = lcm(P1, Q1);


            LU = (G.pow(λ.intValue())).mod(PQ.pow(2));
            K = (LU.subtract(BigInteger.valueOf(1))).divide(PQ);

            System.out.println("The calculated K value is: " + K);
            System.out.println("K value will now used to calculate another private key parameter µ\n");

            µ = K.modInverse(PQ);

            System.out.println("Private Key Parameter: (λ = " + λ + ", " + "µ = " + µ + ")\n");

            System.out.print("Please enter a Random Number R: ");
            R = scanner.nextBigInteger();
            System.out.println("You chose R value as: " + R + "\n");

            System.out.print("Please enter a number message to encrypt: ");
            M = scanner.nextBigInteger();
            System.out.println("You chose Message(M) as: " + M + "\n");

            C = Encrypt(G, M, R, PQ);

            System.out.println("The encrypted message is C = " + C + "\n");

            System.out.print("Please enter Ciphertext C to decrypt: ");
            DC = scanner.nextBigInteger();

            DM = Decrypt(DC, λ, PQ, µ);

            System.out.println("The decrypted message is: M = " + DM + "\n");

            System.out.print("Do another Paillier Calculation: Yes, No (Go Back) or exit: ");
            Scanner another = new Scanner(System.in);
            if (another.hasNext("no")) {
                again = false;
                main.start();
            } else if (another.hasNext("yes")) {
                again = true;
                loop = true;
            } else if (another.hasNext("exit")) {
                System.out.println("");
                System.out.println("Quitting...");
                System.out.println("Thanks for using this Cryptography Software");
                System.exit(0);
            }
        }
    }

    public static BigInteger Encrypt(BigInteger G, BigInteger M, BigInteger R, BigInteger N) {

        BigInteger C;

        C = ((G.pow(M.intValue())).multiply(R.pow(N.intValue()))).mod(N.pow(2));

        return C;
    }

    public static BigInteger Decrypt(BigInteger C, BigInteger LMBDA, BigInteger N, BigInteger Mule) {

        BigInteger DM;
        BigInteger DM1;
        BigInteger U;
        BigInteger LU;

        U = (C.pow(LMBDA.intValue())).mod(N.pow(2));
        LU = (U.subtract(BigInteger.ONE)).divide(N);
        DM1 = (LU.multiply(Mule));
        DM = DM1.mod(N);

        return DM;
    }


    // Source of Code: https://www.geeksforgeeks.org/lcm-of-two-large-numbers/
    // function to calculate LCM of two large numbers
    public static BigInteger lcm(BigInteger a, BigInteger b) {
//        // convert string 'a' and 'b' into BigInteger
//        BigInteger s = new BigInteger(a);
//        BigInteger s1 = new BigInteger(b);

        // calculate multiplication of two bigintegers
        BigInteger mul = a.multiply(b);

        // calculate gcd of two bigintegers
        BigInteger gcd = a.gcd(b);

        // calculate lcm using formula: lcm * gcd = x * y
        BigInteger lcm = mul.divide(gcd);
        return lcm;
    }


}
