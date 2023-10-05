package org.order.dto.response;

import java.time.LocalDateTime;

public record ApiErrorResponse(String message, LocalDateTime dateTime) {

}