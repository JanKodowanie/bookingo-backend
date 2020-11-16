package pw.testoprog.bookingo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pw.testoprog.bookingo.models.GalleryPhoto;
import pw.testoprog.bookingo.models.Venue;
import pw.testoprog.bookingo.repositories.GalleryPhotoRepository;
import pw.testoprog.bookingo.repositories.VenueRepository;
import pw.testoprog.bookingo.services.FileStorageService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class GalleryPhotoController {
    private final GalleryPhotoRepository repository;
    private final VenueRepository venueRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public GalleryPhotoController(GalleryPhotoRepository repository, VenueRepository venueRepository) {
        this.repository = repository;
        this.venueRepository = venueRepository;
    }

    @PostMapping("/venues/{venue_id}/gallery-photos")
    ResponseEntity newGalleryPhoto(@RequestParam("file") MultipartFile file, @PathVariable Integer venue_id) {
        String fileName = fileStorageService.storeFile(file);

        String url = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(fileName)
                .toUriString();

        Venue venue = venueRepository.getOne(venue_id);
        GalleryPhoto photo = new GalleryPhoto(url, venue);
        repository.save(photo);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("{\"url\":\"" + fileStorageService.getAbsolutePath(fileName) + "\"}");
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
        }

        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
