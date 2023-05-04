package com.trakz.server.utils;

import com.trakz.server.entities.dtos.HttpResponseDto;
import org.springframework.http.HttpStatus;

import java.util.Map;

import static java.time.LocalDateTime.now;

public class HttpUtils {
  public static HttpResponseDto buildResponse(String dataName, Object data, String message, HttpStatus status) {
    return HttpResponseDto.builder()
      .timestamp(now())
      .data(Map.of(dataName, data))
      .message(message)
      .status(status)
      .statusCode(status.value())
      .build();
  }

  public static HttpResponseDto buildResponse(Object data, String message, HttpStatus status) {
    return HttpResponseDto.builder()
      .timestamp(now())
      .data(data)
      .message(message)
      .status(status)
      .statusCode(status.value())
      .build();
  }

  public static HttpResponseDto buildResponseWithoutData(String message, HttpStatus status) {
    return HttpResponseDto.builder()
      .timestamp(now())
      .message(message)
      .status(status)
      .statusCode(status.value())
      .build();
  }
}
