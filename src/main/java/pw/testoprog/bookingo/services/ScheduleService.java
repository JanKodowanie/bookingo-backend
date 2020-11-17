package pw.testoprog.bookingo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pw.testoprog.bookingo.models.Schedule;
import pw.testoprog.bookingo.models.ServiceType;
import pw.testoprog.bookingo.models.User;
import pw.testoprog.bookingo.models.Venue;
import pw.testoprog.bookingo.repositories.ScheduleRepository;
import pw.testoprog.bookingo.repositories.UserRepository;
import pw.testoprog.bookingo.repositories.VenueRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    VenueRepository venueRepository;

    @Autowired
    VenueService venueService;

    @Autowired
    ScheduleRepository scheduleRepository;

    public Schedule addNewSchedule(Schedule schedule, User user) throws Exception {
        if(schedule == null)
            throw new Exception("invalid schedule");
        if (user == null)
            throw new Exception("invalid user");
        if(!user.getRole().equals("entrepreneur"))
            throw new Exception("you must be entrepreneur to perform this action");
        try {
            Venue matchingVenue = venueService.getVenueById(schedule.getVenueId());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        scheduleRepository.save(schedule);

        return schedule;
    }

}
