package Utils;

import org.mindrot.jbcrypt.BCrypt;

import java.security.SecureRandom;
import java.util.Random;

public class Security extends BCrypt {
    public String generateHash(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public Boolean checkPassword(String password, String hashPassword){
        return BCrypt.checkpw(password, hashPassword);
    }


    public String generateRandomPassword(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("length must be at least 1");

        }
        String char_set = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

        return generateRandomChars(char_set, length);
    }

    private String generateRandomChars(String set, int length) {
        Random random = new SecureRandom();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(set.charAt(random.nextInt(set.length())));
        }

        return sb.toString();
    }

}
