import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class PasswordEncryptor {
    public static String encrypt(String password, String hashType) {
        String encryptedPassword = null;

        try {
            MessageDigest md = null;
            if (hashType.equalsIgnoreCase("MD5")) {
                md = MessageDigest.getInstance("MD5");
            } else if (hashType.startsWith("SHA")) {
                int length = Integer.parseInt(hashType.substring(3));
                if (length > 0) {
                    md = MessageDigest.getInstance("SHA-" + length);
                }
            }

            if (md != null) {
                md.update(password.getBytes());
                byte[] digest = md.digest();
                BigInteger bigInt = new BigInteger(1, digest);
                encryptedPassword = bigInt.toString(16);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return encryptedPassword;
    }
}
