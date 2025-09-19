// ===== dto/TaskCreateRequest.java =====
package org.planicore.server.dto;

import java.time.LocalDateTime;

public record TaskCreateRequest(
        String title,
        String description,
        TaskStatus status,
        LocalDateTime dueDate
) {}
