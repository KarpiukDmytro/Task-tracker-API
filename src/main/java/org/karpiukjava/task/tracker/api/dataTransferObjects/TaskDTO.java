package org.karpiukjava.task.tracker.api.dataTransferObjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults (level = AccessLevel.PRIVATE)
public class TaskDTO {

    Long id;

    String name;

    @JsonProperty("created_at")
    Instant createdAt;

    @JsonProperty("changed_at")
    Instant changedAt;

    String description;

}
