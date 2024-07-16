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

public class TaskStateEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    Long id;

    @Column (unique = true)
    String name;

    Long ordinal;

    @Builder.Default
    Instant createdAt = Instant.now();

    @Builder.Default
    Instant changedAt = Instant.now();

    @Builder.Default
    @OneToMany
    @JoinColumn(name = "task_state_id",referencedColumnName = "id")
    List<TaskEntity> tasks = new ArrayList<>();
}
