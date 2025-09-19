// ===== dto/TaskUpdateRequest.java =====
package org.planicore.server.dto;

import java.time.LocalDateTime;

public record TaskUpdateRequest(
        String title,
        String description,
        TaskStatus status,
        LocalDateTime dueDate
) {}
