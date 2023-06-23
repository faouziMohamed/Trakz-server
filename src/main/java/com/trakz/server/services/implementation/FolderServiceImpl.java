package com.trakz.server.services.implementation;

import com.trakz.server.entities.Folder;
import com.trakz.server.repositories.FolderRepository;
import com.trakz.server.services.FolderService;
import com.trakz.server.utils.Utils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class FolderServiceImpl implements FolderService {
  final private FolderRepository folderRepository;

  @Override
  public Folder createFolder(Folder folder) {
    // check if folder with the same name already exists
    var folderWithSameName = folderRepository.findByNameIgnoreCase(folder.getName());
    if (folderWithSameName != null) {
      log.warn("Folder with name {} already exists", folder.getName());
      return null;
    }
    log.info("Saving new folder {} to the database", folder.getName());
    String capitalizedName = Utils.capitalize(folder.getName());
    folder.setName(capitalizedName);
    return folderRepository.save(folder);
  }

  @Override
  public Folder getFolderById(Long id) {
    return folderRepository.findById(id).orElse(null);
  }

  @Override
  public Folder getFolderByName(String name) {
    return folderRepository.findByNameIgnoreCase(name);
  }

  @Override
  public Page<Folder> getAllFolders(Pageable pageable) {
    return folderRepository.findAll(pageable);
  }
}
