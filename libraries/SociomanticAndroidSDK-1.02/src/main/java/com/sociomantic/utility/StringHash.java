package com.sociomantic.utility;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by John Nilsen on 13.12.13 at Sociomantic Labs.
 */
public final class StringHash {

    private static final String FATAL_ERROR = "Fatal error!";
    private static SCMLog log = new SCMLog(StringHash.class);

    private StringHash(){

    }

    public static String convertToSHA256(String input){
        try {
            MessageDigest md  = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input.getBytes("UTF-8"));
            return convertToHexString(digest);
        } catch (NoSuchAlgorithmException e) {
            log.e(FATAL_ERROR, e);
        } catch (UnsupportedEncodingException e) {
            log.e(FATAL_ERROR, e);
        }
        return "";
    }

    private static String convertToHexString(byte[] bytes){
        StringBuilder hexString = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xff & aByte);
            if (hex.length() == 1){
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
