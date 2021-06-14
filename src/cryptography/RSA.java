package cryptography;

import java.io.PrintStream;
import java.util.Locale;
import java.util.Scanner;

public class RSA {
    public static void main(String[] args) {
        PrintStream show = System.out;
        Scanner scanner = new Scanner(System.in);

        int P;
        int Q;
        int N;
        int M;

        System.out.print("Please enter a P value: ");
        P = scanner.nextInt();
        System.out.println("Your chose P value as: " + P);

        System.out.print("Please enter a Q value: ");
        Q = scanner.nextInt();
        System.out.println("Your chose Q value as: " + Q);

        System.out.print("Your N Value is: ");
        N = P * Q;
        System.out.println(N);

        int PN;
        PN = (P-1)*(Q-1);
        System.out.println("Your Phi n value is: "+PN);

        System.out.print("Do you want to enter E value or Have it Generated, Yes or No: ");
        String userInput;
        userInput = scanner.nextLine();
        if (scanner.nextLine().toLowerCase().contentEquals("yes") || userInput.toLowerCase().contentEquals("yes") || userInput.equals("yes")){
            int GCD = 0;
            int E;
            while (GCD != 1) {
                System.out.print("Please enter a E value: ");
                E = scanner.nextInt();
                GCD = gcdByEuclidsAlgorithm(E, P-1);

                System.out.print("GCD(" + E + "," + N + ") = " + GCD);
                if (GCD != 1)
                    System.out.println(" - This GCD(e,"+N+") does not = 1, So another E value is selected");
                else{
                    System.out.println("\nYour E Value is: " + E);

                    System.out.println("Public Key: (N="+N+", "+"E="+ E+")");

                    int D;

                    D = modInverse(E, PN);

                    System.out.println("Private key is d = e^-1 mod phi(n) = " + E + "^-1 mod " + PN);
                    System.out.println("Private Key: D = " + D);

                    show.print("Please enter a Message to encrypt: ");
                    M = scanner.nextInt();

                    int ciphertext;
                    ciphertext = modularArithmetic(M, E, N);

                    show.println("C = "+ciphertext);

                    show.println("Please Enter a Value to Decrypt: ");
                    int C;
                    C = scanner.nextInt();
                    M = modularArithmetic(C, D, N);
                    show.println(M);
                }
            }
        } else if (scanner.nextLine().toLowerCase().contentEquals("no") || userInput.toLowerCase().contentEquals("no") || userInput.equals("no")) {
            int GCD = 0;
            int E = 2;
            while (GCD != 1) {
                E++;
                GCD = gcdByEuclidsAlgorithm(E, N);

                System.out.print("GCD(" + E + "," + N + ") = " + GCD);
                if (GCD != 1)
                    System.out.println(" - This GCD(e," + N + ") does not = 1, So another E value is selected");
            }

            System.out.println("\nYour E Value is: " + E);

            System.out.println("Public Key: (N="+N+", "+"E="+ E+")");

            int D;

            D = modInverse(E, PN);

            System.out.println("Private key is d = e^-1 mod phi(n) = " + E + "^-1 mod " + PN);
            System.out.println("Private Key: D = " + D);

            show.print("Please enter a Message to encrypt: ");
            M = scanner.nextInt();

            int ciphertext;
            ciphertext = modularArithmetic(M, E, N);

            show.println("C = "+ciphertext);

            show.println("Please Enter a Value to Decrypt: ");
            int C;
            C = scanner.nextInt();

            M = modularArithmetic(C, D, N);
            show.println(M);

        } else {
            show.println("Error");
        }

    }

    static int modularArithmetic(int c, int d, int n){
        double power;
        double y;
        power = Math.pow(c, d);
        System.out.println(power);
        y = power % n;
        return (int)y;
    }

    // https://www.baeldung.com/java-greatest-common-divisor
    static int gcdByEuclidsAlgorithm(int n1, int n2) {
        if (n2 == 0) {
            return n1;
        }
        return gcdByEuclidsAlgorithm(n2, n1 % n2);
    }

    // https://tutorialspoint.dev/algorithm/mathematical-algorithms/multiplicative-inverse-under-modulo-m
    static int modInverse(int a, int m)
    {
        try {
            a = a % m;
            for (int x = 1; x < m; x++)
                if ((a * x) % m == 1)
                    return x;
            return 1;
        } catch (Exception e){
            System.out.println("ValueError: base is not invertible for the given modulus");
        }
        return 1;
    }
}











