package pw.testoprog.bookingo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.*;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.DigestUtils;
import pw.testoprog.bookingo.models.Venue;
import pw.testoprog.bookingo.repositories.VenueRepository;
import pw.testoprog.bookingo.services.FileStorageService;


import javax.servlet.ServletOutputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GalleryPhotoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private final static String resourcePath = "/venues/{venue_id}/gallery-photos";
    private final static String downloadPath = "/download/{file_name}";
    private final static String correctFileName = "test.png";
    private final static String incorrectFileName = "incorrect.png";
    private InputStream inputStream;
    private MockMultipartFile photo;
    private final ObjectMapper mapper = new ObjectMapper();

    private Venue venue;

    @Autowired
    private VenueRepository venueRepository;

    @BeforeEach
    public void setup() {
        venue = new Venue();
        venue = venueRepository.save(venue);
    }

    @Test
    void givenCorrectFilePath_whenUploadingNewPicture_shouldReturnStatus200() throws Exception {
        inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(correctFileName);
        photo = new MockMultipartFile("file", correctFileName, "image/png", inputStream);
        mockMvc.perform(multipart(resourcePath, venue.getId()).file(photo))
                .andExpect(status().isOk());
        inputStream.close();
    }

    @Test
    void givenCorrectFilePath_whenUploadingNewPicture_shouldRetainSameMd5Hash() throws Exception {
        inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(correctFileName);
        String originalHash = DigestUtils.md5DigestAsHex(inputStream);
        inputStream.close();
        inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(correctFileName);
        photo = new MockMultipartFile("file", correctFileName, "image/png", inputStream);
        MvcResult result = mockMvc.perform(multipart(resourcePath, venue.getId()).file(photo))
                .andExpect(status().isOk())
                .andReturn();
        Map<String, String> response = mapper.readValue(result.getResponse().getContentAsString(), Map.class);
        String filePath = response.get("url");
        File file = new File(filePath);
        InputStream downloadInputStream = new FileInputStream(file);
        String downloadHash = DigestUtils.md5DigestAsHex(downloadInputStream);
        downloadInputStream.close();
        Assert.assertEquals(originalHash, downloadHash);
        inputStream.close();
    }

    @Test
    void givenIncorrectFileName_whenDownloadingPicture_shouldReturnStatus404 () throws Exception {
        mockMvc.perform(get(downloadPath, incorrectFileName))
                .andExpect(status().isNotFound());
    }
}
