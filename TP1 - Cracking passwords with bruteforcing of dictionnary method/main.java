import java.util.Scanner;

public class main {
    private static final int ENCRYPT_OPTION = 1;
    private static final int DECRYPT_OPTION = 2;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        displayOptions();

        int option = getOption(scanner);

        if (option == ENCRYPT_OPTION) {
            handleEncryption(scanner);
        } else if (option == DECRYPT_OPTION) {
            handleDecryption(scanner);
        } else {
            System.out.println("Option invalide \n");
        }

        scanner.close();
    }

    private static void displayOptions() {
        System.out.println("Choisissez une option :\n");
        System.out.println("1. Crypter un mot de passe");
        System.out.println("2. Rechercher le mot de passe correspondant à un hash\n");
    }

    private static int getOption(Scanner scanner) {
        int option;

        do {
            option = scanner.nextInt();
            scanner.nextLine();

            if (option != ENCRYPT_OPTION && option != DECRYPT_OPTION) {
                System.out.println("Option invalide.");
            }
        } while (option != ENCRYPT_OPTION && option != DECRYPT_OPTION);

        return option;
    }

    private static void handleEncryption(Scanner scanner) {
        System.out.print("\nEntrez le mot de passe : ");
        String password = scanner.nextLine();

        displayHashOptions();

        int hashOption = getOption(scanner);

        String hashType = getHashType(hashOption);

        String encryptedPassword = PasswordEncryptor.encrypt(password, hashType);
        if (encryptedPassword != null) {
            System.out.println("\nMot de passe crypté : " + encryptedPassword + "\n");
        }
    }

    private static void handleDecryption(Scanner scanner) {
        System.out.print("\nEntrez le hash : ");
        String hash = scanner.nextLine();

        displayMethodOptions();

        int methodOption = getOption(scanner);

        PasswordDecryptor decryptor = PasswordDecryptorFactory.createDecryptor(methodOption);

        String decryptedPassword = decryptor.decrypt(hash);

        if (decryptedPassword != null) {
            System.out.println("Mot de passe décrypté : " + decryptedPassword);
        }
    }

    private static void displayHashOptions() {
        System.out.println("\nEntrez le type de hachage :");
        System.out.println("1. MD5");
        System.out.println("2. SHA1\n");
    }

    private static void displayMethodOptions() {
        System.out.println("\nChoisissez une méthode de décryptage :");
        System.out.println("1. Dictionnaire");
        System.out.println("2. Force brute\n");
    }

    private static String getHashType(int hashOption) {
        if (hashOption == 1) {
            return "MD5";
        } else {
            return "SHA1";
        }
    }
}
