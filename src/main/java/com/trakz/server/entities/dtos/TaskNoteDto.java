package com.trakz.server.entities.dtos;

import com.trakz.server.entities.TaskNote;
import com.trakz.server.utils.contracts.AbstractBaseDtos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Builder
@Data
public class TaskNoteDto extends AbstractBaseDtos<TaskNote> {
  private String note;

  public static TaskNote toTaskEntity(TaskNoteDto dto) {
    return TaskNote.builder()
      .note(dto.note)
      .build();
  }

  @Override
  public TaskNote toEntity() {
    return TaskNoteDto.toTaskEntity(this);
  }
}
