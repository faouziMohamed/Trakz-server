package com.trakz.server.utils.enumeration;

public enum Recurrence {
  DAILY("DAILY"),
  WEEKDAYS("WEEKDAYS"),
  WEEKLY("WEEKLY"),
  MONTHLY("MONTHLY"),
  YEARLY("YEARLY"),
  ONCE("ONCE"),
  CUSTOM("CUSTOM");

  private final String value;

  Recurrence(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
