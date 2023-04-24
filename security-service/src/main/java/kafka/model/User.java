package kafka.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @Column(name = "kimlik_numarası")
    private String identityNumber;
    @Column(name = "adı")
    private String firstName;
    @Column(name = "soyadı")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "şifre")
    private String password;

    @ManyToMany()
    @JoinTable(name = "user_roles",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "kimlik_numarası")},
            inverseJoinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roleList;


    public User(String firstName, String lastName, String email, String password, List<Role> roleList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roleList = roleList;
    }
}
