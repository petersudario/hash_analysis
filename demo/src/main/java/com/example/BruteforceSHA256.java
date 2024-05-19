package com.example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BruteforceSHA256 {

  public static String sha256(String input) throws NoSuchAlgorithmException {
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    byte[] hash = digest.digest(input.getBytes());
    StringBuilder hexString = new StringBuilder();
    for (byte b : hash) {
      String hex = Integer.toHexString(0xff & b);
      if (hex.length() == 1) {
        hexString.append('0');
      }
      hexString.append(hex);
    }
    return hexString.toString();
  }

  public static void bruteForce(String characters, int length, String targetHash) throws NoSuchAlgorithmException {
    char[] charset = characters.toCharArray();

    for (int i = 0; i < charset.length; i++) {
      StringBuilder sb = new StringBuilder(length);
      sb.append(charset[i]);
      bruteForceRec(charset, length, sb, targetHash);
    }
  }

  private static void bruteForceRec(char[] charset, int length, StringBuilder sb, String targetHash)
      throws NoSuchAlgorithmException {
    if (sb.length() == length) {
      String candidate = sb.toString();
      String hash = sha256(candidate);
      if (hash.equals(targetHash)) {
        System.out.println("Senha encontrada: " + candidate);
        return;
      }
    } else {
      for (int i = 0; i < charset.length; i++) {
        sb.append(charset[i]);
        bruteForceRec(charset, length, sb, targetHash);
        sb.deleteCharAt(sb.length() - 1);
      }
    }
  }
}
