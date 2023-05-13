package kafka.model;

import com.rasitesdmr.appointmentservice.enums.EStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "appointment")
public class Appointment extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private long cityId;
    private long hospitalId;
    private long clinicId;
    private String doctorIdentityNumber;
    private String appointmentTime;
    private String appointmentDate;

    @Enumerated(EnumType.STRING)
    private EStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Patient patient;
}
