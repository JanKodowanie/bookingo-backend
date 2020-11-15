package pw.testoprog.bookingo.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pw.testoprog.bookingo.models.Employee;
import pw.testoprog.bookingo.services.BookingoEmployeeDetailsService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BookingoEmployeeDetailsService employeeDetailsService;

    @Test
    void addNewEmployeeTest() throws Exception {
        Employee expectedEmployee = new Employee(
                "testUser@test.test",
                "password",
                "testUser",
                "testUser",
                "Standard"
        );

        //  Tworzenie pracownika
        mockMvc.perform(MockMvcRequestBuilders
                .post("/employee/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"firstName\":\"testUser\", \"lastName\":\"testUser\", \"emailAddress\":\"testUser@test.test\", \"password\":\"testUser\" }")

        )
                .andExpect(status().is2xxSuccessful());

    }
}
