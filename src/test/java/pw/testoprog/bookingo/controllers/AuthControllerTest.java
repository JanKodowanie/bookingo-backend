package pw.testoprog.bookingo.controllers;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pw.testoprog.bookingo.models.User;
import pw.testoprog.bookingo.services.BookingoUserDetailsService;
import pw.testoprog.bookingo.services.JWTManager;
import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    BookingoUserDetailsService userDetailsService;

    @MockBean
    JWTManager jwtManager;

    private User user;

    private ObjectMapper mapper = new ObjectMapper();


    private ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    @BeforeEach
    public void setup() {

    }

    @Test
    void givenProperRegistrationData_whenRegisteringUser_thenReturn2xxResponse() throws Exception {



        User newUser = new User();
        newUser.setFirstName("testname");
        newUser.setEmailAddress("taest@test.com");
        newUser.setPassword("testPassword");
        newUser.setLastName("testsurname");

        /*mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(newUser);*/

        String body = "{\n" +
                "  \"emailAddress\" : \"taest@example.coam\",\n" +
                "  \"firstName\" : \"aaa\",\n" +
                "  \"lastName\" : \"Test last namea\",\n" +
                "  \"password\" : \"passa\"\n" +
                "}";

        mockMvc.perform( MockMvcRequestBuilders
                .post("/auth/register/standard")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ow.writeValueAsString(newUser))
//                .content(body)
        )
        .andExpect(status().is2xxSuccessful());

    }

    @Test
    void givenProperRegistrationData_whenRegisteringEntrepreneur_thenReturn2xxResponse() throws Exception {

        User newUser = new User();
        newUser.setFirstName("testname");
        newUser.setEmailAddress("taest@test.com");
        newUser.setPassword("testPassword");
        newUser.setLastName("testsurname");

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/auth/register/entrepreneur")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ow.writeValueAsString(newUser))
        )
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void givenProperRegistrationData_whenRegisteringAdmin_thenReturn2xxResponse() throws Exception {

        User newUser = new User();
        newUser.setFirstName("testname");
        newUser.setEmailAddress("taest@test.com");
        newUser.setPassword("testPassword");
        newUser.setLastName("testsurname");

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/auth/register/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ow.writeValueAsString(newUser))
        )
                .andExpect(status().is2xxSuccessful());
    }

}
