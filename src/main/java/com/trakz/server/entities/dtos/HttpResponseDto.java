package com.trakz.server.entities.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@SuperBuilder
@JsonInclude(NON_NULL)
public class HttpResponseDto {
  protected LocalDateTime timestamp;
  protected String message;
  protected int statusCode;
  protected HttpStatusCode status;
  protected String reason;
  protected String developerMessage;
  private Object data;

}
