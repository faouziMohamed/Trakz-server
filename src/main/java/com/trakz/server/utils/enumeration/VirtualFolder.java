package com.trakz.server.utils.enumeration;

import com.trakz.server.utils.Utils;
import lombok.NonNull;

import java.util.Arrays;

public enum VirtualFolder {
//  private final String[] virtualFolders = {"My Day", "Important", "Planned"};

  MY_DAY("My Day"),
  IMPORTANT("Important"),
  PLANNED("Planned");

  final static String[] folders = Arrays
    .stream(VirtualFolder.values())
    .map(VirtualFolder::getName)
    .toArray(String[]::new);
  private final String name;

  VirtualFolder(String name) {
    this.name = name;
  }

  static public boolean exists(@NonNull String name) {
    return Utils.isInArray(name, folders);
  }

  public String getName() {
    return name;
  }

  public boolean equalsName(String otherName) {
    System.out.println("Comparing " + name + " with " + otherName);
    return name.equalsIgnoreCase(otherName);
  }
}
