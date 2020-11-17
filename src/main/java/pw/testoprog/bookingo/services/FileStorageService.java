package pw.testoprog.bookingo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pw.testoprog.bookingo.exceptions.FileStorageException;
import pw.testoprog.bookingo.exceptions.BookingoFileNotFoundException;
import pw.testoprog.bookingo.properties.FileStorageProperties;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) throws FileStorageException {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file, String[] subdirectories, String[] acceptedExts) throws FileStorageException{
        // Normalize file name
        String fileName = file.getOriginalFilename();

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            String extension = fileName.split("[.]", 2)[1];

            if (!Arrays.asList(acceptedExts).contains(extension)) {
                throw new FileStorageException("Format not supported. Please upload " + String.join(", ", acceptedExts));
            }

            String newFileName = UUID.randomUUID().toString() + "." + extension;
            Path targetLocation = this.fileStorageLocation;
            for (String str : subdirectories) {
                targetLocation = targetLocation.resolve(str);
            }

            if (!Files.exists(targetLocation)) {
                Files.createDirectories(targetLocation);
            }
            Path targetFile = targetLocation.resolve(newFileName);
            Files.createFile(targetFile);
            Files.copy(file.getInputStream(), targetFile, StandardCopyOption.REPLACE_EXISTING);

            return "/media/" + String.join("/", subdirectories) + "/" + newFileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String path) throws BookingoFileNotFoundException {
        try {
            Path filePath = Paths.get(this.fileStorageLocation.toString() + path);
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new BookingoFileNotFoundException("File not found " + path);
            }
        } catch (MalformedURLException ex) {
            throw new BookingoFileNotFoundException("File not found " + path, ex);
        }
    }
}