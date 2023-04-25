package com.bytner.librarytestapi2.common.exception;

import lombok.Getter;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationErrorDto extends ExceptionDto {

    private final List<ViolationInfo> violationInfoList = new ArrayList<>();

    public ValidationErrorDto() {
        super("Validation errors");
    }

    public void addViolation(String field, String message) {
        violationInfoList.add(new ViolationInfo(field, message));
    }

    @Value
    public static class ViolationInfo {
        String field;
        String message;
    }
}
