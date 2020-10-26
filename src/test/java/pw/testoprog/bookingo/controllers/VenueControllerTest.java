package pw.testoprog.bookingo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.*;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pw.testoprog.bookingo.models.ServiceType;
import pw.testoprog.bookingo.models.User;
import pw.testoprog.bookingo.models.Venue;
import pw.testoprog.bookingo.serializers.VenueContext;
import pw.testoprog.bookingo.services.BookingoUserDetailsService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class VenueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    BookingoUserDetailsService userDetailsService;

    private User user;
    private Venue venue;
    private VenueContext venueContext;
    private static int count = 0;

    private ObjectMapper mapper = new ObjectMapper();
    private ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    @BeforeEach
    void setup() throws Exception {
        count += 1;
        String email = "test" + count + "@gmail.com";
        user = new User(email, "password", "Jan", "Kowalski", "entrepreneur");
        userDetailsService.registerUser(user, "entrepreneur");

        venue = new Venue("Test Name", "Test City", "Test Address", user, new HashSet<>(Arrays.asList(new ServiceType("Test Service Type"))));
        venue.setId(100+count);
        venueContext = new VenueContext(venue, user.getId(), new Integer[]{1, 2} );
    }

    @Test
    void getAllVenues_returnsOkResponse() throws Exception {
        mockMvc.perform(get("/venues"))
                .andExpect(status().isOk());
    }

    @Test
    void postVenue_returnsOkResponse() throws Exception {
        mockMvc.perform(post("/venues")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(venueContext))
        )
        .andExpect(status().isOk());
    }

    @Test
    void postWithInvalidUser_returnsUserNotFound() throws Exception {
        UUID wrongUserId = UUID.randomUUID();
        venueContext.setUser_id(wrongUserId);
        mockMvc.perform(post("/venues")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(venueContext)))
                .andExpect(status().isNotFound());
    }

    @Test
    void postNotAVenue_returnsNotFound() throws Exception {
        User NotAVenue = new User();
        mockMvc.perform(post("/venues")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(NotAVenue)))
        .andExpect(status().isNotFound());
    }

    @Test
    void postANulLVenue_returnsNotFound() throws Exception {
        mockMvc.perform(post("/venues")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void putAVenue_whenUsedVenueId_thenReturnOkResponse() throws Exception {
        mockMvc.perform(put("/venues/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(venueContext)))
                .andExpect(status().isOk());
    }

    @Test
    void putAVenue_whenUnusedVenueId_thenReturnOkResponse() throws Exception {
        mockMvc.perform(put("/venues/{id}", 999)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(venueContext)))
                .andExpect(status().isOk());
    }

    @Test
    void patchAVenue_whenWrongDataIsSent_thenReturnIsBadRequest() throws Exception {
        User NotAVenue = new User();
        mockMvc.perform(put("/venues/{id}", 99+count)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(NotAVenue)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteAVenue_whenProperId_thenReturnNoContentResponse() throws Exception {
        mockMvc.perform(delete("/venues/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteAVenue_whenWrongId_thenReturnNotFoundResponse() throws Exception {
        mockMvc.perform(delete("/venues/-1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getVenuesByServiceType_whenProperServiceTypeId_returnsOkResponse() throws Exception {
        mockMvc.perform(get("/venues/service-type/{id}", 1))
                .andExpect(status().isOk());
    }
}
