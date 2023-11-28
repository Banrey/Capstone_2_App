package com.example.capstone2app;

import java.math.BigInteger;
import java.security.MessageDigest;

public class md5Encryption {
    public static String MD5 (String pass){

        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(pass.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hash = number.toString(16);
            while (hash.length()<32){
                hash = "0"+ hash;

            }
            return hash;

        }catch(Exception e){
            throw new RuntimeException(e);

        }

    }
}
