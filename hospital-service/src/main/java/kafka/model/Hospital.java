package kafka.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "hastane")
public class Hospital {

    @Id
    @GeneratedValue(generator = "random_id_generator")
    @GenericGenerator(name = "random_id_generator", strategy = "com.rasitesdmr.hospitalservice.util.RandomIdGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "adı")
    private String name;

    @Column(name = "adres")
    private String address;

    @ManyToOne
    @JoinColumn(name = "şehir_id")
    private City city;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "hastane_doktor",
            joinColumns = {
                    @JoinColumn(name = "hastane_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "doktor_id")
            }
    )
    private Set<Doctor> doctors;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "hastane_klinik",
            joinColumns = {
                    @JoinColumn(name = "hastane_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "klinik_id")
            }
    )
    private Set<Clinic> clinics;
}
