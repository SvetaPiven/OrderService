package org.order.config;

import java.time.LocalDateTime;

public record ApiErrorResponse(String message, LocalDateTime dateTime) {

}