class PasswordDecryptorFactory {
    public static final int DICTIONARY_METHOD_OPTION = 1;
    public static final int BRUTE_FORCE_METHOD_OPTION = 2;

    public static PasswordDecryptor createDecryptor(int option) {
        if (option == DICTIONARY_METHOD_OPTION) {
            return new DictionaryPasswordDecryptor();
        } else if (option == BRUTE_FORCE_METHOD_OPTION) {
            return new BruteForcePasswordDecryptor();
        } else {
            throw new IllegalArgumentException("Option invalide.");
        }
    }
}