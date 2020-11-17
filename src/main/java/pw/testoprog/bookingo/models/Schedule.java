package pw.testoprog.bookingo.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "venueId must be provided.")
    private Integer venueId;

    @NotNull(message = "serviceTypeId must be provided.")
    private Integer serviceTypeId;

    @NotNull(message = "startDate must be provided.")
    private LocalDateTime startDate;

    @NotNull(message = "endDate must be provided.")
    private LocalDateTime endDate;

    public Schedule() {

    }

    public Schedule(Integer venueId, Integer serviceTypeId, LocalDateTime startDate, LocalDateTime endDate) {
        this.venueId = venueId;
        this.serviceTypeId = serviceTypeId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return String.format("Usługa o id %s w salonie o id %s od %s do %s", this.serviceTypeId, this.venueId, this.startDate, this.endDate);
    }

    public Integer getVenueId() {
        return venueId;
    }
}
