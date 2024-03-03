package itmo.spring.meeting.back.model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashManager {
    public static byte[] getSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    private static MessageDigest configureHashFunc(byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt);
        return md;
    }

    private static byte[] hashGeneration(MessageDigest md, String password){
        return md.digest(password.getBytes(StandardCharsets.UTF_8));
    }

    public static byte[] getHashFromPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
        return hashGeneration(configureHashFunc(salt), password);
    }
}
