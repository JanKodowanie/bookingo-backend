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
    ScheduleRepository scheduleRepository;

    public Schedule addNewSchedule(Schedule schedule, User user) throws Exception {
        System.out.println("dupaaa");
        /*if(venue == null)
            throw new Exception("Invalid venue");
        if(serviceType == null)
            throw new Exception("Invalid serviceType");
        if(startDate == null)
            throw new Exception("Invalid startDate");
        if(endDate == null)
            throw new Exception("Invalid endDate");
        if(user == null)
            throw new Exception("Invalid user");
        if(!user.getRole().equals("entrepreneur"))
            throw new Exception("You don't have permission to do that");*/
        if(schedule == null)
            throw new Exception("invalid schedule");
        if (user == null)
            throw new Exception("invalid user");

        scheduleRepository.save(schedule);

        return schedule;
    }

}
