package com.example.contacts;

import java.security.MessageDigest;

public class HelpMe {
    public static String sha256(String s){
        try{
            //creation du hash SHA256
            MessageDigest digest = MessageDigest.getInstance("SHA256");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            //creation du chaines en héxa
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length;i++){
                hexString.append(Integer.toHexString( messageDigest[i]));

            }
            return hexString.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return " ";
    }
}
