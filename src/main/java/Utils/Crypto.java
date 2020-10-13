package Utils;

import org.mindrot.jbcrypt.BCrypt;

import java.security.SecureRandom;
import java.util.Random;

public class Crypto extends BCrypt {
    private static String generateRandomChars(String set, int length) {
        Random random = new SecureRandom();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(set.charAt(random.nextInt(set.length())));
        }

        return sb.toString();
    }


    public static String generateRandomPassword(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("length must be at least 1");

        }
        String char_set = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

        return generateRandomChars(char_set, length);
    }

    public static int generateRandomNumber(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("length must be at least 1");

        }
        String char_set = "1234567890";

        return Integer.parseInt(generateRandomChars(char_set, length));
    }
    public static String hashpw(String pw) {

        String salt = gensalt();

        return hashpw(pw, salt);
    }
}
