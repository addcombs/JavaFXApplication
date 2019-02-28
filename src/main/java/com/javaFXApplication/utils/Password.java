package com.javaFXApplication.utils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

public class Password {

          private static final Random RANDOM = new SecureRandom();
          private static final String ALPHANUMERIC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
          private static final int ITERATIONS = 10000;
          private static final int KEY_LENGTH = 256;

          public static String getSalt(int length){
                    StringBuilder salt = new StringBuilder(length);
                    for (int i = 0; i < length; i++){
                              salt.append(ALPHANUMERIC.charAt(RANDOM.nextInt(ALPHANUMERIC.length())));
                    }
                    return new String(salt);
          }

          public static byte[] hash(char[] password, byte[] salt){
                    PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
                    Arrays.fill(password, Character.MIN_VALUE);
                    try{
                              SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                              return skf.generateSecret(spec).getEncoded();
                    } catch (NoSuchAlgorithmException | InvalidKeySpecException e){
                              throw new AssertionError ("Error while hashing a password: " + e.getMessage(), e);
                    } finally{
                              spec.clearPassword();
                    }
          }

          public static String generateSecurePassword(String password, String salt){
                    String returnValue = null;
                    byte[] securePassword = hash(password.toCharArray(), salt.getBytes());
                    returnValue = Base64.getEncoder().encodeToString(securePassword);
                    return returnValue;
          }

          public static boolean verifyUserPassword(String providedPassword, String securedPassword, String salt){
                    boolean returnValue = false;
                    String newSecurePassword = generateSecurePassword(providedPassword,salt);
                    returnValue = newSecurePassword.equalsIgnoreCase(securedPassword);
                    return returnValue;
          }

}
