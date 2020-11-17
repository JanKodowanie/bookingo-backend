package pw.testoprog.bookingo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pw.testoprog.bookingo.models.Schedule;
import pw.testoprog.bookingo.models.ServiceType;
import pw.testoprog.bookingo.models.User;
import pw.testoprog.bookingo.models.Venue;
import pw.testoprog.bookingo.repositories.ScheduleRepository;
import pw.testoprog.bookingo.repositories.ServiceTypeRepository;
import pw.testoprog.bookingo.repositories.UserRepository;
import pw.testoprog.bookingo.repositories.VenueRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ServiceTypeRepository serviceTypeRepository, VenueRepository venueRepository, UserRepository userRepository, ScheduleRepository scheduleRepository) {

        return args -> {
            User u1 = userRepository.save(new User("j.kowalski@gmail.com", "password", "Jan", "Kowalski", "entrepreneur"));
            User u2 = userRepository.save(new User("a.nowak@gmail.com", "password", "Anna", "Nowak", "entrepreneur"));

            ServiceType s1 = serviceTypeRepository.save(new ServiceType("Strzyżenie"));
            ServiceType s2 = serviceTypeRepository.save(new ServiceType("Manicure"));
            ServiceType s3 = serviceTypeRepository.save(new ServiceType("Farbowanie włosów"));
            ServiceType s4 = serviceTypeRepository.save(new ServiceType("Trening osobisty"));

            HashSet<ServiceType> sSet1 = new HashSet<>(Arrays.asList(s1, s3));
            HashSet<ServiceType> sSet2 = new HashSet<>(Arrays.asList(s1, s2, s3));
            HashSet<ServiceType> sSet3 = new HashSet<>(Collections.singletonList(s4));


            Venue v1 = venueRepository.save(new Venue("Salon fryzjerski pani Stasi", "Warszawa", "Al. Ken 1", u1, sSet1));
            Venue v2 = venueRepository.save(new Venue("MaxLux Salon Fryzjerski", "Warszawa", "Złota 1", u1, sSet2));
            Venue v3 = venueRepository.save(new Venue("The Church of Holy Gains", "Kraków", "Lajkonika 2", u2, sSet3));

            log.info("\nLoaded venues:");
            log.info(v1.toString());
            log.info(v2.toString());
            log.info(v3.toString());

            Schedule sc1 = scheduleRepository.save(new Schedule(v1.getId(), s1.getId(), LocalDateTime.of(2021, Month.JANUARY, 1, 8, 0, 0), LocalDateTime.of(2021, Month.JANUARY, 1, 10, 0, 0)));
            Schedule sc2 = scheduleRepository.save(new Schedule(v1.getId(), s1.getId(), LocalDateTime.of(2021, Month.JANUARY, 1, 10, 0, 0), LocalDateTime.of(2021, Month.JANUARY, 1, 12, 0, 0)));
            Schedule sc3 = scheduleRepository.save(new Schedule(v1.getId(), s2.getId(), LocalDateTime.of(2021, Month.JANUARY, 1, 8, 0, 0), LocalDateTime.of(2021, Month.JANUARY, 1, 10, 0, 0)));

            log.info("\nloaded schedules:");
            log.info(sc1.toString());
            log.info(sc2.toString());
            log.info(sc3.toString());
        };
    }
}
