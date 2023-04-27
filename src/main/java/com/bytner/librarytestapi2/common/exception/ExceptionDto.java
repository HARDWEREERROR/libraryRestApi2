package com.bytner.librarytestapi2.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ExceptionDto {

    private final LocalDateTime timeStamp = LocalDateTime.now();
    private final String message;
}
