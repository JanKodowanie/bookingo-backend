package pw.testoprog.bookingo.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import pw.testoprog.bookingo.models.Schedule;
import pw.testoprog.bookingo.models.User;
import pw.testoprog.bookingo.repositories.ServiceTypeRepository;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ScheduleServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    BookingoUserDetailsService userDetailsService;

    @Autowired
    ServiceTypeRepository serviceTypeRepository;

    @Autowired
    VenueService venueService;

    @Autowired
    ScheduleService scheduleService;

    @Test
    void addScheduleAsNobody() throws Exception {
        User user = null;
        Schedule schedule = new Schedule(1, 1, LocalDateTime.of(2021, Month.JANUARY, 1, 8, 0, 0), LocalDateTime.of(2021, Month.JANUARY, 1, 10, 0, 0));
        Exception exception = assertThrows(Exception.class, ()
                        -> {
                    Schedule addedSchedule = scheduleService.addNewSchedule(schedule, user);
                }
        );
        assertTrue(exception.getMessage().contains("invalid user"));
    }

    @Test
    void addScheduleAsUnauthorizedUser() throws Exception {
        User user = new User("test", "test", "test", "test", "standard");
        Schedule schedule = new Schedule(1, 1, LocalDateTime.of(2021, Month.JANUARY, 1, 8, 0, 0), LocalDateTime.of(2021, Month.JANUARY, 1, 10, 0, 0));
        Exception exception = assertThrows(Exception.class, ()
                        -> {
                    Schedule addedSchedule = scheduleService.addNewSchedule(schedule, user);
                }
        );
        assertTrue(exception.getMessage().contains("you must be entrepreneur to perform this action"));
    }

    @Test
    void addScheduleAsAuthorized() throws Exception {
        User user = new User("test", "test", "test", "test", "entrepreneur");
        Schedule schedule = new Schedule(1, 1, LocalDateTime.of(2021, Month.JANUARY, 1, 8, 0, 0), LocalDateTime.of(2021, Month.JANUARY, 1, 10, 0, 0));
        Schedule addedSchedule = scheduleService.addNewSchedule(schedule, user);
        assertNotNull(addedSchedule);
        assertEquals(schedule, addedSchedule);
    }

    @Test
    void addNullSchedule() throws Exception {
        User user = new User("test", "test", "test", "test", "standard");
        Schedule schedule = null;
        Exception exception = assertThrows(Exception.class, ()
                        -> {
                    Schedule addedSchedule = scheduleService.addNewSchedule(schedule, user);
                }
        );
        assertTrue(exception.getMessage().contains("invalid schedule"));
    }

    @Test
    void addScheduleWithWrongVenueId() throws Exception {
        User user = new User("test", "test", "test", "test", "entrepreneur");
        Schedule schedule = new Schedule(100, 1, LocalDateTime.of(2021, Month.JANUARY, 1, 8, 0, 0), LocalDateTime.of(2021, Month.JANUARY, 1, 10, 0, 0));
        Exception exception = assertThrows(Exception.class, ()
                        -> {
                    Schedule addedSchedule = scheduleService.addNewSchedule(schedule, user);
                }
        );
        assertEquals("Venue not found", exception.getMessage());
    }

}
