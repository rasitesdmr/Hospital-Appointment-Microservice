package kafka.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "doktor")
public class Doctor {

    @Id
    @Column(name = "kimlik_numarası")
    private String identityNumber;

    @Column(name = "adı")
    private String firstName;

    @Column(name = "soyadı")
    private String lastName;

    @Column(name = "doğum_tarihi")
    private String dateOfBirth;

    @Column(name = "telefon_numarası")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "uzmanlık_alanları")
    private String profession;

    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "doctors")
    private Set<Hospital> hospitals;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "doktor_klinik",
            joinColumns = {
                    @JoinColumn(name = "doktor_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "klinik_id")
            }
    )
    private Set<Clinic>clinics;

}
