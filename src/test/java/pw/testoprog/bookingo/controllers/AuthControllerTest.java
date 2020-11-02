package pw.testoprog.bookingo.controllers;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pw.testoprog.bookingo.exceptions.UserNotFoundException;
import pw.testoprog.bookingo.models.User;
import pw.testoprog.bookingo.services.BookingoUserDetailsService;
import pw.testoprog.bookingo.services.JWTManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    BookingoUserDetailsService bookingoUserDetailsService;

    @MockBean
    JWTManager jwtManager;

    private ObjectMapper mapper = new ObjectMapper();

    private ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    @Test
    void createUserTest() throws Exception {

        User expectedUser = new User(
                "testUser@test.test",
                "password",
                "testUser",
                "testUser",
                "Standard"
        );

        //  użytkownik nie może się zalogować - błędne dane logowania (konto nie istnieje)
        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"email\":\"testUser@test.test\", \"password\":\"testUser\" }")
        )
                .andExpect(status().is4xxClientError());

        //  test braku użytkownika - assert
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            UserDetails u = bookingoUserDetailsService.loadUserByUsername("testUser@test.test");
        });

        //  tworzenie konta użytkownika
        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/register/standard")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"firstName\":\"testUser\", \"lastName\":\"testUser\", \"emailAddress\":\"testUser@test.test\", \"password\":\"testUser\" }")
        )
                .andExpect(status().is2xxSuccessful());

        // użytkownik może się zalogować - konto zostało utworzone
        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"email\":\"testUser@test.test\", \"password\":\"testUser\" }")
        )
                .andExpect(status().is2xxSuccessful());

        //  test utworzonego użytkownika - assert
        try {
            UserDetails u = bookingoUserDetailsService.loadUserByUsername("testUser@test.test");
            Assert.assertEquals(u.getUsername(), expectedUser.getEmailAddress());
        } catch (Exception ignored) {

        }
    }
}
