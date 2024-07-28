package com.example.recruitment.common.dto;

import com.example.recruitment.common.code.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class CommonDtoOut<T> {
    private Integer errorCode;
    private Integer statusCode;
    private String message;
    private T object;


    public static <T> CommonDtoOut<T> success(T object) {
        return CommonDtoOut.<T>builder()
                .errorCode(ErrorCode.SUCCESS)
                .statusCode(HttpStatus.OK.value())
                .message("Success")
                .object(object)
                .build();
    }

  public static <T> CommonDtoOut<T> create(T object) {
    return CommonDtoOut.<T>builder()
      .errorCode(ErrorCode.CREATED)
      .statusCode(HttpStatus.CREATED.value())
      .message("Created")
      .object(object)
      .build();
  }

}
