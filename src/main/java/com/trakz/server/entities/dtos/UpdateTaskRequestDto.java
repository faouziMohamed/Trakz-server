package com.trakz.server.entities.dtos;

import com.trakz.server.entities.Note;
import com.trakz.server.entities.Task;
import com.trakz.server.utils.contracts.AbstractBaseDtos;
import com.trakz.server.utils.enumeration.Recurrence;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Builder
@Data
public class UpdateTaskRequestDto extends AbstractBaseDtos<Task> {
  private Long id;
  private String content = null;
  private Long folderId = 1L;
  private boolean isInMyDay = false;
  private boolean isImportant = false;
  private boolean isCompleted = false;
  private Recurrence recurrence = null;
  private int recurrenceEvery = 0;
  private String recurrenceUnit = null;
  private LocalDateTime dueDate = null;
  private Note note = null;

  @Override
  public Task toEntity() {
    return UpdateTaskRequestDto.toEntityModel(this);
  }

  public static Task toEntityModel(UpdateTaskRequestDto dto) {
    return Task.builder()
      .id(dto.id)
      .content(dto.content)
      .folderId(dto.folderId)
      .isInMyDay(dto.isInMyDay)
      .isImportant(dto.isImportant)
      .isCompleted(dto.isCompleted)
      .recurrence(dto.recurrence)
      .recurrenceEvery(dto.recurrenceEvery)
      .recurrenceUnit(dto.recurrenceUnit)
      .dueDate(dto.dueDate)
      .note(dto.note)
      .build();
  }
}
