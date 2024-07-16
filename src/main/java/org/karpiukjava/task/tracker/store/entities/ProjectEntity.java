package org.karpiukjava.task.tracker.store.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name="project")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    String name;

    @Builder.Default
    Instant createAt = Instant.now();

    @Builder.Default
    Instant changedAt = Instant.now();

    @Builder.Default
    @OneToMany
    @JoinColumn(name = "project_id",referencedColumnName = "id")
    List<TaskStateEntity> taskStates = new ArrayList<>();
}
