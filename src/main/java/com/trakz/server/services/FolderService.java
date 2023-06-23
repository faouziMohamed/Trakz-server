package com.trakz.server.services;

import com.trakz.server.entities.Folder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FolderService {
  Folder createFolder(Folder folder);

  Folder getFolderById(Long id);

  Folder getFolderByName(String name);

  Page<Folder> getAllFolders(Pageable pageable);

}
