package kafka.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "şehir")
public class City {

    @Id
    @GeneratedValue(generator = "random_id_generator")
    @GenericGenerator(name = "random_id_generator", strategy = "com.rasitesdmr.hospitalservice.util.RandomIdGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "adı")
    private String name;

    @OneToMany(mappedBy = "city")
    private List<Hospital> hospitals;
}
