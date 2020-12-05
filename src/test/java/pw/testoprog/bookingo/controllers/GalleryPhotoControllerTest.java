package pw.testoprog.bookingo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pw.testoprog.bookingo.models.GalleryPhoto;
import pw.testoprog.bookingo.models.Venue;
import pw.testoprog.bookingo.repositories.GalleryPhotoRepository;
import pw.testoprog.bookingo.repositories.VenueRepository;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GalleryPhotoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private final static String resourcePath = "/venues/{venue_id}/gallery-photos";
    private final static String correctFileName = "test.png";
    private final static String incorrectFileName = "incorrect.png";
    private final ObjectMapper mapper = new ObjectMapper();

    private Venue venue;

    @Autowired
    private VenueRepository venueRepository;
    @Autowired
    private GalleryPhotoRepository photoRepository;

    @BeforeEach
    public void setup() {
        venue = new Venue();
        venue = venueRepository.save(venue);
    }

    @Test
    void givenCorrectData_whenUploadingNewPicture_shouldReturnStatus200() throws Exception {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(correctFileName);
        MockMultipartFile photo = new MockMultipartFile("file", correctFileName, "image/png", inputStream);
        ResultActions resultActions = mockMvc.perform(multipart(resourcePath, venue.getId()).file(photo))
                .andExpect(status().isOk());

        Venue updatedVenue = venueRepository.findById(this.venue.getId()).get();
        Assert.assertFalse(updatedVenue.getGalleryPhotos().isEmpty());
        GalleryPhoto uploadedPhoto = (GalleryPhoto) updatedVenue.getGalleryPhotos().toArray()[0];

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        JSONObject obj = new JSONObject(contentAsString);
        String url = obj.getString("url");

        Assert.assertEquals(uploadedPhoto.getUrl(), url);

        inputStream.close();
    }

    @Test
    void givenNoImage_whenUploadingNewPicture_shouldReturnStatus400() throws Exception {

        mockMvc.perform(multipart(resourcePath, venue.getId()))
                .andExpect(status().isBadRequest());

        Venue updatedVenue = venueRepository.findById(this.venue.getId()).get();
        Assert.assertTrue(updatedVenue.getGalleryPhotos().isEmpty());

    }

    @Test
    void givenNotExistingVenue_whenUploadingNewPicture_shouldReturnStatus404() throws Exception {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(correctFileName);
        MockMultipartFile photo = new MockMultipartFile("file", correctFileName, "image/png", inputStream);

        mockMvc.perform(multipart(resourcePath, venue.getId() + 100).file(photo))
                .andExpect(status().isNotFound());
    }
}