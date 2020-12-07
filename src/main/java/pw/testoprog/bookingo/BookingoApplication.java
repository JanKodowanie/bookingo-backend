package pw.testoprog.bookingo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pw.testoprog.bookingo.config.FileStorageProperties;

@EnableConfigurationProperties({
        FileStorageProperties.class
})
@SpringBootApplication(exclude={UserDetailsServiceAutoConfiguration.class})
public class BookingoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookingoApplication.class, args);
    }

}
