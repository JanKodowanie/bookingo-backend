package pw.testoprog.bookingo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.DigestUtils;
import pw.testoprog.bookingo.models.Venue;
import pw.testoprog.bookingo.repositories.VenueRepository;

import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GalleryPhotoControllerTest {

    private final static int venueId = 999;
    private final static String resourcePath = "/user/%d/gallery-photos";
    private InputStream inputStream;
    private MockMultipartFile photo;
    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VenueRepository venueRepository;

    private Venue venue;

    @BeforeEach
    public void setup() throws Exception {
        venue = new Venue();
        venue.setId(venueId);
        venueRepository.save(venue);
        inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("test.png");
        photo = new MockMultipartFile("file", "test.png", "image/png", inputStream);
    }

    @Test
    public void givenCorrectFilePath_whenUploadingNewPicture_shouldReturnStatus200() throws Exception {
        mockMvc.perform(multipart(resourcePath, venueId).file(photo))
                .andExpect(status().isOk());
    }

    @Test
    public void givenCorrectFilePath_whenUploadingNewPicture_shouldRetainSameMd5Hash() throws Exception {
        String originalHash = DigestUtils.md5DigestAsHex(inputStream);
        MvcResult result = mockMvc.perform(multipart(resourcePath, venueId).file(photo))
                .andExpect(status().isOk())
                .andReturn();
        Map<String, String> response = mapper.readValue(result.getResponse().getContentAsString(), Map.class);
        String downloadUrl = response.get("url");
        InputStream downloadStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(downloadUrl);
        String downloadHash = DigestUtils.md5DigestAsHex(downloadStream);
        Assert.assertEquals(originalHash, downloadHash);
    }
}
