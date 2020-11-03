package pw.testoprog.bookingo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import pw.testoprog.bookingo.exceptions.UserNotFoundException;
import pw.testoprog.bookingo.models.User;
import pw.testoprog.bookingo.services.BookingoUserDetailsService;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BookingoUserDetailsService userDetailsService;

    private User user;

    private ObjectMapper mapper = new ObjectMapper();
    private ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    @BeforeEach
    public void setup() {
        user = new User();
        user.setId(UUID.randomUUID());
        user.setFirstName("Test");
    }

    User expectedUser = new User(
            "testUser@test.test",
            "password",
            "testUser",
            "testUser",
            "Standard"
    );

    @Test
    void givenProperUserId_whenGettingUserDetails_thenReturnOkResponse() throws Exception {
        given(userDetailsService.getUserDetails(user.getId())).willReturn(user);

        mockMvc.perform(get("/user/{userId}", user.getId()))
                .andExpect(status().isOk());

        try {
            UserDetails u = userDetailsService.loadUserByUsername("testUser@test.test");
            Assert.assertEquals(u.getUsername(), expectedUser.getEmailAddress());
        } catch (Exception ignored) {

        }
    }

    @Test
    void givenWrongUserId_whenGettingUserDetails_thenReturnNotFoundResponse() throws Exception {
        UUID wrongId = UUID.randomUUID();
        given(userDetailsService.getUserDetails(wrongId)).willThrow(UserNotFoundException.class);

        mockMvc.perform(get("/user/{userId}", wrongId))
                .andExpect(status().isNotFound());

        try {
            UserDetails u = userDetailsService.loadUserByUsername("random@test.test");
            Assert.assertNotEquals(u.getUsername(), expectedUser.getEmailAddress());
        } catch (Exception ignored) {

        }
    }

    @Test
    void givenProperUserId_whenEditingUserDetails_thenReturnOkResponse() throws Exception {
        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setFirstName("Changed first name");
        newUser.setEmailAddress("testUser@test.test");
        newUser.setPassword("pass");
        newUser.setLastName("Test last name");
        newUser.setCreatedOn(LocalDate.now());

        given(userDetailsService.getUserDetails(newUser.getId())).willReturn(newUser);
        given(userDetailsService.updateUser(newUser.getId(), newUser)).willReturn(newUser);

        mockMvc.perform(
                patch("/user/{id}", newUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(ow.writeValueAsString(newUser))
                )
                .andExpect(status().isOk());

        try {
            UserDetails u = userDetailsService.loadUserByUsername("testUser@test.test");
            Assert.assertEquals(u.getUsername(), newUser.getEmailAddress());
        } catch (Exception ignored) {

        }
    }


    @Test
    void givenWrongUserId_whenEditingUserDetails_thenReturnNotFoundResponse() throws Exception {
        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setFirstName("Changed first name");
        newUser.setEmailAddress("test@example.com");
        newUser.setPassword("pass");
        newUser.setLastName("Test last name");
        newUser.setCreatedOn(LocalDate.now());

        UUID wrongId = UUID.randomUUID();

        given(userDetailsService.getUserDetails(wrongId)).willThrow(UserNotFoundException.class);
        given(userDetailsService.updateUser(wrongId, newUser)).willThrow(UserNotFoundException.class);

        mockMvc.perform(
                patch("/user/{id}", wrongId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ow.writeValueAsString(newUser))
        )
                .andExpect(status().isNotFound());

        try {
            UserDetails u = userDetailsService.loadUserByUsername("testUser@test.test");
            Assert.assertNotEquals(u.getUsername(), newUser.getEmailAddress());
        } catch (Exception ignored) {

        }
    }

    @Test
    void givenProperUserId_whenDeletingUser_thenReturnOkResponse() throws Exception {
        doNothing().when(userDetailsService).deleteUser(user.getId());

        mockMvc.perform(delete("/user/{id}", user.getId()))
                .andExpect(status().isOk());
        
    }

    @Test
    void givenWrongUserId_whenDeletingUser_thenReturnNotFoundResponse() throws Exception {
        UUID wrongId = UUID.randomUUID();

        doThrow(UserNotFoundException.class).when(userDetailsService).deleteUser(wrongId);

        mockMvc.perform(delete("/user/{id}", wrongId))
                .andExpect(status().isNotFound());

        try {
            UserDetails u = userDetailsService.loadUserByUsername("random@test.test");
            Assert.assertNull(u);
        } catch (Exception ignored) {

        }
    }



}
