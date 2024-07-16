package org.karpiukjava.task.tracker.api.factories;

import org.karpiukjava.task.tracker.api.dataTransferObjects.ProjectDTO;
import org.karpiukjava.task.tracker.store.entities.ProjectEntity;
import org.springframework.stereotype.Component;

@Component
public class ProjectDTOFactory {

    public ProjectDTO makeProjectDTO(ProjectEntity entity){
        return ProjectDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreateAt())
                .changedAt(entity.getChangedAt())
                .build();
    }
}
