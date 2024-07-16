package org.karpiukjava.task.tracker.api.factories;

import org.karpiukjava.task.tracker.api.dataTransferObjects.TaskDTO;
import org.karpiukjava.task.tracker.store.entities.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskDTOFactory {
    private TaskDTO makeTaskDTO (TaskEntity entity) {
        return TaskDTO.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .changedAt(entity.getChangedAt())
                .description(entity.getDescription())
                .build();
    }
}
