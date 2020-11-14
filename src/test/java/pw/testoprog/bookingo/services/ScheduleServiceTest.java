package pw.testoprog.bookingo.services;


import org.junit.jupiter.api.Test;
import pw.testoprog.bookingo.models.Schedule;
import pw.testoprog.bookingo.models.User;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScheduleServiceTest {

    private ScheduleService scheduleService;

    @Test
    void addSchedule() throws Exception {
        System.out.println("dupa1");
        User user = new User("test", "test", "test", "test", "entrepreneur");
        Schedule schedule = new Schedule(1, 1, LocalDateTime.of(2021, Month.JANUARY, 1, 8, 0, 0), LocalDateTime.of(2021, Month.JANUARY, 1, 10, 0, 0));
        System.out.println("dupa2");
        Schedule addedSchedule = scheduleService.addNewSchedule(schedule, user);
//        assertTrue(addedSchedule != null);
        System.out.println("dupa3");
    }

}
