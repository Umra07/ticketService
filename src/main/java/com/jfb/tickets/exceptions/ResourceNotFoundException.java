package com.jfb.tickets.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Getter
@RequiredArgsConstructor
public class ResourceNotFoundException extends RuntimeException {
  private final String message;
  private final HttpStatus status;
}
