package com.trakz.server.repositories;

import com.trakz.server.entities.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderRepository extends JpaRepository<Folder, Long> {
  Folder findByNameIgnoreCase(String name);
}
