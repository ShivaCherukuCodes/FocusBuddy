package com.focusbuddy.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReminderDTO {
    private Long id;
    private String message;
    private LocalDateTime remindAt;
    private boolean sent;
    private Long userId;
    private boolean repeatDaily;

}
