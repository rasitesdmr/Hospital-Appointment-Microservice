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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "telefon_numarası", referencedColumnName = "telefon_numarası")
    private DoctorContactInfo contactInfo;

    @Column(name = "uzmanlık_alanı")
    private String profession;

    @ManyToMany(mappedBy = "doctors",fetch = FetchType.LAZY)
    private Set<Hospital> hospitals;

    @ManyToMany(fetch = FetchType.LAZY)
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