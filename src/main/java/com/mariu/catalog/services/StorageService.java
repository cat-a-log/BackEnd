package com.mariu.catalog.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mariu.catalog.model.Item;
import com.mariu.catalog.storage.StorageException;
import com.mariu.catalog.storage.StorageFileNotFoundException;
import com.mariu.catalog.storage.StorageProperties;

@Service
public class StorageService {

  private final Path rootLocation;

  @Autowired
  public StorageService(StorageProperties properties) {

    if (properties.getLocation().trim().length() == 0) {
      throw new StorageException("File upload location can not be Empty.");
    }

    this.rootLocation = Paths.get(properties.getLocation());
  }

  private String generateName(String filename) {
    String fileExtension = "";
    int dotIndex = filename.lastIndexOf('.');

    if (dotIndex > 0 && dotIndex < filename.length() - 1) {
      fileExtension = filename.substring(dotIndex);
    }

    return UUID.randomUUID().toString() + fileExtension;
  }

  public String store(MultipartFile file, String filename) {
    if (filename == null) {
      filename = this.generateName(file.getOriginalFilename());
    }

    try {
      if (file.isEmpty()) {
        throw new StorageException("Failed to store empty file.");
      }

      Path destinationFile = this.rootLocation.resolve(Paths.get(filename))
          .normalize().toAbsolutePath();
      if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
        throw new StorageException(
            "Cannot store file outside current directory.");
      }
      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(inputStream, destinationFile,
            StandardCopyOption.REPLACE_EXISTING);
      }

      return destinationFile.getFileName().toString();
    } catch (IOException err) {
      throw new StorageException("Failed to store file.", err);
    }
  }

  public Path load(String filename) {
    return rootLocation.resolve(filename);
  }

  public Resource loadFromItem(Item item) {
    String filePath = item.getFilePath();

    try {
      Path file = load(filePath);
      Resource resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new StorageFileNotFoundException(
            "Could not read file: " + filePath);

      }
    } catch (MalformedURLException e) {
      throw new StorageFileNotFoundException("Could not read file: " + filePath, e);
    }
  }

  public void delete(Item item) {
    String filePath = item.getFilePath();

    if (filePath == "placeholder.png") {
      return;
    }

    try {
      Files.deleteIfExists(load(filePath));
    } catch (IOException err) {
      
    }
  }

  public void init() {
    try {
      Files.createDirectories(rootLocation);
    } catch (IOException e) {
      throw new StorageException("Could not initialize storage", e);
    }
  }
}