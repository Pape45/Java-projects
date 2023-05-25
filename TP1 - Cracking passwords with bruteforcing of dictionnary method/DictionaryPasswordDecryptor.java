import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class DictionaryPasswordDecryptor implements PasswordDecryptor {
    private static final String DICTIONARY_MD5_FILE = "dictionnaire_md5.txt";
    private static final String DICTIONARY_SHA1_FILE = "dictionnaire_sha1.txt";

    private Map<String, String> dictionary;

    public DictionaryPasswordDecryptor() {
        dictionary = new HashMap<>();
    }

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

        String dictionaryFile = getDictionaryFile(hashOption);
        loadDictionary(dictionaryFile);

        for (Map.Entry<String, String> entry : dictionary.entrySet()) {
            if (entry.getValue().equals(encryptedPassword)) {
                return entry.getKey();
            }
        }

        System.out.println("\nLe mot de passe n'a pas été trouvé dans le dictionnaire.");
        return null;
    }

    private void displayHashOptions() {
        System.out.println("\nEntrez le type de hachage :\n");
        System.out.println("1. MD5");
        System.out.println("2. SHA1\n");
    }

    private String getDictionaryFile(int hashOption) {
        if (hashOption == 1) {
            return DICTIONARY_MD5_FILE;
        } else {
            return DICTIONARY_SHA1_FILE;
        }
    }

    private void loadDictionary(String dictionaryFile) {
        try (Scanner scanner = new Scanner(new File(dictionaryFile))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String password = parts[0];
                    String encryptedPassword = parts[1];
                    dictionary.put(password, encryptedPassword);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("\nLe dictionnaire n'a pas été trouvé.");
            System.exit(0);
        }
    }
}