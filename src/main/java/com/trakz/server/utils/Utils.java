package com.trakz.server.utils;

public class Utils {
  public static String capitalize(String str) {
    if (str == null || str.isEmpty()) {
      return str;
    }
    return str.substring(0, 1).toUpperCase() + str.substring(1);
  }

  // capitalize all words in a string
  public static String capitalizeAll(String str) {
    if (str == null || str.isEmpty()) {
      return str;
    }
    String[] words = str.split(" ");
    StringBuilder sb = new StringBuilder();
    for (String word : words) {
      sb.append(capitalize(word)).append(" ");
    }
    return sb.toString().trim();
  }

  // check if a string is null or empty or contains only whitespace
  public static boolean isNullOrEmpty(String str) {
    return str == null || str.trim().isEmpty();
  }


  public static <TArray extends Comparable<TArray>> boolean isInArray(TArray element, TArray[] array) {
    for (TArray item : array) {
      if (item.compareTo(element) == 0) {
        return true;
      }
    }
    return false;
  }

}
