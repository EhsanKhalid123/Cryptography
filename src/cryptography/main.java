package cryptography;

import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        start();
    }

    public static void start(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please select which Cryptography method you would like to use:\n1. RSA\n2. ElGamal\n3. Paillier\nEnter 1, 2 or 3: ");
        int input = scanner.nextInt();

        if (input == 1) {
            RSA rsa = new RSA();
            rsa.RSACalculation();
        } else if (input == 2) {
            ElGamal elgmal = new ElGamal();
            elgmal.ElGamal();
        } else if (input == 3) {
            Paillier paillier = new Paillier();
            paillier.Paillier();
        }
    }
}
