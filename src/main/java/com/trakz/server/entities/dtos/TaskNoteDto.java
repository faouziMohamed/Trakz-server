package com.trakz.server.entities.dtos;

import com.trakz.server.entities.Note;
import com.trakz.server.utils.contracts.AbstractBaseDtos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Builder
@Data
public class TaskNoteDto extends AbstractBaseDtos<Note> {
  private String note;

  @Override
  public Note toEntity() {
    return TaskNoteDto.toTaskEntity(this);
  }

  public static Note toTaskEntity(TaskNoteDto dto) {
    return Note.builder()
      .content(dto.note)
      .build();
  }
}
