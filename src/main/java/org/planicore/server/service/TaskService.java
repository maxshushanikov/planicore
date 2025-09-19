package org.planicore.server.service;

import org.planicore.server.domain.Task;
import org.planicore.server.dto.*;
import org.planicore.server.mapper.TaskMapper;
import org.planicore.server.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskService {

    private final TaskRepository repository;
    private final TaskMapper mapper;

    public TaskService(TaskRepository repository, TaskMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public TaskResponse create(TaskCreateRequest request) {
        Task task = mapper.toEntity(request);
        return mapper.toResponse(repository.save(task));
    }

    public List<TaskResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public TaskResponse findById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public TaskResponse update(Long id, TaskUpdateRequest request) {
        Task task = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        mapper.updateEntityFromDto(request, task);
        return mapper.toResponse(repository.save(task));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Task not found");
        }
        repository.deleteById(id);
    }
}
