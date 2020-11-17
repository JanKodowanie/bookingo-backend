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
import pw.testoprog.bookingo.exceptions.FileStorageException;
import pw.testoprog.bookingo.exceptions.UserNotFoundException;
import pw.testoprog.bookingo.models.GalleryPhoto;
import pw.testoprog.bookingo.models.User;
import pw.testoprog.bookingo.models.Venue;
import pw.testoprog.bookingo.repositories.GalleryPhotoRepository;
import pw.testoprog.bookingo.repositories.VenueRepository;
import pw.testoprog.bookingo.serializers.ErrorResponse;
import pw.testoprog.bookingo.serializers.FileUrlResponse;
import pw.testoprog.bookingo.serializers.MessageResponse;
import pw.testoprog.bookingo.services.FileStorageService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

@RestController
public class GalleryPhotoController {

    @Autowired
    private GalleryPhotoRepository repository;

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private FileStorageService fileStorageService;



    @PostMapping("/venues/{venue_id}/gallery-photos")
    ResponseEntity newGalleryPhoto(@RequestParam("file") MultipartFile file, @PathVariable Integer venue_id) {
        String url = null;

        try {
            url = fileStorageService.storeFile(file, new String[]{"venues", venue_id.toString()}, new String[]{"jpg", "png", "jpeg"});
        } catch (FileStorageException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        }


        Optional<Venue> opt = venueRepository.findById(venue_id);

        if (!opt.isPresent()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse("Venue with the given id was not found."));
        }
        Venue venue = opt.get();
        GalleryPhoto photo = new GalleryPhoto(url, venue);
        repository.save(photo);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new FileUrlResponse(url));
    }

}