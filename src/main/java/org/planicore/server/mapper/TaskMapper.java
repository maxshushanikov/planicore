package org.planicore.server.mapper;

import org.planicore.server.domain.Task;
import org.planicore.server.dto.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task toEntity(TaskCreateRequest request);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(TaskUpdateRequest request, @MappingTarget Task task);

    TaskResponse toResponse(Task task);
}
