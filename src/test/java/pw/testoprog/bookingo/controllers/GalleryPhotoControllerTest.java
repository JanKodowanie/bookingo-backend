package pw.testoprog.bookingo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import pw.testoprog.bookingo.models.Venue;
import pw.testoprog.bookingo.repositories.GalleryPhotoRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class GalleryPhotoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GalleryPhotoRepository repository;

    private Venue venue;
}
