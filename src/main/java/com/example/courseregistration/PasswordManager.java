package com.example.courseregistration;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

public class PasswordManager {
    private PasswordManager() {}
    public static String getHash(String password) {
        MessageDigest messageDigest = null;

        try{
            messageDigest = MessageDigest.getInstance("MD5");
        }
        catch(Exception e) {
            System.out.println("Failed generating hash for " + password + ", exiting...");
            System.exit(1);
        }

        byte[] digestHash = messageDigest.digest(password.getBytes());

        BigInteger number = new BigInteger(1, digestHash);

        return number.toString(16);
    }
}
