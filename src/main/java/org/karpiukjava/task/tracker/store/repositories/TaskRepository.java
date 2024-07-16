package org.karpiukjava.task.tracker.store.repositories;

import org.karpiukjava.task.tracker.store.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity,Long> {
}
