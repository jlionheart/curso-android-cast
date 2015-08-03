package com.curso_android_cast.cursoandroidcast.util;

import java.security.MessageDigest;
import java.security.SecureRandom;

public class PasswordUtil {

    private static final String SHA_256 = "SHA-256";
    private static final String UTF_8 = "UTF-8";

    private PasswordUtil(){
        super();
    }

    public static String generateRandomSalt(){
        SecureRandom random = new SecureRandom();
        StringBuilder result = new StringBuilder();

        byte[] hash = random.generateSeed(50);

        for(byte byt : hash)
            result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));

        return result.toString();
    }

    public static String generatePasswordHash(String password, String salt){
        StringBuilder result = new StringBuilder();
        MessageDigest messageDigest;
        String newPassword = password.concat(salt);
        byte[] hash;

        try {
            messageDigest = MessageDigest.getInstance(SHA_256);
            hash = messageDigest.digest(newPassword.getBytes(UTF_8));
        }
        catch (Exception e){
            throw new RuntimeException();
        }

        for(byte byt : hash)
            result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));

        return result.toString();
    }
}
