package com.dwf.mvc.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class Error {
    private String messageUser;
    private String messageToDeveloper;
    private String stackTrace;
}