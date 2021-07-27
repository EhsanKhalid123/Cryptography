package cryptography;

import java.math.BigInteger;
import java.util.Scanner;

@SuppressWarnings("NonAsciiCharacters")

public class Paillier {

//    https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public void PaillierCalculation() {

        BigInteger P = null, Q = null, PQ = null, PQ1, G, λ, P1, Q1, LU, µ, K, R, M, C, DM, DC, GCD;



        boolean again = true;
        while (again) {

            boolean loop = true;
            boolean loop2 = true;

            while (loop) {
                System.out.println();
                loop = false;

                P = Validator2("Please enter a Prime Number P: ");
                System.out.println("You chose P value as: " + P + "\n");

                Q = Validator2("Please enter a Prime Number Q: ");
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

            G = Validator("Please enter an Integer G (order of g must be a multiple of n): ");
            // G = numberHere(eg: 5652) such that the order of g is a multiple of n. Concept: order of a group, search this for more info
            System.out.println("You chose G value as: " + G + "\n");

            System.out.println("Public Key Values: (N = " + PQ + ", G = " + G + ")\n");

            P1 = P.subtract(BigInteger.ONE);
            Q1 = Q.subtract(BigInteger.ONE);
            λ = lcm(P1, Q1);

            LU = (G.pow(λ.intValue())).mod(PQ.pow(2));
            K = (LU.subtract(BigInteger.valueOf(1))).divide(PQ);

            System.out.println("The calculated K value is: " + K);
            System.out.println("K value will now used to calculate another private key parameter µ\n");

            try {
                µ = K.modInverse(PQ);
            } catch (Exception e) {
                System.out.println(ANSI_RED + "Error: Please enter different G value as the order of g must be a multiple of n or change P or Q values! Due to the\nway the Paillier method was designed by its founders the current entered values don't work!" + ANSI_CYAN + " Please Try Again!" + ANSI_RESET);
                continue;
            }

            System.out.println("Private Key Parameter: (λ = " + λ + ", " + "µ = " + µ + ")\n");

            R = Validator("Please enter a Random Number R: ");
            System.out.println("You chose R value as: " + R + "\n");

            M = Validator("Please enter a number message to encrypt: ");
            System.out.println("You chose Message(M) as: " + M + "\n");

            C = Encrypt(G, M, R, PQ);

            System.out.println("The encrypted message is C = " + C + "\n");

            DC = Validator("Please enter Ciphertext C to decrypt: ");

            DM = Decrypt(DC, λ, PQ, µ);

            System.out.println("The decrypted message is: M = " + DM + "\n");

            while (loop2) {
                System.out.print("Do another Paillier Calculation: Yes, No (Go Back) or Exit: ");
                Scanner another = new Scanner(System.in);

                String another1 = another.next();
                if (another1.toLowerCase().contentEquals("no")) {
                    again = false;
                    loop2 = false;
                    main.start();
                } else if (another1.toLowerCase().contentEquals("yes")) {
                    loop2 = false;
                } else if (another1.toLowerCase().contentEquals("exit")) {
                    System.out.println();
                    System.out.println("Quitting...");
                    System.out.println("Thanks for using the Cryptography Calculator!");
                    System.exit(0);
                } else {
                    System.out.println(ANSI_RED + "You can only enter Yes, No or Exit!\n" + ANSI_RESET);
                    loop2 = true;
                }
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

        // calculate multiplication of two bigintegers
        BigInteger mul = a.multiply(b);

        // calculate gcd of two bigintegers
        BigInteger gcd = a.gcd(b);

        // calculate lcm using formula: lcm * gcd = x * y
        return mul.divide(gcd);
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
