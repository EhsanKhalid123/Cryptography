package cryptography;

import java.math.BigInteger;
import java.util.Scanner;

public class ElGamal {
    public void ElGamal() {

        Scanner scanner = new Scanner(System.in);
        boolean again = true;

        while (again) {
            BigInteger P, G, X, Y, K, R, M, D, C1, C2, DK, DC1, DC2, KInv, DM;
            final BigInteger One = new BigInteger(String.valueOf(1));

            System.out.println();

            System.out.print("Please enter a Prime Number P: ");
            P = scanner.nextBigInteger();
            System.out.println("You chose P value as: " + P + "\n");

            System.out.print("Please enter a Generator G: ");
            G = scanner.nextBigInteger();
            System.out.println("You chose G value as: " + G + "\n");

            System.out.print("Please enter a Private Key X: ");
            X = scanner.nextBigInteger();
            System.out.println("You chose X value as: " + X + "\n");

            Y = G.modPow(X, P);
            System.out.println("Your Public Key Y value is: " + Y + "\n");

            System.out.println("Public Key: " + "P = " + P + "," + " G = " + G + "," + " Y = " + Y);
            System.out.println("Private Key: " + "X = " + X + "\n");

            System.out.print("Please enter a Random Number R: ");
            R = scanner.nextBigInteger();
            System.out.println("You chose R value as: " + R + "\n");

            K = Y.modPow(R, P);
            System.out.println("Your K value is: " + K + "\n");

            System.out.print("Please enter Message M to Encrypt: ");
            M = scanner.nextBigInteger();
            System.out.println("You chose M value as: " + M + "\n");

            C1 = G.modPow(R, P);
            System.out.println("Your Ciphertext C1 value is: " + C1 + "\n");

            C2 = M.multiply(K).mod(P);
            System.out.println("Your Ciphertext C2 value is: " + C2 + "\n");

            System.out.println("Your Encrypted Message is: (C1 = " + C1 + ", " + "C2 = " + C2 + ")" + "\n");

            System.out.print("Please enter Ciphertext C1 to Decrypt: ");
            DC1 = scanner.nextBigInteger();

            System.out.print("Please enter Ciphertext C2 to Decrypt: ");
            DC2 = scanner.nextBigInteger();

            System.out.println("");

            DK = DC1.modPow(X, P);
            System.out.println("The receiver Uses C1 to find K value using k = c1^x mod p: K = " + DK);

            System.out.println("");

            KInv = DK.modInverse(P);
            System.out.println("The receiver now uses K to calculate K^-1: K^-1 = " + KInv);

            System.out.println("");

            DM = KInv.multiply(DC2).mod(P);
            System.out.println("The receiver now uses C2 and K^-1 to decrypt the Original Message\nThe Decrypted Message(M) is: " + DM + "\n");

            System.out.print("Do another ElGamal Calculation: Yes, No (Go Back) or exit: ");
            Scanner another = new Scanner(System.in);
            if (another.hasNext("no")) {
                again = false;
                main.start();
            } else if (another.hasNext("yes"))
                again = true;
            else if (another.hasNext("exit")) {
                System.out.println("");
                System.out.println("Quitting...");
                System.out.println("Thanks for using this Cryptography Software");
                System.exit(0);
            }
        }
    }
}
