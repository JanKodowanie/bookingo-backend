package pw.testoprog.bookingo.benchmarks;

import com.fasterxml.jackson.databind.ser.Serializers;
import org.junit.runner.RunWith;
import org.openjdk.jmh.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pw.testoprog.bookingo.dto.UserDTO;
import pw.testoprog.bookingo.exceptions.EmailAlreadyRegisteredException;
import pw.testoprog.bookingo.models.User;
import pw.testoprog.bookingo.repositories.UserRepository;
import pw.testoprog.bookingo.services.BookingoUserDetailsService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class UserCreationBenchmark extends BaseBenchmark {

    private static UserRepository userRepository;
    private static BookingoUserDetailsService userService;

    @Param({"1", "5", "10"})
    private int userCount;

    @Autowired
    void setUserRepository(UserRepository userRepository) {
        UserCreationBenchmark.userRepository = userRepository;
    }

    @Autowired
    void setUserService(BookingoUserDetailsService userService) {
        UserCreationBenchmark.userService = userService;
    }

    @Benchmark
    public void userCreation() {
        List<User> users = generateUsers(userCount);
        users.parallelStream().forEach(userRepository::save);
    }

    @Benchmark
    public void userRegistration() {
        List<UserDTO> users = generateUserDTOs(userCount);
        users.parallelStream().forEach(user -> {
            try {
                userService.registerUser(user, "standard");
            } catch (EmailAlreadyRegisteredException e) {
                e.printStackTrace();
            }
        });
    }

    private List<User> generateUsers(int userCount) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < userCount; i++) {
            users.add(new User());
        }
        return users;
    }

    private List<UserDTO> generateUserDTOs(int userCount) {
        List<UserDTO> users = new ArrayList<>();
        for (int i = 0; i < userCount; i++) {
            users.add(new UserDTO(new User(UUID.randomUUID().toString() + "@example.com", "", "", "", "")));
        }
        return users;
    }
}
