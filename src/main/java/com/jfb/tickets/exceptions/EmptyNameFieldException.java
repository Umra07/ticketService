package com.jfb.tickets.exceptions;

public class EmptyNameFieldException extends RuntimeException {
  public EmptyNameFieldException() {
    super("The 'name' field is required and cannot be empty.");
  }
}
