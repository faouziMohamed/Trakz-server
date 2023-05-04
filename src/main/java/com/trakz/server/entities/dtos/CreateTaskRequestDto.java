package com.trakz.server.entities.dtos;

import com.trakz.server.entities.Task;
import com.trakz.server.entities.TaskNote;
import com.trakz.server.utils.contracts.AbstractBaseDtos;
import com.trakz.server.utils.enumeration.Recurrence;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Builder
@Data
public class CreateTaskRequestDto extends AbstractBaseDtos<Task> {
  private String content;
  private Long folderId;
  private boolean isInMyDay = false;
  private boolean isImportant = false;
  private boolean isCompleted = false;
  private Recurrence recurrence = null;
  private int recurrenceEvery = 0;
  private String recurrenceUnit = null;
  private TaskNote taskNote = null;

  public static Task toTaskEntity(CreateTaskRequestDto dto) {
    return Task.builder()
      .content(dto.content)
      .folderId(dto.folderId)
      .isInMyDay(dto.isInMyDay)
      .isImportant(dto.isImportant)
      .isCompleted(dto.isCompleted)
      .recurrence(dto.recurrence)
      .recurrenceEvery(dto.recurrenceEvery)
      .recurrenceUnit(dto.recurrenceUnit)
      .taskNote(dto.taskNote)
      .build();
  }

  @Override
  public Task toEntity() {
    return CreateTaskRequestDto.toTaskEntity(this);
  }
}
