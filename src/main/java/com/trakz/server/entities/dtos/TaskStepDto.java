package com.trakz.server.entities.dtos;

import com.trakz.server.entities.TaskStep;
import com.trakz.server.utils.contracts.AbstractBaseDtos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TaskStepDto extends AbstractBaseDtos<TaskStep> {
  private String content;
  private boolean isCompleted = false;
  private Long taskId;

  @Override
  public TaskStep toEntity() {
    return TaskStep.builder()
      .content(content)
      .isCompleted(isCompleted)
      .taskId(taskId)
      .build();
  }
}
