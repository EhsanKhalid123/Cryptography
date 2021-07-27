package cryptography;

import java.math.BigInteger;
import java.util.Scanner;

public class ElGamal {

//    https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public void ElGamalCalculation() {

        boolean again = true;

        while (again) {

            boolean loop = true;

            BigInteger P, G, X, Y, K, R, M, C1, C2, DK, DC1, DC2, KInv, DM;

            System.out.println();

            P = Validator2("Please enter a Prime Number P: ");
            System.out.println("You chose P value as: " + P + "\n");

            G = Validator("Please enter a Generator G (must be a primitive root to " + P + "): ");
            System.out.println("You chose G value as: " + G + "\n");


            X = Validator("Please enter a Private Key X: ");
            System.out.println("You chose X value as: " + X + "\n");

            Y = G.modPow(X, P);
            System.out.println("Your Public Key Y value is: " + Y + "\n");

            System.out.println("Public Key: " + "P = " + P + "," + " G = " + G + "," + " Y = " + Y);
            System.out.println("Private Key: " + "X = " + X + "\n");

            R = Validator("Please enter a Random Number R: ");
            System.out.println("You chose R value as: " + R + "\n");

            K = Y.modPow(R, P);
            System.out.println("Your K value is: " + K + "\n");

            M = Validator("Please enter Message M to Encrypt: ");
            System.out.println("You chose M value as: " + M + "\n");

            C1 = G.modPow(R, P);
            System.out.println("Your Ciphertext C1 value is: " + C1 + "\n");

            C2 = M.multiply(K).mod(P);
            System.out.println("Your Ciphertext C2 value is: " + C2 + "\n");

            System.out.println("Your Encrypted Message is: (C1 = " + C1 + ", " + "C2 = " + C2 + ")" + "\n");

            DC1 = Validator("Please enter Ciphertext C1 to Decrypt: ");

            DC2 = Validator("Please enter Ciphertext C2 to Decrypt: ");

            System.out.println();

            DK = DC1.modPow(X, P);
            System.out.println("The receiver Uses C1 to find K value using k = c1^x mod p: K = " + DK);

            System.out.println();

            try {
                KInv = DK.modInverse(P);
            } catch (Exception e) {
                System.out.print(ANSI_RED + "Error: Please enter different G, P, R or X values or change one of the values! Due to the way\nthe ElGamal method was designed by its founders the current entered values don't work!\n" + ANSI_RESET);
                System.out.println(ANSI_RED + "BigInteger not Invertible!" + ANSI_CYAN + " Please Try Again!" + ANSI_RESET);
                continue;
            }

            System.out.println("The receiver now uses K to calculate K^-1: K^-1 = " + KInv);

            System.out.println();

            DM = KInv.multiply(DC2).mod(P);

            System.out.println("The receiver now uses C2 and K^-1 to decrypt the Original Message\nThe Decrypted Message(M) is: " + DM + "\n");

            while (loop) {
                System.out.print("Do another ElGamal Calculation: Yes, No (Go Back) or Exit: ");
                Scanner another = new Scanner(System.in);

                String another1 = another.next();
                if (another1.toLowerCase().contentEquals("no")) {
                    again = false;
                    loop = false;
                    main.start();
                } else if (another1.toLowerCase().contentEquals("yes")) {
                    loop = false;
                    again = true;
                } else if (another1.toLowerCase().contentEquals("exit")) {
                    System.out.println();
                    System.out.println("Quitting...");
                    System.out.println("Thanks for using the Cryptography Calculator!");
                    System.exit(0);
                } else {
                    System.out.println(ANSI_RED + "You can only enter Yes, No or Exit!\n" + ANSI_RESET);
                    loop = true;
                }
            }
        }
    }

    public static BigInteger Validator(String context) {

        boolean loop = true;
        Scanner scanner = new Scanner(System.in);
        BigInteger value = null;
        while (loop) {
            System.out.print(context);
            try {
                value = scanner.nextBigInteger();
                loop = false;
            } catch (Exception e) {
                System.out.println(ANSI_RED + "Error, You can only enter Number Values!\n" + ANSI_RESET);
                scanner.next();
                loop = true;
            }
        }
        return value;
    }

    public static BigInteger Validator2(String context) {

        boolean loop = true;
        Scanner scanner = new Scanner(System.in);
        BigInteger value = null;
        while (loop) {
            System.out.print(context);
            try {
                value = scanner.nextBigInteger();
                if (!value.isProbablePrime(1)) {
                    System.out.println(ANSI_RED + value + " is not a prime number!\n" + ANSI_RESET);
                    loop = true;
                } else {
                    loop = false;
                }

            } catch (Exception e) {
                System.out.println(ANSI_RED + "Error, You can only enter Number Values!\n" + ANSI_RESET);
                scanner.next();
                loop = true;
            }
        }
        return value;
    }
}
