package org.karpiukjava.task.tracker.api.factories;

import org.karpiukjava.task.tracker.api.dataTransferObjects.ProjectDTO;
import org.karpiukjava.task.tracker.api.dataTransferObjects.TaskDTO;
import org.karpiukjava.task.tracker.api.dataTransferObjects.TaskStateDTO;
import org.karpiukjava.task.tracker.store.entities.TaskStateEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskStateDTOFactory {

    public TaskStateDTO makeTaskStateDTO (TaskStateEntity entity) {
        return TaskStateDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .ordinal(entity.getOrdinal())
                .changedAt(entity.getChangedAt())
                .build();
    }
}
