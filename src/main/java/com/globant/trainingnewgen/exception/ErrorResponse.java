package com.globant.trainingnewgen.exception;

public record ErrorResponse(
        String message,
        String path
) {
}
