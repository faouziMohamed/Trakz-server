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
public class CreateTaskRequestDto extends AbstractBaseDtos<Task> {
  private String content;
  private Long folderId = 0L;
  private String folderName = null;
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
    return CreateTaskRequestDto.toTaskEntity(this);
  }

  public static Task toTaskEntity(CreateTaskRequestDto dto) {
    return Task.builder()
      .content(dto.content)
      .folderId(dto.folderId == null ? 0L : dto.folderId)
      .isInMyDay(dto.isInMyDay)
      .isImportant(dto.isImportant)
      .isCompleted(dto.isCompleted)
      .dueDate(dto.dueDate)
      .recurrence(dto.recurrence)
      .recurrenceEvery(dto.recurrenceEvery)
      .recurrenceUnit(dto.recurrenceUnit)
      .note(dto.note)
      .build();
  }

  public Long getFolderId() {
    return folderId == null ? 0L : folderId;
  }
}
