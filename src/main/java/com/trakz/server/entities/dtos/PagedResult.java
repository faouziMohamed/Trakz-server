package com.trakz.server.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PagedResult {
  private List<?> items;
  private Long totalItems;
  private Integer totalPages;
  private Integer currentPage;
  private Integer pageSize;
  private String[] sort;

  public static PagedResult toDtoModel(Page<?> items) {
    return PagedResult.builder()
      .items(items.getContent())
      .totalItems(items.getTotalElements())
      .totalPages(items.getTotalPages())
      .currentPage(items.getNumber())
      .pageSize(items.getSize())
      .sort(items.getSort().toString().split(","))
      .build();
  }
}
