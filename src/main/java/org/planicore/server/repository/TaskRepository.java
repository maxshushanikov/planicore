package org.planicore.server.repository;

import org.planicore.server.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // При необходимости можно добавить методы поиска по статусу, дате и т.д.
}