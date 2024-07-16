package org.karpiukjava.task.tracker.api.controllers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.karpiukjava.task.tracker.api.dataTransferObjects.AckDTO;
import org.karpiukjava.task.tracker.api.dataTransferObjects.ProjectDTO;
import org.karpiukjava.task.tracker.api.exceptions.BadRequestException;
import org.karpiukjava.task.tracker.api.exceptions.NotFoundException;
import org.karpiukjava.task.tracker.api.factories.ProjectDTOFactory;
import org.karpiukjava.task.tracker.store.entities.ProjectEntity;
import org.karpiukjava.task.tracker.store.repositories.ProjectRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RestController
public class ProjectController {

    ProjectDTOFactory projectDTOFactory;
    ProjectRepository projectRepository;

    public static final String CREATE_OR_UPDATE_PROJECT = "/api/projects/";
    public static final String FETCH_PROJECTS = "/api/projects";
    public static final String DELETE_PROJECT = "/api/projects/{project_id}";

    @GetMapping(FETCH_PROJECTS)
    public List<ProjectDTO> fetchProjects(
            @RequestParam(value = "prefix_name",required = false) Optional<String> optionalPrefixName){
        optionalPrefixName = optionalPrefixName.filter(prefixName -> prefixName.trim().isEmpty());

        Stream<ProjectEntity> projectEntityStream = optionalPrefixName
                .map(projectRepository::streamAllByNameStartsWithIgnoreCase)
                .orElseGet(projectRepository::streamAll);

        return projectEntityStream
                .map(projectDTOFactory::makeProjectDTO)
                .collect(Collectors.toList());
    }

    @PutMapping(CREATE_OR_UPDATE_PROJECT)
    public ProjectDTO createOrUpdateProject(
            @RequestParam(value = "project_id",required = false) Optional<Long> optionalProjectID,
            @RequestParam(value = "project_name",required = false) Optional<String> optionalProjectName){

        boolean isCreated = !optionalProjectID.isPresent();

        if(isCreated && optionalProjectName.isEmpty()){
            throw new BadRequestException("Project name can't be empty");
        }

        optionalProjectName = optionalProjectName.filter(projectName -> !projectName.trim().isEmpty());

        final ProjectEntity project = optionalProjectID
                .map(this::getProjectOrThrowException)
                .orElseGet(() -> ProjectEntity.builder().build());

        optionalProjectName
                .ifPresent(projectName -> {
                    projectRepository
                            .findByName(projectName)
                            .filter(anotherProject -> !Objects.equals(anotherProject.getId(),project.getId()))
                            .ifPresent(anotherProject -> {
                                throw new BadRequestException(
                                        String.format("Project \"%s\" already exists.",projectName)
                                );
                            });
                    project.setName(projectName);
                });

        final ProjectEntity savedProject = projectRepository.saveAndFlush(project);

        return projectDTOFactory.makeProjectDTO(savedProject);

    }


    private ProjectEntity getProjectOrThrowException(Long projectID) {
        return projectRepository
                .findById(projectID)
                .orElseThrow(() ->
                        new NotFoundException(
                                String.format("Project \"%s\" doesn't exist.", projectID)
                        )
                );
    }

    @DeleteMapping(DELETE_PROJECT)
    public AckDTO deleteProject(@PathVariable("project_id") Long projectID){
        projectRepository
                .findById(projectID)
                .orElseThrow(() ->
                        new NotFoundException(
                                String.format("Project \"%s\" doesn't exist.", projectID)
                        )
                );

        projectRepository.deleteById(projectID);

        return AckDTO.makeDefault(true);
    }

}
