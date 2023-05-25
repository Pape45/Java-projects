import java.util.Scanner;

class BruteForcePasswordDecryptor implements PasswordDecryptor {
    private static final String CHARSET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int MAX_PASSWORD_LENGTH = 6;

    @Override
    public String decrypt(String encryptedPassword) {
        Scanner scanner = new Scanner(System.in);
        int hashOption;

        do {
            displayHashOptions();
            hashOption = scanner.nextInt();
            scanner.nextLine();

            if (hashOption != 1 && hashOption != 2) {
                System.out.println("Option invalide.");
            }
        } while (hashOption != 1 && hashOption != 2);

        String hashType = getHashType(hashOption);
        StringBuilder password = new StringBuilder();
        long startTime = System.currentTimeMillis();

        for (int length = 1; length <= MAX_PASSWORD_LENGTH; length++) {
            if (generatePasswords(password, length, 0, encryptedPassword, hashType)) {
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                System.out.println("\nDéchiffrement en cours...");
                System.out.println("Temps écoulé : " + duration + " millisecondes");
                return password.toString();
            }
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("\nLe mot de passe correspondant au hash n'a pas été trouvé.");
        System.out.println("Temps écoulé : " + duration + " millisecondes");

        return null;
    }

    private void displayHashOptions() {
        System.out.println("\nEntrez le type de hachage :\n");
        System.out.println("1. MD5");
        System.out.println("2. SHA1\n");
    }

    private String getHashType(int hashOption) {
        if (hashOption == 1) {
            return "MD5";
        } else {
            return "SHA1";
        }
    }

    private boolean generatePasswords(StringBuilder password, int length, int position, String encryptedPassword, String hashType) {
        if (position == length) {
            String generatedPassword = password.toString();
            String generatedHash = PasswordEncryptor.encrypt(generatedPassword, hashType);
            if (generatedHash.equals(encryptedPassword)) {
                return true;
            }
        } else {
            for (int i = 0; i < CHARSET.length(); i++) {
                password.append(CHARSET.charAt(i));
                if (generatePasswords(password, length, position + 1, encryptedPassword, hashType)) {
                    return true;
                }
                password.deleteCharAt(password.length() - 1);
            }
        }

        return false;
    }
}