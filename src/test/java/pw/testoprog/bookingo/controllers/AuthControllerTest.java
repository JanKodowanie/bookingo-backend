package pw.testoprog.bookingo.controllers;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pw.testoprog.bookingo.services.BookingoUserDetailsService;
import pw.testoprog.bookingo.services.JWTManager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    BookingoUserDetailsService userDetailsService;

    @MockBean
    JWTManager jwtManager;

    private ObjectMapper mapper = new ObjectMapper();

    private ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

    @Test
    void givenProperRegistrationData_whenRegisteringUser_thenReturn2xxResponse() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/register/standard")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"firstName\":\"testUser\", \"lastName\":\"testUser\", \"emailAddress\":\"testUser@test.test\", \"password\":\"testUser\" }")
        )
                .andExpect(status().is2xxSuccessful());

    }

    @Test
    void givenImproperRegistrationData_whenRegisteringUser_thenReturn4xxResponse() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/register/standard")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"firstName\":\"testUser\", \"lastName\":\"testUser\", \"emailAddress\":\"testUser@test.test\" }")
        )
                .andExpect(status().is4xxClientError());

    }

    @Test
    void givenProperRegistrationData_whenRegisteringEntrepreneur_thenReturn2xxResponse() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/register/entrepreneur")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"firstName\":\"testEntrepreneur\", \"lastName\":\"testEntrepreneur\", \"emailAddress\":\"testEntrepreneur@test.test\", \"password\":\"testEntrepreneur\" }")
        )
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void givenImproperRegistrationData_whenRegisteringEntrepreneur_thenReturn4xxResponse() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/register/standard")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"firstName\":\"testUser\", \"lastName\":\"testUser\", \"emailAddress\":\"testUser@test.test\" }")
        )
                .andExpect(status().is4xxClientError());

    }

    @Test
    void givenProperRegistrationData_whenRegisteringAdmin_thenReturn2xxResponse() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/register/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"firstName\":\"testAdmin\", \"lastName\":\"testAdmin\", \"emailAddress\":\"testAdmin@test.test\", \"password\":\"testAdmin\" }")
        )
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void givenImproperRegistrationData_whenRegisteringAdmin_thenReturn4xxResponse() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/register/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"firstName\":\"testAdmin\", \"lastName\":\"testAdmin\", \"emailAddress\":\"testAdmin@test.test\", \"password\":null }")
        )
                .andExpect(status().is4xxClientError());
    }

    @Test
    void createUserTest() throws Exception {

        //  użytkownik nie może się zalogować - błędne dane logowania (konto nie istnieje)
        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"email\":\"testUser@test.test\", \"password\":\"testUser\" }")
        )
                .andExpect(status().is4xxClientError());

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
    }

}
