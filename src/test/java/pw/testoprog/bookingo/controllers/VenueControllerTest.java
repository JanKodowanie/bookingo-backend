package pw.testoprog.bookingo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.*;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import pw.testoprog.bookingo.models.ServiceType;
import pw.testoprog.bookingo.models.User;
import pw.testoprog.bookingo.models.Venue;
import pw.testoprog.bookingo.repositories.ServiceTypeRepository;
import pw.testoprog.bookingo.services.BookingoUserDetailsService;
import pw.testoprog.bookingo.services.VenueService;

import javax.validation.constraints.AssertFalse;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class VenueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    BookingoUserDetailsService userDetailsService;

    @Autowired
    ServiceTypeRepository serviceTypeRepository;

    @Autowired
    VenueService venueService;

    private User newUser;
    private Venue newVenue;
    private static int count = 0;

    private ObjectMapper mapper = new ObjectMapper();
    private ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    @BeforeEach
    void setup() throws Exception {
        count += 1;
        String email = "test" + count + "@gmail.com";
        newUser = new User(email, "password", "Jan", "Kowalski", "entrepreneur");
        userDetailsService.registerUser(newUser, "entrepreneur");
        ServiceType s1 = new ServiceType("Test1");
        ServiceType s2 = new ServiceType("Test2");
        serviceTypeRepository.save(s1);
        serviceTypeRepository.save(s2);
        List<ServiceType> serviceTypeList = Arrays.asList(s1, s2);
        newVenue = new Venue("Test Name", "Test City", "Test Address", newUser, new HashSet<>(serviceTypeList));
    }

    @Test
    void getAllVenues_returnsOkResponse() throws Exception {
        mockMvc.perform(get("/venues"))
                .andExpect(status().isOk());
    }


    //CREATE & READ
    @Test
    void postVenue_checkIfAddedToDatabase() throws Exception {
        //post new venue
        ResultActions returnedResult = mockMvc.perform(post("/venues")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(newVenue))
        )
                .andExpect(status().isCreated());
        Venue postVenue = mapper.readValue(returnedResult.andReturn().getResponse().getContentAsString(), Venue.class);

        //get posted venue from database
        ResultActions resultActions = mockMvc.perform(get("/venues/{id}", postVenue.getId())
        )
                .andExpect(status().isOk());

                MvcResult result = resultActions.andReturn();
        Venue responseVenue = mapper.readValue(result.getResponse().getContentAsString(), Venue.class);

        //assert if creation was successful
        Assert.assertEquals(newVenue, responseVenue);
    }

    @Test
    void postWithInvalidUser_returnsUserNotFound() throws Exception {
        UUID wrongUserId = UUID.randomUUID();
        newVenue.getUser().setId(wrongUserId);
        mockMvc.perform(post("/venues")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(newVenue)))
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


    //UPDATE
    @Test
    void updateAVenue_getUpdatedData() throws Exception {
        //getting venue to update
        ResultActions returnedResult = mockMvc.perform(get("/venues/1"))
                .andExpect(status().isOk());
        Venue beforeUpdateVenue = mapper.readValue(returnedResult.andReturn().getResponse().getContentAsString(), Venue.class);
        newVenue = beforeUpdateVenue.clone();
        newVenue.setName("Updated Venue");
        //posting updated venue
        returnedResult = mockMvc.perform(put("/venues/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(newVenue))
        )
                .andExpect(status().isOk());
        Venue afterUpdateVenue = mapper.readValue(returnedResult.andReturn().getResponse().getContentAsString(), Venue.class);
        //checking if update was successful
        Assert.assertEquals(afterUpdateVenue, newVenue);

    }

    @Test
    void patchAVenue_whenWrongDataIsSent_thenReturnIsBadRequest() throws Exception {
        User NotAVenue = new User();
        mockMvc.perform(put("/venues/{id}", 99+count)
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(NotAVenue)))
                .andExpect(status().isNotFound());
    }


    //DELETE
    @Test
    void deleteAVenue_whenProperId_thenReturnNoContentResponse() throws Exception {
        //posting a venue to delete
        ResultActions returnedResult = mockMvc.perform(post("/venues")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(newVenue))
        )
                .andExpect(status().isCreated());
        Venue addedVenue = mapper.readValue(returnedResult.andReturn().getResponse().getContentAsString(), Venue.class);

        //deleting a venue
        mockMvc.perform(delete("/venues/{id}", addedVenue.getId()))
                .andExpect(status().isNoContent());

        //checking if venue was deleted
        mockMvc.perform(get("/venues/{id}", addedVenue.getId()))
                .andExpect(status().isNotFound());
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
