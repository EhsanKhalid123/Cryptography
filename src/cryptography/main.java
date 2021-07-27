package cryptography;

import java.util.Scanner;

public class main {

    //    https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_CYAN = "\u001B[36m";

    @SuppressWarnings("MethodNameSameAsClassName")

    public static void main(String[] args) {
        start();
    }

    public static void start() {
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;

        while (loop) {
            System.out.println();
            System.out.println("Welcome to the Cryptography Calculator");
            System.out.println();
            System.out.print("Enter a number to choose a Cryptography method you would like to use:\n1. RSA\n2. ElGamal\n3. Paillier\n4. About/Help\n5. Exit\nEnter 1, 2, 3, 4 or 5: ");

            try {
                int input = scanner.nextInt();
                if (input == 1) {
                    RSA rsa = new RSA();
                    rsa.RSACalculation();
                    loop = false;
                } else if (input == 2) {
                    ElGamal elgamal = new ElGamal();
                    elgamal.ElGamalCalculation();
                    loop = false;
                } else if (input == 3) {
                    Paillier paillier = new Paillier();
                    paillier.PaillierCalculation();
                    loop = false;
                } else if (input == 4) {
                    loop = false;
                    boolean loop2 = true;
                    System.out.println();
                    System.out.println("This is a Cryptography Calculator which provides RSA, Elgamal and Paillier calculations");
                    System.out.println("Each method shows the encryption and decryption processes and calculates all the required values");
                    System.out.println("The Cryptography Calculator can encrypt and decrypt messages!");
                    System.out.println();
                    System.out.println(ANSI_CYAN + "Note: Some entered values may not give the correct result or may crash the program due the specific\nways the Cryptography methods are designed by its founders and if the program crashes or gives a wrong result\nit is not because of this program or a development issue! If the program crashes please restart it Thanks!" + ANSI_RESET);
                    System.out.println();
                    System.out.println("Version: 1.0.0.0");
                    System.out.println("Developer: Ehsan Khalid\nCompany: EK Creations");

                    while (loop2) {
                        System.out.println();
                        System.out.print("Please enter a number:\n1. Go Back\n2. Exit\nEnter 1 or 2: ");
                        try {
                            int back = scanner.nextInt();
                            if (back == 1) {
                                loop = true;
                                loop2 = false;
                            } else if (back == 2) {
                                System.out.println();
                                System.out.println("Quitting...");
                                System.out.println("Thanks for using the Cryptography Calculator");
                                System.exit(0);
                            } else {
                                System.out.println();
                                System.out.println(ANSI_RED + "You can only enter 1 or 2!" + ANSI_RESET);
                            }
                        } catch (Exception e) {
                            System.out.println();
                            System.out.println(ANSI_RED + "Error, You can only enter Number Values!" + ANSI_CYAN + " Please Try Again!" + ANSI_RESET);
                            scanner.next();
                        }
                    }
                } else if (input == 5) {
                    loop = false;
                    System.out.println();
                    System.out.println("Quitting...");
                    System.out.println("Thanks for using the Cryptography Calculator!");
                    System.exit(0);
                } else {
                    System.out.println();
                    System.out.println(ANSI_RED + "You can only choose numbers 1 - 5," + ANSI_CYAN + " Please try again!" + ANSI_RESET);
                    loop = true;
                }
            } catch (Exception e) {
                System.out.println();
                System.out.println(ANSI_RED + "Error, You can only enter Number Values!" + ANSI_RESET);
                scanner.next();
            }
        }
    }
}