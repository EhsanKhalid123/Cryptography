package cryptography;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        start();
    }

    public static void start() {
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;

        while (loop == true) {

            System.out.println("");
            System.out.print("Please enter an number to choose a Cryptography you would like to use:\n1. RSA\n2. ElGamal\n3. Paillier\n4. Exit\nEnter 1, 2, 3 or 4: ");

            try {
                int input = scanner.nextInt();
                if (input == 1) {
                    RSA rsa = new RSA();
                    rsa.RSACalculation();
                    loop = false;
                } else if (input == 2) {
                    ElGamal elgmal = new ElGamal();
                    elgmal.ElGamal();
                    loop = false;
                } else if (input == 3) {
                    Paillier paillier = new Paillier();
                    paillier.Paillier();
                    loop = false;
                } else if (input == 4) {
                    loop = false;
                    System.out.println("");
                    System.out.println("Quitting...");
                    System.out.println("Thanks for using this Cryptography Software");
                    System.exit(0);
                } else {
                    System.out.println("");
                    System.out.println("You can only choose numbers 1 - 4, Please try again!");
                    loop = true;
                }
            } catch (Exception e) {
                System.out.println();
                System.out.println("Error, The values selected cannot be calculated!");
                System.out.println("Error, You can only enter Number Values!");
                continue;
            }
        }
    }
}