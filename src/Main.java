import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter file name: ");
        String file_name = sc.nextLine();
        System.out.println("Enter password: ");
        String password = sc.nextLine();

        // check if password is not longer than alphabet size:
        if(password.length() < 37) {
            Vigenere vigenere1 = new Vigenere(file_name, password);
            System.out.println("Enter output file name: ");
            String outputFileName;
            Scanner S = new Scanner(System.in);
            outputFileName = S.nextLine();

            System.out.println("\n Choose what to do:  1- encode   2- decode");
            int option = sc.nextInt();

            if(option == 1) {
                vigenere1.setOutputFileName(outputFileName);
                System.out.println("--> Text form file- " + file_name + " :");
                vigenere1.algorithmEncode();
            }
            else if (option == 2) {
                vigenere1.setOutputFileName(outputFileName);
                System.out.println("--> Text form file " + file_name + ":");
                vigenere1.algorithDecode();
            }
            else {
                System.out.println("\nWrong charcter!");
            }
        }
        else {
            System.out.println("\nPassword too long! Try once again!");
        }
    }
}
