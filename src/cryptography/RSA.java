package cryptography;

import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Scanner;

public class RSA {
    public void RSACalculation() {

        PrintStream show = System.out; // Defining show to System.out to make it easier to print
        Scanner scanner = new Scanner(System.in); // Scanner Object to get user input


        boolean again = true;
        while (again) {

            // Defined BigInteger Variables to save user input to
            BigInteger P;
            BigInteger Q;
            BigInteger N;
            BigInteger M;
            String userInput;

            System.out.println();

            System.out.print("Please enter a P value (Make sure its Prime Number): ");
            P = scanner.nextBigInteger();

            System.out.println("You chose P value as: " + P + "\n");

            System.out.print("Please enter a Q value (Make sure its Prime Number): ");
            Q = scanner.nextBigInteger();
            System.out.println("You chose Q value as: " + Q + "\n");

            System.out.print("Your N Value is: ");
            N = P.multiply(Q);
            System.out.println(N + "\n");

            BigInteger PN;
            PN = (P.subtract(BigInteger.valueOf(1))).multiply(Q.subtract(BigInteger.valueOf(1)));
            System.out.println("Your Phi n value is: " + PN + "\n");


            System.out.print("Enter an E value? (Yes) or Have it Generated (No), Yes or No: ");
            userInput = scanner.next();
            if (userInput.toLowerCase().contentEquals("yes")) {
                BigInteger GCD = new BigInteger(String.valueOf(BigInteger.valueOf(0)));
                BigInteger E;
                while (!GCD.equals(BigInteger.valueOf(1))) {
                    System.out.print("Please enter a E value: ");
                    E = scanner.nextBigInteger();
                    GCD = gcdByEuclidsAlgorithm(E, P.subtract(BigInteger.valueOf(1)));

                    System.out.print("\n" + "GCD(" + E + "," + N + ") = " + GCD);
                    if (!GCD.equals(BigInteger.valueOf(1)))
                        System.out.println(" - This GCD(" + E + ", " + N + ") does not = 1, So another E value is selected");
                    else {
                        System.out.println("\nYour E Value is: " + E + "\n");

                        System.out.println("Public Key: (N=" + N + ", " + "E=" + E + ")");

                        BigInteger D;

                        D = modInverse(E, PN);  //Use this when not using BigInteger or modInverse Method returns BigInteger
//                    D = BigInteger.valueOf((modInverse(E, PN))); //Use this when using BigIntegers or if modInverse method returns int

//                    System.out.println("Private key is d = e^-1 mod phi(n) = " + E + "^-1 mod " + PN);
                        System.out.println("Private Key: D = " + D + "\n");
                        System.out.println("Known Parameters: " + "P = " + P + ", " + " Q = " + Q + ", " + " N = " + N + ", " + " E = " + E + ", " + " ϕn = " + PN + ", " + " D = " + D + "\n");
                        show.print("Please enter a Message to encrypt: ");
                        M = scanner.nextBigInteger();

                        BigInteger ciphertext;
                        ciphertext = modularArithmetic(M, E, N);

                        show.println("Encrypted Message (Ciphertext C) is C = " + ciphertext + "\n");

                        show.print("Please Enter a Value to Decrypt: ");
                        BigInteger C;
                        C = scanner.nextBigInteger();
                        M = modularArithmetic(C, D, N);
                        show.println("The Decrypted Message(M) is M = " + M + "\n");
                    }
                }
            } else if (userInput.toLowerCase().contentEquals("no")) {
                BigInteger GCD = new BigInteger(String.valueOf(BigInteger.valueOf(0)));
//            int E = 2;
                BigInteger E = new BigInteger(String.valueOf(2));
                while (!GCD.equals(BigInteger.valueOf(1))) {
                    E = E.add(BigInteger.valueOf(1));
                    GCD = gcdByEuclidsAlgorithm(E, P.subtract(BigInteger.valueOf(1)));

                    System.out.print("GCD(" + E + "," + N + ") = " + GCD);
                    if (!GCD.equals(BigInteger.valueOf(1)))
                        System.out.println(" - This GCD(" + E + ", " + N + ") does not = 1, So another E value is selected");
                    else {
                        System.out.println("\nYour E Value is: " + E + "\n");

                        // Testing Method For Integer Value is Too Big, so change it into String ""
//                    BigInteger x;
//                    BigInteger F = BigInteger.valueOf(3919);
//                    BigInteger G = new BigInteger("4599650820"); -- Here Big value into string, now it works
//                    x = F.modInverse(G);
//                    show.println(x);

                        System.out.println("Public Key: (N=" + N + ", " + "E=" + E + ")");

                        BigInteger D;

                        D = modInverse(E, PN);  //-- Use this when not using BigInteger or modInverse Method returns BigInteger
//                    D = BigInteger.valueOf(modInverse(E, PN)); //Use this when using BigIntegers or if modInverse method returns int

//                    System.out.println("Private key is d = e^-1 mod phi(n) = " + E + "^-1 mod " + PN);
                        System.out.println("Private Key: D = " + D + "\n");
                        System.out.println("Known Parameters: " + "P = " + P + ", " + " Q = " + Q + ", " + " N = " + N + ", " + " E = " + E + ", " + " ϕn = " + PN + ", " + " D = " + D + "\n");

                        show.print("Please enter a Message to encrypt: ");
                        M = scanner.nextBigInteger();

                        BigInteger ciphertext;
                        ciphertext = modularArithmetic(M, E, N);

                        show.println("Encrypted Message (Ciphertext C) is C = " + ciphertext + "\n");

                        show.print("Please Enter a Value to Decrypt: ");
                        BigInteger C;
                        C = scanner.nextBigInteger();

                        M = modularArithmetic(C, D, N);
                        show.println("The Decrypted Message(M) is M = " + M + "\n");
                    }
                }
            } else {
                show.println("You can only enter, Yes or No");
            }

            show.print("Do another RSA Calculation: Yes, No (Go Back), exit: ");
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

    static BigInteger modularArithmetic(BigInteger c, BigInteger d, BigInteger n) {
        BigInteger c1 = new BigInteger(String.valueOf(c));
        BigInteger n1 = new BigInteger(String.valueOf(n));
        // Below Can work fine, but there is no point storing answer in variable and then returning the variable
        // When you can directly return result without storing it.
//        BigInteger power = c1.modPow(d,n1);
//        BigInteger y;
//        y = power.mod(n1);
//        return power;
        return c1.modPow(d, n1);
    }

//    static BigInteger pow(BigInteger base, BigInteger exponent) {
//        BigInteger result = BigInteger.ONE;
//        while (exponent.signum() > 0) {
//            if (exponent.testBit(0)) result = result.multiply(base);
//            base = base.multiply(base);
//            exponent = exponent.shiftRight(1);
//        }
//        return result;
//    }

    // https://www.baeldung.com/java-greatest-common-divisor
    static BigInteger gcdByEuclidsAlgorithm(BigInteger n1, BigInteger n2) {
        if (n2.equals(BigInteger.valueOf(0))) {
            return n1;
        }

        return gcdByEuclidsAlgorithm(n2, n1.mod(n2));
    }

    // https://tutorialspoint.dev/algorithm/mathematical-algorithms/multiplicative-inverse-under-modulo-m
    static BigInteger modInverse(BigInteger a, BigInteger m) {
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











