package kafka.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "doktor_iletişim")
public class DoctorContactInfo {

    @Id
    @Column(name = "telefon_numarası")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @OneToOne(mappedBy = "contactInfo", cascade = CascadeType.ALL)
    private Doctor doctor;
}
