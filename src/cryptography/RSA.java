package cryptography;//

import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Scanner;

public class RSA {

//    https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public void RSACalculation() {

        PrintStream show = System.out; // Defining show to System.out to make it easier to print
        Scanner scanner = new Scanner(System.in); // Scanner Object to get user input

        boolean again = true;
        while (again) {

            boolean loop = true;
            boolean loop2 = true;

            BigInteger P, Q, N, PN;
            String userInput;
            System.out.println();

            P = Validator2("Please enter a Prime Number P: ");
            System.out.println("You chose P value as: " + P + "\n");

            Q = Validator2("Please enter a Prime Number Q: ");
            System.out.println("You chose Q value as: " + Q + "\n");

            N = P.multiply(Q);
            System.out.print("Your N Value is: " + N + "\n");

            PN = (P.subtract(BigInteger.valueOf(1))).multiply(Q.subtract(BigInteger.valueOf(1)));
            System.out.println("Your Phi n value is: " + PN + "\n");

            while (loop2) {
                System.out.print("Enter an E value? (Yes) or Have it Generated (No), Yes or No: ");
                userInput = scanner.next();

                if (userInput.toLowerCase().contentEquals("yes")) {
                    loop2 = false;
                    BigInteger GCD = new BigInteger(String.valueOf(0)), E;

                    while (!GCD.equals(BigInteger.valueOf(1))) {
                        System.out.println();
                        E = Validator("Please enter a E value: ");
                        GCD = gcdByEuclidsAlgorithm(E, PN);
                        Encryption_Decryption(E, N, PN, GCD, P, Q);
                    }
                } else if (userInput.toLowerCase().contentEquals("no")) {
                    loop2 = false;
                    BigInteger GCD = new BigInteger(String.valueOf(0)), E = new BigInteger(String.valueOf(2));
                    System.out.println();

                    while (!GCD.equals(BigInteger.valueOf(1))) {
                        E = E.add(BigInteger.valueOf(1));
                        GCD = gcdByEuclidsAlgorithm(E, PN);
                        Encryption_Decryption(E, N, PN, GCD, P, Q);
                    }
                } else {
                    show.println(ANSI_RED + "You can only enter, Yes or No!\n" + ANSI_RESET);
                    loop2 = true;
                }
            }

            while (loop) {
                show.print("Do another RSA Calculation: Yes, No (Go Back), Exit: ");
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

    static void Encryption_Decryption(BigInteger E, BigInteger N, BigInteger PN, BigInteger GCD, BigInteger P, BigInteger Q) {

        BigInteger D = BigInteger.ZERO, C, ciphertext, M;

        System.out.print("GCD(" + E + "," + N + ") = " + GCD);
        if (!GCD.equals(BigInteger.valueOf(1)))
            System.out.println(" - This GCD(" + E + ", " + PN + ") does not = 1, So another E value is selected");
        else {
            System.out.println();
            System.out.println("\nYour E Value is: " + E + "\n");
            System.out.println("Public Key: (N=" + N + ", " + "E=" + E + ")\n");
            try {
                D = modInverse(E, PN);
            } catch (Exception e) {
                System.out.println(ANSI_RED + "Error: Base is not invertible for entered E value! Please enter a different E value! This is due to the way\nRSA method was designed by its founders, the current entered E value doesn't work!" + ANSI_RED + ANSI_CYAN + " Please Try Again!" + ANSI_RESET + "\n");
            }

            System.out.println("Private Key: D = " + D + "\n");
            System.out.println("Known Parameters: " + "P = " + P + ", " + " Q = " + Q + ", " + " N = " + N + ", " + " E = " + E + ", " + " ϕn = " + PN + ", " + " D = " + D + "\n");

            M = Validator("Please enter a Message to encrypt: ");
            ciphertext = modularArithmetic(M, E, N);

            System.out.println("Encrypted Message (Ciphertext C) is C = " + ciphertext + "\n");

            C = Validator("Please Enter a Value to Decrypt: ");
            M = modularArithmetic(C, D, N);
            System.out.println("The Decrypted Message(M) is M = " + M + "\n");
        }
    }

    static BigInteger modularArithmetic(BigInteger c, BigInteger d, BigInteger n) {
        BigInteger c1 = new BigInteger(String.valueOf(c));
        BigInteger n1 = new BigInteger(String.valueOf(n));
        return c1.modPow(d, n1);
    }

    // https://www.baeldung.com/java-greatest-common-divisor
    static BigInteger gcdByEuclidsAlgorithm(BigInteger n1, BigInteger n2) {
        if (n2.equals(BigInteger.valueOf(0))) {
            return n1;
        }
        return gcdByEuclidsAlgorithm(n2, n1.mod(n2));
    }

    static BigInteger modInverse(BigInteger a, BigInteger m) {
        BigInteger x;
        new BigInteger(String.valueOf(m));
        x = a.modInverse(m);
        return x;
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

//        Note to self for understanding purposes:

//        https://tutorialspoint.dev/algorithm/mathematical-algorithms/multiplicative-inverse-under-modulo-m
//        Below is the method for modInverse when using normal integers and not BigInteger
//        a = a % m;
//        for (int x = 1; x < m; x++)
//            if ((a * x) % m == 1)
//                return x;
//        return 1;

//        https://stackoverflow.com/questions/5171002/rsa-calculating-cd-mod-n
//        How InverseMod or powMod Works:
//        The "powMod" operation can be taken down into smaller step.
//        For example 5 ^ 3 % 6 is equal ((5 * 5) % 6) * 5 % 6 and 5 ^ 4 % 6 is equal to (((5 * 5) % 6) * 5 % 6) * 5 % 6)

//        Testing Method For When Integer Value is Too Big, so change it into String "", Use Strings when error message value is too large:
//        BigInteger x;
//        BigInteger F = BigInteger.valueOf(3919);
//        BigInteger G = new BigInteger("4599650820"); -- Here Big value into string, now it works
//        x = F.modInverse(G);
//        System.out.println(x);










