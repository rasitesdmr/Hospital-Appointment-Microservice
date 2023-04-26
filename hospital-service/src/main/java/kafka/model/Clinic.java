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
@Table(name = "klinik")
public class Clinic {

    @Id
    @GeneratedValue(generator = "random_id_generator")
    @GenericGenerator(name = "random_id_generator", strategy = "com.rasitesdmr.hospitalservice.util.RandomIdGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "adı")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "clinics")
    private Set<Hospital>hospitals;

    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "clinics")
    private Set<Doctor>doctors;
}
