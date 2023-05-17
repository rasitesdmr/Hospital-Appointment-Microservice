package kafka.model.dto.response;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorResponse {

    private String identityNumber;

    private String firstName;

    private String lastName;

    private String dateOfBirth;

    private String phoneNumber;

    private String email;

    private String profession;
}
