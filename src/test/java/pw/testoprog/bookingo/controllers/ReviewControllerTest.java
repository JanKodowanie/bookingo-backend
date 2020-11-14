package pw.testoprog.bookingo.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pw.testoprog.bookingo.models.ServiceType;
import pw.testoprog.bookingo.models.User;
import pw.testoprog.bookingo.models.Venue;
import pw.testoprog.bookingo.repositories.ServiceTypeRepository;
import pw.testoprog.bookingo.services.BookingoUserDetailsService;
import pw.testoprog.bookingo.services.VenueService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerTest {
    /*
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    BookingoUserDetailsService userDetailsService;

    @Autowired
    ServiceTypeRepository serviceTypeRepository;

    @Autowired
    VenueService venueService;

    @Autowired
    ReviewService reviewService;

    ObjectMapper om = new ObjectMapper();
    ObjectWriter ow = om.writer().withDefaultPrettyPrinter();

    private User newUser;
    private Venue newVenue;
    private ServiceType newService;
    private Review newReview;

    @BeforeEach
    void setupUser() throws Exception {
        //Create user
        Random rn = new Random();
        String email = "test" + Integer.toString(rn.nextInt()) + "@gmail.com";
        newUser = new User(email, "password", "Jan", "Kowalski", "user");
        userDetailsService.registerUser(newUser, "user");

        //Create venue & service type
        newService = new ServiceType("Test1");
        serviceTypeRepository.save(newService);
        newVenue = new Venue("Test Venue", "Test City", "Test Address", newUser, new HashSet<>(Arrays.asList(s1)));
        newVenue = venueService.addNewVenue(newVenue);

        //Create review
        newReview = new Review(newUser.getId(), "test content", newService.getId());
    }


    //CREATE & READ
    @Test
    void postReview_checkIfAddedToDatabase() throws Exception {
        //Post new review
        mockMvc.perform(post("/venues/{venue-id})/reviews",newVenue.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(newReview)))
                .andExpect(status().isCreated());

        //Get posted review
        MvcResult resultAction = mockMvc.perform(get("/venues/{venue-id}/reviews", newVenue.getId()))
                .andExpect(status().isOk())
                .andReturn();

        Review responseReviews[] = om.readValue(resultAction.getResponse().getContentAsString(), Review.class);
        for(int i=0; i<responseReviews.length; i++) {
            Review responseReview = responseReviews[i];
            if (newReview.equals(responseReview))
                Assert.assertTrue(true);
        }
        Assert.assertTrue(false);
    }


    //UPDATE
    @Test
    void updateReview_getUpdatedData() throws Exception {
        //Add review to database
        newReview = reviewService.addReview(newReview, newVenue.getId());

        //Get review to update
        MvcResult resultAction = mockMvc.perform(get("/review/{id}", newReview.getId()))
                .andExpect(status().isOk())
                .andReturn();


        //Update data
        Review beforeUpdateReview = om.readValue(resultAction.getResponse().getContentAsString(), Review.class);
        beforeUpdateReview.setContent("Updated Content");

        //Posting updated review
        resultAction = mockMvc.perform(put("/review/{id}", newReview.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(beforeUpdateReview))
        )
                .andExpect(status().isOk())
                .andReturn();
        Review afterUpdateReview = om.readValue(resultAction.getResponse().getContentAsString(), Review.class);

        //Chcecking if update was successful
        Assert.assertEquals(beforeUpdateReview, afterUpdateReview);
    }

    //DELETE
    @Test
    void deleteAReview_checkIfRemoved() throws Exception {
        //Adding review to database
        newReview = reviewService.addReview(newReview, newVenue.getId());

        //Delete review
        mockMvc.perform(delete("/review/{id}", newReview.getId()))
                .andExpect(status().isNoContent());

        //Check if deleted
        mockMvc.perform(get("/review/{id}", newReview.getId()))
                .andExpect(status().isNotFound());
    }

    */
}
