package kafka.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rasitesdmr.securityservice.enums.ERole;
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
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol_adı")
    public ERole name;

    @ManyToMany(mappedBy = "roleList")
    @JsonIgnore
    private List<User> userList;

    public Role(ERole name) {
        this.name = name;
    }
}

