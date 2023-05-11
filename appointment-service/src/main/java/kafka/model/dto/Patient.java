package kafka.model.dto;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "patient")
public class Patient {

    @Id
    private String identityNumber;
    private String firstName;
    private String lastName;
    private String email;

    @OneToMany(mappedBy = "patient",fetch = FetchType.LAZY)
    private List<Appointment> appointment;

}
